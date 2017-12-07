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

	private SVM obj = new SVM();

	private int globalvaraddr = 0;
	private int localvaraddr = 0;
	private int currentLocale = Address.GLOBAL;

	private SymbolTable<Address> addrTable = new SymbolTable<Address>();

	private void predefine () {
	// Add predefined procedures to the address table.
		addrTable.put("read", new Address(SVM.READOFFSET, Address.CODE));
		addrTable.put("write", new Address(SVM.WRITEOFFSET, Address.CODE));
	}

	public SVM getSVM() {
	    return obj;
	}

	private JsonArray nodeOrder = new JsonArray();

	private static final Map<Integer, String> convertLocale = createConverter();
    private static Map<Integer, String> createConverter() {
        Map<Integer,String> convertLocale = new HashMap<Integer,String>();
        convertLocale.put(0, "code");
        convertLocale.put(1, "global");
		convertLocale.put(2, "local");
        return convertLocale;
    }

	private Map<Integer,LinkedList<String>> codeTemplates = new HashMap<Integer,LinkedList<String>>();

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
		JsonArray codeTemplateArray = new JsonArray();
		for (String codeTemplateString : codeTemplates.get(contextHash)) {
			codeTemplateArray.add(new JsonPrimitive(codeTemplateString));
		}
		JsonArray addrTableArray = new JsonArray();
		addrTable.getGlobals().forEach((id,addr) -> {
			JsonObject addrTableObject = new JsonObject();
			addrTableObject.addProperty("scope", convertLocale.get(addr.locale));
			addrTableObject.addProperty("id", id);
			addrTableObject.addProperty("type_address", Integer.toString(addr.offset));
			addrTableArray.add(addrTableObject);
		});
		addrTable.getLocals().forEach((id,addr) -> {
			JsonObject addrTableObject = new JsonObject();
			addrTableObject.addProperty("scope", convertLocale.get(addr.locale));
			addrTableObject.addProperty("id", id);
			addrTableObject.addProperty("type_address", Integer.toString(addr.offset));
			addrTableArray.add(addrTableObject);
		});
		nodeObject.addProperty("id", contextHash);
		nodeObject.add("explanations", explanationArray);
		nodeObject.add("codeTemplate", codeTemplateArray);
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"Code to evaluate variable declarations",
				"CALL",
				"HALT",
				"Code to evaulate procedure declarations"
			)
		));
	    predefine();
		addNode(ctx, "Predefine the read and write procedures");
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
	    int calladdr = obj.currentOffset();
		addNode(ctx, "Note the current instruction address, c1 (" + calladdr + ")");
	    obj.emit12(SVM.CALL, 0);
		addNode(ctx, "Emit 'CALL 0'");
	    obj.emit1(SVM.HALT);
		addNode(ctx, "Emit 'HALT'");
	    List<FunParser.Proc_declContext> proc_decl = ctx.proc_decl();
		if (!proc_decl.isEmpty()) {
			addNode(ctx, "Walk proc-decl, generating code");
		    for (FunParser.Proc_declContext pd : proc_decl)
				visit(pd);
		}
	    int mainaddr = addrTable.get("main").offset;
		addNode(ctx, "Lookup 'main' and retrieve its address, " + mainaddr);
	    obj.patch12(calladdr, mainaddr);
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"Code to evaluate formal declarations",
				"Code to evaluate variable declarations",
				"RETURN"
			)
		));
	    String id = ctx.ID().getText();
	    Address procaddr = new Address(obj.currentOffset(), Address.CODE);
		addNode(ctx, "Insert '" + id + "' into the address table at address " + obj.currentOffset() + " (scope: code)");
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
	    if (fd != null) {
			addNode(ctx, "Walk formal-decl, generating code");
			visit(fd);
		}
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if(!var_decl.isEmpty()) {
			addNode(ctx, "Walk var-decl, generating code");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
		addNode(ctx, "Walk com, generating code");
	    visit(ctx.seq_com());
	    obj.emit11(SVM.RETURN, 0);
		addNode(ctx, "Emit 'RETURN 0'");
	    addrTable.exitLocalScope();
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
	    Address procaddr = new Address(obj.currentOffset(), Address.CODE);
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_declContext fd = ctx.formal_decl();
	    if (fd != null)
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    visit(ctx.seq_com());
            visit(ctx.expr());
	    obj.emit11(SVM.RETURN, 1);
	    addrTable.exitLocalScope();
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"COPYARG"
			)
		));
	    FunParser.TypeContext tc = ctx.type();
	    if (tc != null) {
			String id = ctx.ID().getText();
			addNode(ctx, "Insert '" + id + "' into the address table at address " + localvaraddr + " (scope: local)");
			addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
			obj.emit11(SVM.COPYARG, 1);
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"Code to evaluate expr"
			)
		));
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"No code template"
			)
		));
		addNode(ctx, "BOOL");
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitInt(FunParser.IntContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"No code template"
			)
		));
		addNode(ctx, "INT");
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code assn}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitAssn(FunParser.AssnContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"Code to evaluate expr",
				"STOREG d or STOREL d"
			)
		));
		addNode(ctx, "Walk expr generating code");
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
		addNode(ctx, "Lookup '" + id + "' and retrieve its address, " + varaddr.offset);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit STOREG " + varaddr.offset);
				obj.emit12(SVM.STOREG,varaddr.offset);
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit STOREL " + varaddr.offset);
				obj.emit12(SVM.STOREL,varaddr.offset);
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
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address procaddr = addrTable.get(id);
	    // Assume procaddr.locale == CODE.
	    obj.emit12(SVM.CALL,procaddr.offset);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitIf(FunParser.IfContext ctx) {
	    visit(ctx.expr());
	    int condaddr = obj.currentOffset();
	    obj.emit12(SVM.JUMPF, 0);
	    if (ctx.c2 == null) { // IF without ELSE
		visit(ctx.c1);
		int exitaddr = obj.currentOffset();
		obj.patch12(condaddr, exitaddr);
	    }
	    else {                // IF ... ELSE
		visit(ctx.c1);
		int jumpaddr = obj.currentOffset();
		obj.emit12(SVM.JUMP, 0);
		int elseaddr = obj.currentOffset();
		obj.patch12(condaddr, elseaddr);
		visit(ctx.c2);
		int exitaddr = obj.currentOffset();
		obj.patch12(jumpaddr, exitaddr);
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"Code to evaluate expr",
				"JUMPF",
				"Code to evaluate com",
				"JUMP"
			)
		));
	    int startaddr = obj.currentOffset();
		addNode(ctx, "Note the current instruction address, c1 (" + startaddr + ")");
		addNode(ctx, "Walk expr, generating code");
	    visit(ctx.expr());
	    int condaddr = obj.currentOffset();
		addNode(ctx, "Note the current instruction address, c2 (" + condaddr + ")");
		addNode(ctx, "Emit 'JUMPF 0'");
	    obj.emit12(SVM.JUMPF, 0);
		addNode(ctx, "Walk com, generating code");
	    visit(ctx.seq_com());
		addNode(ctx, "Emit 'JUMP c1 (" + startaddr + ")'");
	    obj.emit12(SVM.JUMP, startaddr);
	    int exitaddr = obj.currentOffset();
		addNode(ctx, "Note the current instruction address, c3 (" + exitaddr + ")");
		addNode(ctx, "Patch c3 (" + exitaddr + ") into the jump at c2 (" + condaddr + ")");
	    obj.patch12(condaddr, exitaddr);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSeq(FunParser.SeqContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"No code template"
			)
		));
		addNode(ctx, "Visit the sequential command");
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
			codeTemplates.put(ctx.op.hashCode(), new LinkedList<String>(
				Arrays.asList(
					"Code to evaluate expr1",
					"Code to evaluate expr2"
				)
			));
			addNode(ctx.op, "Visit the first expression");
			visit(ctx.e1);
			addNode(ctx.op, "Visit the second expression");
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.EQ:
					codeTemplates.get(ctx.op.hashCode()).add("CMPEQ");
					addNode(ctx.op, "Emit 'CMPEQ'");
				    obj.emit1(SVM.CMPEQ);
				    break;
				case FunParser.LT:
					codeTemplates.get(ctx.op.hashCode()).add("LT");
					addNode(ctx.op, "Emit 'LT'");
				    obj.emit1(SVM.CMPLT);
				    break;
				case FunParser.GT:
					codeTemplates.get(ctx.op.hashCode()).add("GT");
					addNode(ctx.op, "Emit 'GT'");
				    obj.emit1(SVM.CMPGT);
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
			codeTemplates.put(ctx.op.hashCode(), new LinkedList<String>(
				Arrays.asList(
					"Code to evaluate expr1",
					"Code to evaluate expr2"
				)
			));
			addNode(ctx.op, "Visit the first expression");
			visit(ctx.e1);
			addNode(ctx.op, "Visit the second expression");
			visit(ctx.e2);
			switch (ctx.op.getType()) {
				case FunParser.PLUS:
					codeTemplates.get(ctx.op.hashCode()).add("ADD");
					addNode(ctx.op, "Emit 'ADD'");
				    obj.emit1(SVM.ADD);
				    break;
				case FunParser.MINUS:
					codeTemplates.get(ctx.op.hashCode()).add("SUB");
					addNode(ctx.op, "Emit 'SUB'");
				    obj.emit1(SVM.SUB);
				    break;
				case FunParser.TIMES:
					codeTemplates.get(ctx.op.hashCode()).add("MUL");
					addNode(ctx.op, "Emit 'MUL'");
				    obj.emit1(SVM.MUL);
				    break;
				case FunParser.DIV:
					codeTemplates.get(ctx.op.hashCode()).add("DIV");
					addNode(ctx.op, "Emit 'DIV'");
				    obj.emit1(SVM.DIV);
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
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"LOADC"
			)
		));
		addNode(ctx, "Emit 'LOADC 0'");
	    obj.emit12(SVM.LOADC, 0);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitTrue(FunParser.TrueContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"LOADC"
			)
		));
		addNode(ctx, "Emit 'LOADC 1'");
	    obj.emit12(SVM.LOADC, 1);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNum(FunParser.NumContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"LOADC"
			)
		));
	    int value = Integer.parseInt(ctx.NUM().getText());
		addNode(ctx, "Emit 'LOADC " + value + "'");
	    obj.emit12(SVM.LOADC, value);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitId(FunParser.IdContext ctx) {
		codeTemplates.put(ctx.hashCode(), new LinkedList<String>(
			Arrays.asList(
				"LOADG d or LOADC d"
			)
		));
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
		addNode(ctx, "Retrieve " + id + " fron the address table and get address: " + varaddr.offset);
	    switch (varaddr.locale) {
		    case Address.GLOBAL:
				addNode(ctx, "Emit 'LOADG " + varaddr.offset + "'");
				obj.emit12(SVM.LOADG,varaddr.offset);
				break;
		    case Address.LOCAL:
				addNode(ctx, "Emit 'LOADC " + varaddr.offset + "'");
				obj.emit12(SVM.LOADL,varaddr.offset);
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
	    visit(ctx.actual());
	    String id = ctx.ID().getText();
	    Address funcaddr = addrTable.get(id);
	    // Assume that funcaddr.locale == CODE.
	    obj.emit12(SVM.CALL,funcaddr.offset);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNot(FunParser.NotContext ctx) {
	    visit(ctx.prim_expr());
	    obj.emit1(SVM.INV);
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
