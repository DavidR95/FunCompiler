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

	private void addNode(Object ctx, String explanation) {
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
		addNode(ctx, "Predefine the read and write procedures");
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
	    int calladdr = SVM.currentOffset();
		addNode(ctx, "Note the current instruction address, c1 (" + calladdr + ")");
	    SVM.emit12();
		addNode(ctx, "Emit 'CALL 0'");
	    SVM.emit1();
		addNode(ctx, "Emit 'HALT'");
	    List<FunParser.Proc_declContext> proc_decl = ctx.proc_decl();
		if (!proc_decl.isEmpty()) {
			addNode(ctx, "Walk proc-decl, generating code");
		    for (FunParser.Proc_declContext pd : proc_decl)
				visit(pd);
		}
	    int mainaddr = addrTable.get("main").offset;
		addNode(ctx, "Lookup 'main' and retrieve its address, " + mainaddr);
		addNode(ctx, "Patch address of 'main' (" + mainaddr + ") into the call at c1 (" + calladdr + ")");
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
	    Address procaddr = new Address(SVM.currentOffset(), Address.CODE);
		addNode(ctx, "Insert '" + id + "' into the address table at address " + SVM.currentOffset() + " (scope: code)");
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
		addNode(ctx, "Enter local scope");
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
		addNode(ctx, "Walk formal-decl, generating code");
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if(!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
		addNode(ctx, "Walk com, generating code");
	    visit(ctx.seq_com());
	    SVM.emit11();
		addNode(ctx, "Emit 'RETURN 0'");
	    addrTable.exitLocalScope();
		addNode(ctx, "Exit local scope");
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
	    Address procaddr = new Address(SVM.currentOffset(), Address.CODE);
		addNode(ctx, "Insert '" + id + "' into the address table at address " + SVM.currentOffset() + " (scope: code)");
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
		addNode(ctx, "Enter local scope");
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
		addNode(ctx, "Walk formal-decl, generating code");
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
		addNode(ctx, "Walk com, generating code");
	    visit(ctx.seq_com());
		addNode(ctx, "Walk return expr, generating code");
        visit(ctx.expr());
	    SVM.emit11();
		addNode(ctx, "Emit 'RETURN 1'");
	    addrTable.exitLocalScope();
		addNode(ctx, "Exit local scope");
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
			addNode(ctx, "Insert '" + id + "' into the address table at address " + localvaraddr + " (scope: local)");
			addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
			SVM.emit11();
			addNode(ctx, "Emit 'COPYARG 1'");
	    } else {
			addNode(ctx, "Note: no formal parameters");
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
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    switch (currentLocale) {
		    case Address.LOCAL:
				addNode(ctx, "Insert '" + id + "' into the address table at address " + localvaraddr + " (scope: local)");
				addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
				break;
		    case Address.GLOBAL:
				addNode(ctx, "Insert '" + id + "' into the address table at address " + globalvaraddr + " (scope: global)");
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
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + varaddr.offset);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit STOREG " + varaddr.offset);
				SVM.emit12();
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit STOREL " + varaddr.offset);
				SVM.emit12();
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
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address procaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + procaddr.offset);
	    // Assume procaddr.locale == CODE.
		addNode(ctx, "Emit 'CALL " + procaddr.offset + "'");
	    SVM.emit12();
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitIf(FunParser.IfContext ctx) {
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.expr());
	    int condaddr = SVM.currentOffset();
		addNode(ctx, "Note the current instruction address, c1 (" + condaddr + ")");
	    SVM.emit12();
		addNode(ctx, "Emit 'JUMPF 0'");
	    if (ctx.c2 == null) {
			addNode(ctx, "Walk com, generating code");
			visit(ctx.c1);
			int exitaddr = SVM.currentOffset();
			addNode(ctx, "Note the current instruction address, c2 (" + exitaddr + ")");
			addNode(ctx, "Patch c2 (" + exitaddr + ") into the jump at c1 (" + condaddr + ")");
	    } else {
			addNode(ctx, "Walk com1, generating code");
			visit(ctx.c1);
			int jumpaddr = SVM.currentOffset();
			addNode(ctx, "Note the current instruction address, c2 (" + jumpaddr + ")");
			SVM.emit12();
			addNode(ctx, "Emit 'JUMP 0'");
			int elseaddr = SVM.currentOffset();
			addNode(ctx, "Note the current instruction address, c3 (" + elseaddr + ")");
			addNode(ctx, "Patch c3 (" + elseaddr + ") into the jump at c1 (" + condaddr + ")");
			addNode(ctx, "Walk com2, generating code");
			visit(ctx.c2);
			int exitaddr = SVM.currentOffset();
			addNode(ctx, "Note the current instruction address, c4 (" + exitaddr + ")");
			addNode(ctx, "Patch c4 (" + exitaddr + ") into the jump at c2 (" + jumpaddr + ")");
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
	    int startaddr = SVM.currentOffset();
		addNode(ctx, "Note the current instruction address, c1 (" + startaddr + ")");
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.expr());
	    int condaddr = SVM.currentOffset();
		addNode(ctx, "Note the current instruction address, c2 (" + condaddr + ")");
		addNode(ctx, "Emit 'JUMPF 0'");
	    SVM.emit12();
		addNode(ctx, "Walk com, generating code");
	    visit(ctx.seq_com());
		addNode(ctx, "Emit 'JUMP c1 (" + startaddr + ")'");
	    SVM.emit12();
	    int exitaddr = SVM.currentOffset();
		addNode(ctx, "Note the current instruction address, c3 (" + exitaddr + ")");
		addNode(ctx, "Patch c3 (" + exitaddr + ") into the jump at c2 (" + condaddr + ")");
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSeq(FunParser.SeqContext ctx) {
		addNode(ctx, "Walk com, generating code");
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
			addNode(ctx.op, "Walk expr1, generating code");
			visit(ctx.e1);
			addNode(ctx.op, "Walk expr2, generating code");
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.EQ:
					addNode(ctx.op, "Emit 'CMPEQ'");
				    SVM.emit1();
				    break;
				case FunParser.LT:
					addNode(ctx.op, "Emit 'LT'");
				    SVM.emit1();
				    break;
				case FunParser.GT:
					addNode(ctx.op, "Emit 'GT'");
				    SVM.emit1();
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
			addNode(ctx.op, "Walk expr1, generating code");
			visit(ctx.e1);
			addNode(ctx.op, "Walk expr2, generating code");
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.PLUS:
					addNode(ctx.op, "Emit 'ADD'");
				    SVM.emit1();
				    break;
				case FunParser.MINUS:
					addNode(ctx.op, "Emit 'SUB'");
				    SVM.emit1();
				    break;
				case FunParser.TIMES:
					addNode(ctx.op, "Emit 'MUL'");
				    SVM.emit1();
				    break;
				case FunParser.DIV:
					addNode(ctx.op, "Emit 'DIV'");
				    SVM.emit1();
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
		addNode(ctx, "Emit 'LOADC 0'");
	    SVM.emit12();
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitTrue(FunParser.TrueContext ctx) {
		addNode(ctx, "Emit 'LOADC 1'");
	    SVM.emit12();
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
		addNode(ctx, "Emit 'LOADC " + value + "'");
	    SVM.emit12();
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
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + varaddr.offset);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit 'LOADG " + varaddr.offset + "'");
				SVM.emit12();
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit 'LOADC " + varaddr.offset + "'");
				SVM.emit12();
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
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address funcaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + funcaddr.offset);
	    // Assume funcaddr.locale == CODE.
		addNode(ctx, "Emit 'CALL " + funcaddr.offset + "'");
	    SVM.emit12();
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNot(FunParser.NotContext ctx) {
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.prim_expr());
		addNode(ctx, "Emit 'INV'");
	    SVM.emit1();
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
