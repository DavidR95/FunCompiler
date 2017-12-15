package api;

//////////////////////////////////////////////////////////////
//
// A visitor for code generation for Fun.
//
// Developed August 2015 by Simon Gay (University of Glasgow).
//
// Based on a previous version by David Watt.
//
//////////////////////////////////////////////////////////////

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class FunEncoderVisitor extends AbstractParseTreeVisitor<Void> implements FunVisitor<Void> {
	private int globalvaraddr = 0;
	private int localvaraddr = 0;
	private int currentLocale = Address.GLOBAL;
	private int currentOffset = 0;

	private SymbolTable<Address> addrTable = new SymbolTable<Address>();

	private void predefine () {
	// Add predefined procedures to the address table.
		addrTable.put("read", new Address(32766, Address.CODE));
		addrTable.put("write", new Address(32767, Address.CODE));
	}

	private JsonArray nodeOrder = new JsonArray();

	private String convertLocale(int locale) {
		switch(locale) {
			case 0: return "code";
			case 1: return "global";
			case 2: return "local";
		}
		return "Unrecognised locale";
	}

	private Map<Integer,LinkedList<String>> nodeExplanations = new HashMap<Integer,LinkedList<String>>();

	private void addNode(Object ctx, String explanation, int offsetAmount, Boolean isObjectCode) {
		currentOffset += offsetAmount;
		int contextHash = ctx.hashCode();
		List<String> explanationList = nodeExplanations.get(contextHash);
		if (explanationList != null) {
			explanationList.add(explanation);
		} else {
			nodeExplanations.put(contextHash, new LinkedList<String>(Arrays.asList(explanation)));
		}
		JsonObject nodeObject = new JsonObject();
		JsonArray explanationArray = new JsonArray();
		for (String nodeExplanation : nodeExplanations.get(contextHash)) {
			explanationArray.add(new JsonPrimitive(nodeExplanation));
		}
		JsonArray addrTableArray = new JsonArray();
		addrTable.getGlobals().forEach((id,addr) -> {
			JsonObject addrTableObject = new JsonObject();
			addrTableObject.addProperty("scope", convertLocale(addr.locale));
			addrTableObject.addProperty("id", id);
			addrTableObject.addProperty("type_address", Integer.toString(addr.offset));
			addrTableArray.add(addrTableObject);
		});
		addrTable.getLocals().forEach((id,addr) -> {
			JsonObject addrTableObject = new JsonObject();
			addrTableObject.addProperty("scope", convertLocale(addr.locale));
			addrTableObject.addProperty("id", id);
			addrTableObject.addProperty("type_address", Integer.toString(addr.offset));
			addrTableArray.add(addrTableObject);
		});
		nodeObject.addProperty("id", contextHash);
		nodeObject.add("explanations", explanationArray);
		nodeObject.add("table", addrTableArray);
		nodeOrder.add(nodeObject);
	}

	public JsonArray getNodeOrder() {
		return nodeOrder;
	}

	/**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProg(FunParser.ProgContext ctx) {
	    predefine();
		addNode(ctx, "Predefine the read and write procedures", 0, false);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code", 0, false);
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
	    int calladdr = currentOffset;
		addNode(ctx, "Note the current instruction address, c1 (" + calladdr + ")", 0, false);
		addNode(ctx, "Emit 'CALL 0'", 3, true);
		addNode(ctx, "Emit 'HALT'", 1, true);
	    List<FunParser.Proc_declContext> proc_decl = ctx.proc_decl();
		if (!proc_decl.isEmpty()) {
			addNode(ctx, "Walk proc-decl, generating code", 0, false);
		    for (FunParser.Proc_declContext pd : proc_decl)
				visit(pd);
		}
	    int mainaddr = addrTable.get("main").offset;
		addNode(ctx, "Lookup 'main' and retrieve its address, " + mainaddr, 0, false);
		addNode(ctx, "Patch address of 'main' (" + mainaddr + ") into the call at c1 (" + calladdr + ")", 0, false);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proc}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProc(FunParser.ProcContext ctx) {
	    String id = ctx.ID().getText();
	    Address procaddr = new Address( currentOffset, Address.CODE);
		addNode(ctx, "Insert '" + id + "' into the address table at address " + currentOffset + " (scope: code)", 0, false);
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
		addNode(ctx, "Enter local scope", 0, false);
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
		addNode(ctx, "Walk formal-decl, generating code", 0, false);
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if(!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code", 0, false);
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
		addNode(ctx, "Walk com, generating code", 0, false);
	    visit(ctx.seq_com());
		addNode(ctx, "Emit 'RETURN 0'", 2, true);
	    addrTable.exitLocalScope();
		addNode(ctx, "Exit local scope", 0, false);
	    currentLocale = Address.GLOBAL;
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code func}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFunc(FunParser.FuncContext ctx) {
	    String id = ctx.ID().getText();
	    Address procaddr = new Address(currentOffset, Address.CODE);
		addNode(ctx, "Insert '" + id + "' into the address table at address " + currentOffset + " (scope: code)", 0, false);
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
		addNode(ctx, "Enter local scope", 0, false);
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
		addNode(ctx, "Walk formal-decl, generating code", 0, false);
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code", 0, false);
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
		addNode(ctx, "Walk com, generating code", 0, false);
	    visit(ctx.seq_com());
		addNode(ctx, "Walk return expr, generating code", 0, false);
        visit(ctx.expr());
		addNode(ctx, "Emit 'RETURN 1'", 2, true);
	    addrTable.exitLocalScope();
		addNode(ctx, "Exit local scope", 0, false);
	    currentLocale = Address.GLOBAL;
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code formal}
	 * labeled alternative in {@link FunParser#formal_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFormal(FunParser.FormalContext ctx) {
	    FunParser.TypeContext tc = ctx.type();
	    if (tc != null) {
			String id = ctx.ID().getText();
			addNode(ctx, "Insert '" + id + "' into the address table at address " + localvaraddr + " (scope: local)", 0, false);
			addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
			addNode(ctx, "Emit 'COPYARG 1'", 2, true);
	    } else {
			addNode(ctx, "Note: no formal parameters", 0, false);
		}
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code var}
	 * labeled alternative in {@link FunParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitVar(FunParser.VarContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    switch (currentLocale) {
		    case Address.LOCAL:
				addNode(ctx, "Insert '" + id + "' into the address table at address " + localvaraddr + " (scope: local)", 0, false);
				addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
				break;
		    case Address.GLOBAL:
				addNode(ctx, "Insert '" + id + "' into the address table at address " + globalvaraddr + " (scope: global)", 0, false);
				addrTable.put(id, new Address(globalvaraddr++, Address.GLOBAL));
				break;
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitBool(FunParser.BoolContext ctx) {
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitInt(FunParser.IntContext ctx) {
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code assn}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitAssn(FunParser.AssnContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + varaddr.offset, 0, false);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit STOREG " + varaddr.offset, 3, true);
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit STOREL " + varaddr.offset, 3, true);
				break;
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proccall}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProccall(FunParser.ProccallContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address procaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + procaddr.offset, 0, false);
	    // Assume procaddr.locale == CODE.
		addNode(ctx, "Emit 'CALL " + procaddr.offset + "'", 3, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitIf(FunParser.IfContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.expr());
	    int condaddr = currentOffset;
		addNode(ctx, "Note the current instruction address, c1 (" + condaddr + ")", 0, false);
		addNode(ctx, "Emit 'JUMPF 0'", 3, true);
	    if (ctx.c2 == null) {
			addNode(ctx, "Walk com, generating code", 0, false);
			visit(ctx.c1);
			int exitaddr = currentOffset;
			addNode(ctx, "Note the current instruction address, c2 (" + exitaddr + ")", 0, false);
			addNode(ctx, "Patch c2 (" + exitaddr + ") into the jump at c1 (" + condaddr + ")", 0, false);
	    } else {
			addNode(ctx, "Walk com1, generating code", 0, false);
			visit(ctx.c1);
			int jumpaddr = currentOffset;
			addNode(ctx, "Note the current instruction address, c2 (" + jumpaddr + ")", 0, false);
			addNode(ctx, "Emit 'JUMP 0'", 3, true);
			int elseaddr = currentOffset;
			addNode(ctx, "Note the current instruction address, c3 (" + elseaddr + ")", 0, false);
			addNode(ctx, "Patch c3 (" + elseaddr + ") into the jump at c1 (" + condaddr + ")", 0, false);
			addNode(ctx, "Walk com2, generating code", 0, false);
			visit(ctx.c2);
			int exitaddr = currentOffset;
			addNode(ctx, "Note the current instruction address, c4 (" + exitaddr + ")", 0, false);
			addNode(ctx, "Patch c4 (" + exitaddr + ") into the jump at c2 (" + jumpaddr + ")", 0, false);
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitWhile(FunParser.WhileContext ctx) {
	    int startaddr = currentOffset;
		addNode(ctx, "Note the current instruction address, c1 (" + startaddr + ")", 0, false);
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.expr());
	    int condaddr = currentOffset;
		addNode(ctx, "Note the current instruction address, c2 (" + condaddr + ")", 0, false);
		addNode(ctx, "Emit 'JUMPF 0'", 3, true);
		addNode(ctx, "Walk com, generating code", 0, false);
	    visit(ctx.seq_com());
		addNode(ctx, "Emit 'JUMP c1 (" + startaddr + ")'", 3, true);
	    int exitaddr = currentOffset;
		addNode(ctx, "Note the current instruction address, c3 (" + exitaddr + ")", 0, false);
		addNode(ctx, "Patch c3 (" + exitaddr + ") into the jump at c2 (" + condaddr + ")", 0, false);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSeq(FunParser.SeqContext ctx) {
		addNode(ctx, "Walk com, generating code", 0, false);
	    visitChildren(ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitExpr(FunParser.ExprContext ctx) {
	    if (ctx.e2 != null) {
			addNode(ctx.op, "Walk expr1, generating code", 0, false);
			visit(ctx.e1);
			addNode(ctx.op, "Walk expr2, generating code", 0, false);
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.EQ:
					addNode(ctx.op, "Emit 'CMPEQ'", 1, true);
				    break;
				case FunParser.LT:
					addNode(ctx.op, "Emit 'LT'", 1, true);
				    break;
				case FunParser.GT:
					addNode(ctx.op, "Emit 'GT'", 1, true);
				    break;
			}
	    } else {
			visit(ctx.e1);
		}
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#sec_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSec_expr(FunParser.Sec_exprContext ctx) {
	    if (ctx.e2 != null) {
			addNode(ctx.op, "Walk expr1, generating code", 0, false);
			visit(ctx.e1);
			addNode(ctx.op, "Walk expr2, generating code", 0, false);
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.PLUS:
					addNode(ctx.op, "Emit 'ADD'", 1, true);
				    break;
				case FunParser.MINUS:
					addNode(ctx.op, "Emit 'SUB'", 1, true);
				    break;
				case FunParser.TIMES:
					addNode(ctx.op, "Emit 'MUL'", 1, true);
				    break;
				case FunParser.DIV:
					addNode(ctx.op, "Emit 'DIV'", 1, true);
				    break;
			}
	    } else {
			visit(ctx.e1);
		}
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFalse(FunParser.FalseContext ctx) {
		addNode(ctx, "Emit 'LOADC 0'", 3, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitTrue(FunParser.TrueContext ctx) {
		addNode(ctx, "Emit 'LOADC 1'", 3, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNum(FunParser.NumContext ctx) {
	    int value = Integer.parseInt(ctx.NUM().getText());
		addNode(ctx, "Emit 'LOADC " + value + "'", 3, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitId(FunParser.IdContext ctx) {
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + varaddr.offset, 0, false);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit 'LOADG " + varaddr.offset + "'", 3, true);
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit 'LOADC " + varaddr.offset + "'", 3, true);
				break;
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code funccall}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFunccall(FunParser.FunccallContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address funcaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + funcaddr.offset, 0, false);
	    // Assume funcaddr.locale == CODE.
		addNode(ctx, "Emit 'CALL " + funcaddr.offset + "'", 3, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNot(FunParser.NotContext ctx) {
		addNode(ctx, "Walk expr, generating code", 0, false);
	    visit(ctx.prim_expr());
		addNode(ctx, "Emit 'INV'", 1, true);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitParens(FunParser.ParensContext ctx) {
	    visit(ctx.expr());
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#actual}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitActual(FunParser.ActualContext ctx) {
	    FunParser.ExprContext ec = ctx.expr();
	    if (ec != null) {
			visit(ec);
	    }
	    return null;
	}

}
