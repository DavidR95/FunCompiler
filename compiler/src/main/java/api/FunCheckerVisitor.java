package api;

//////////////////////////////////////////////////////////////
//
// A visitor for contextual analysis of Fun.
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FunCheckerVisitor extends AbstractParseTreeVisitor<Type> implements FunVisitor<Type> {

	// An ArrayList of Strings, each entry holding an error
	private List<String> contextualErrors = new LinkedList<String>();

	private int errorCount = 0;

	private JsonArray animationOrder = new JsonArray();

	private CommonTokenStream tokens;

	public FunCheckerVisitor(CommonTokenStream toks) {
	    tokens = toks;
	}

	private void reportError (String message, ParserRuleContext ctx) {
		// Print an error message relating to the given
		// part of the AST.
	    Interval interval = ctx.getSourceInterval();
	    Token start = tokens.get(interval.a);
	    Token finish = tokens.get(interval.b);
	    int startLine = start.getLine();
	    int startCol = start.getCharPositionInLine();
	    int finishLine = finish.getLine();
	    int finishCol = finish.getCharPositionInLine();
		// Add the error to the contextual errors ArrayList
	    contextualErrors.add(startLine + ":" + startCol + "-" +
                             finishLine + ":" + finishCol
		   		   		     + " " + message);
		errorCount++;
	}

	// Return the total number of errors so far detected.
	public int getNumberOfContextualErrors () {
		return errorCount;
	}

	// Return the actual contextual errors
	public List<String> getContextualErrors() {
		return contextualErrors;
	}

	// Clear the contextual errors ArrayList
	public void reset() {
		contextualErrors.clear();
	}

	private void addExplanation(Object ctx, String explanation) {
		JsonObject animationObject = new JsonObject();
		animationObject.addProperty("id", ctx.hashCode());
		animationObject.addProperty("explanation", explanation);
		animationOrder.add(animationObject);
	}

	public JsonArray getAnimationOrder() {
		return animationOrder;
	}

	//-- Scope checking --//

	private SymbolTable<Type> typeTable = new SymbolTable<Type>();

	/**
	 * Add predefined procedures to the type table.
	 */
	private void predefine () {
		typeTable.put("read", new Type.Mapping(Type.VOID, Type.INT));
		typeTable.put("write", new Type.Mapping(Type.INT, Type.VOID));
	}

	/**
   	 * Add an id with its type to the type table, checking
	 * that id is not already declared in the same scope.
   	 * @param id the id of the variable
	 * @param type the type of the variable
   	 * @param decl the parse tree
   	 */
	private void define (String id, Type type, ParserRuleContext decl) {
		addExplanation(decl, "Attempting to add " + id + " to the type table along with its type, " + type);
		boolean ok = typeTable.put(id, type);
		if (ok) {
			addExplanation(decl, type + " " + id + " successfully added to the type table");
		} else {
			addExplanation(decl, "Type error, " + id + " is already declared");
			reportError(id + " is redeclared", decl);
		}
	}

	/**
   	 * Retrieve the id's type from the type table.
   	 * @param id the id of the variable
   	 * @param occ the parse tree
   	 * @param visit the occurence of a visit a message should be associated
	 * @return the type
   	 */
	private Type retrieve (String id, ParserRuleContext occ) {
		addExplanation(occ, "Attempting to retrieve " + id + " from the type table");
		Type type = typeTable.get(id);
		if (type == null) {
			addExplanation(occ, "Type error, " + id + " is undeclared");
			reportError(id + " is undeclared", occ);
			return Type.ERROR;
		} else
			addExplanation(occ, id + " successfully retrieved from the type table");
			return type;
	}

	//-- Type checking --//

	private static final Type.Mapping
	   NOTTYPE = new Type.Mapping(Type.BOOL, Type.BOOL),
	   COMPTYPE = new Type.Mapping(
	      new Type.Pair(Type.INT, Type.INT), Type.BOOL),
	   ARITHTYPE = new Type.Mapping(
	      new Type.Pair(Type.INT, Type.INT), Type.INT),
	   MAINTYPE = new Type.Mapping(Type.VOID, Type.VOID);

	/**
   	 * Checks that a constuct's actual type matches the
	 * expected type.
   	 * @param typeExpected the expected type of the contruct
   	 * @param typeActual the actual type of the construct
   	 * @param construct the parse tree
   	 */
	private void checkType (Type typeExpected, Type typeActual,
	                        ParserRuleContext construct) {
		if (typeActual.equiv(typeExpected)) {
			addExplanation(construct, "Success, " + typeActual + " and " + typeExpected + " are of the same type");
		} else {
			addExplanation(construct, "Type error, type is " + typeActual + ", should be " + typeExpected);
			reportError("type is " + typeActual+ ", should be " + typeExpected,
				construct);
		}
	}

	/**
	 * Check that a procedure call identifies a procedure
	 * and that its argument type matches the procedure's
	 * type. Return the type of the procedure call.
	 * @param id the name of the procedure
	 * @param typeArg the type of the argument
	 * @param call the parse tree
	 * @return the range of the mapping
	 */
	private Type checkCall (String id, Type typeArg,
	                        ParserRuleContext call) {
		Type typeProc = retrieve(id, call);
		if (!(typeProc instanceof Type.Mapping)) {
			reportError(id + " is not a procedure", call);
			return Type.ERROR;
		} else {
			Type.Mapping mapping = (Type.Mapping)typeProc;
			checkType(mapping.domain, typeArg, call);
			return mapping.range;
		}
	}

	/**
	 * Check that a unary operator operand's type matches
	 * the operator's type. Return the type of the operator
	 * @param typeOp the type of the operator
	 * @param typeArg the type of the argument
	 * @param op the parse tree
	 * @return the range of the mapping
	 */
	private Type checkUnary (Type.Mapping typeOp, Type typeArg,
	                         ParserRuleContext op) {
		if (!(typeOp.domain instanceof Type.Primitive))
			reportError("unary operator should have 1 operand", op);
		else
			checkType(typeOp.domain, typeArg, op);
		return typeOp.range;
	}

	/**
	 * Check that a binary operator's operand types match the
	 * operator's type. Return the type of the operator application.
	 * @param typeOp the type of the operator
	 * @param typeArg1 the type of the first argument
	 * @param typeArg2 the type of the second argument
	 * @param op the parse tree
	 * @return the range of the mapping
	 */
	private Type checkBinary (Type.Mapping typeOp, Type typeArg1,
							  Type typeArg2, ParserRuleContext op) {
		if (!(typeOp.domain instanceof Type.Pair))
			reportError("binary operator should have 2 operands", op);
		else {
			Type.Pair pair = (Type.Pair)typeOp.domain;
			checkType(pair.first, typeArg1, op);
			checkType(pair.second, typeArg2, op);
		}
		return typeOp.range;
	}

	//-- Visitors --//

	/**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProg(FunParser.ProgContext ctx) {
		addExplanation(ctx, "Predefine read and write procedures");
		predefine();
		addExplanation(ctx, "Visit children");
	    visitChildren(ctx);
		addExplanation(ctx, "Check a main procedure has been declared");
	    Type tmain = retrieve("main", ctx);
		addExplanation(ctx, "Check main procedure is a void -> void procedure");
	    checkType(MAINTYPE, tmain, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proc}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	 // NOT SURE THIS IS WORKING PROPERLY IN REGARDS TO IT VISITING FORMAL DECLARATION WHEN IT SHOULDN'T
	public Type visitProc(FunParser.ProcContext ctx) {
		addExplanation(ctx, "Enter the local scope of the procedure");
	    typeTable.enterLocalScope();
	    Type t;
	    FunParser.Formal_declContext fd = ctx.formal_decl();
	    if (fd != null) {
			addExplanation(ctx, "Visit the formal declaration");
			t = visit(fd);
	    } else
			t = Type.VOID;
	    Type proctype = new Type.Mapping(t, Type.VOID);
		addExplanation(ctx, "Retrieve the ID of the procedure");
		addExplanation(ctx.ID(), ctx.ID().getText());
	    define(ctx.ID().getText(), proctype, ctx);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
		if (!var_decl.isEmpty()) {
			addExplanation(ctx, "Visit all variable declarations");
		    for (FunParser.Var_declContext vd : var_decl)
				visit(vd);
		}
	    visit(ctx.seq_com());
		addExplanation(ctx, "Exit the local scope of the procedure");
	    typeTable.exitLocalScope();
	    define(ctx.ID().getText(), proctype, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code func}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFunc(FunParser.FuncContext ctx) {
	    typeTable.enterLocalScope();
	    Type t1 = visit(ctx.type());
	    Type t2;
	    FunParser.Formal_declContext fd = ctx.formal_decl();
	    if (fd != null) {
			t2 = visit(fd);
	    } else
			t2 = Type.VOID;
	    Type functype = new Type.Mapping(t2, t1);
	    define(ctx.ID().getText(), functype, ctx);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl) {
			visit(vd);
		}
	    visit(ctx.seq_com());
	    Type returntype = visit(ctx.expr());
	    checkType(t1, returntype, ctx);
	    typeTable.exitLocalScope();
	    define(ctx.ID().getText(), functype, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code formal}
	 * labeled alternative in {@link FunParser#formal_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFormal(FunParser.FormalContext ctx) {
		addExplanation(ctx, "Not yet implemented");
	    FunParser.TypeContext tc = ctx.type();
	    Type t;
	    if (tc != null) {
			t = visit(tc);
			addExplanation(ctx, "Not yet implemented");
			define(ctx.ID().getText(), t, ctx);
	    }
	    else
			t = Type.VOID;
	    return t;
	}

	/**
	 * Visit a parse tree produced by the {@code var}
	 * labeled alternative in {@link FunParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitVar(FunParser.VarContext ctx) {
		addExplanation(ctx, "Retrieve the declared type");
	    Type t1 = visit(ctx.type());
		addExplanation(ctx, "Type retrieved was: " + t1);
		addExplanation(ctx, "Retrieve the ID of the declaration");
		addExplanation(ctx.ID(), "ID: " + ctx.ID().getText());
		addExplanation(ctx, "ID retrieved was " + ctx.ID().getText());
		define(ctx.ID().getText(), t1, ctx);
		addExplanation(ctx, "Retrieve the type of the expression");
	    Type t2 = visit(ctx.expr());
		addExplanation(ctx, "Type received was: " + t2);
		addExplanation(ctx, "Check declared type (" + t1 + ") is the same as the type of the expression (" + t2 + ")");
	    checkType(t1, t2, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitBool(FunParser.BoolContext ctx) {
		addExplanation(ctx, "Type: bool");
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitInt(FunParser.IntContext ctx) {
		addExplanation(ctx, "Type: int");
	    return Type.INT;
	}

	/**
	 * Visit a parse tree produced by the {@code assn}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitAssn(FunParser.AssnContext ctx) {
		addExplanation(ctx, "Not yet implemented");
		addExplanation(ctx.ID(), "Not yet implemented");
		addExplanation(ctx, "Not yet implemented");
	    Type tvar = retrieve(ctx.ID().getText(), ctx);
	    Type t = visit(ctx.expr());
		addExplanation(ctx, "Not yet implemented");
	    checkType(tvar, t, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proccall}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProccall(FunParser.ProccallContext ctx) {
	    Type t = visit(ctx.actual());
	    Type tres = checkCall(ctx.ID().getText(), t, ctx);
	    if (! tres.equiv(Type.VOID))
		reportError("procedure should be void", ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitIf(FunParser.IfContext ctx) {
	    Type t = visit(ctx.expr());
	    visit(ctx.c1);
	    if (ctx.c2 != null) {
			visit(ctx.c2);
		}
	    checkType(Type.BOOL, t, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitWhile(FunParser.WhileContext ctx) {
		addExplanation(ctx, "Not yet implemented");
	    Type t = visit(ctx.expr());
		addExplanation(ctx, "Not yet implemented");
		checkType(Type.BOOL, t, ctx);
	    visit(ctx.seq_com());
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitSeq(FunParser.SeqContext ctx) {
		addExplanation(ctx, "Not yet implemented");
	    visitChildren(ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitExpr(FunParser.ExprContext ctx) {
	    if (ctx.e2 != null) {
			addExplanation(ctx.op, "Not yet implemented");
			Type t1 = visit(ctx.e1);
			addExplanation(ctx.op, "Not yet implemented");
			Type t2 = visit(ctx.e2);
			addExplanation(ctx.op, "Not yet implemented");
			return checkBinary(COMPTYPE, t1, t2, ctx);
	    } else {
			return visit(ctx.e1);
	    }
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#sec_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitSec_expr(FunParser.Sec_exprContext ctx) {
	    if (ctx.e2 != null) {
			addExplanation(ctx.op, "Not yet implemented");
			Type t1 = visit(ctx.e1);
			addExplanation(ctx.op, "Not yet implemented");
			Type t2 = visit(ctx.e2);
			addExplanation(ctx.op, "Not yet implemented");
			return checkBinary(ARITHTYPE, t1, t2, ctx);
	    } else {
			return visit(ctx.e1);
	    }
	}

	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFalse(FunParser.FalseContext ctx) {
		addExplanation(ctx, "False");
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitTrue(FunParser.TrueContext ctx) {
		addExplanation(ctx, "True");
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitNum(FunParser.NumContext ctx) {
		addExplanation(ctx, "Not yet implemented");
	    return Type.INT;
	}

	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitId(FunParser.IdContext ctx) {
		addExplanation(ctx, "Not yet implemented");
	    return retrieve(ctx.ID().getText(), ctx);
	}

	/**
	 * Visit a parse tree produced by the {@code funccall}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFunccall(FunParser.FunccallContext ctx) {
	    Type t = visit(ctx.actual());
	    Type tres = checkCall(ctx.ID().getText(), t, ctx);
	    if (tres.equiv(Type.VOID))
		reportError("procedure should be non-void", ctx);
	    return tres;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitNot(FunParser.NotContext ctx) {
	    Type t = visit(ctx.prim_expr());
	    return checkUnary(NOTTYPE, t, ctx);
	}

	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitParens(FunParser.ParensContext ctx) {
	    return visit(ctx.expr());
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#actual}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitActual(FunParser.ActualContext ctx) {
	    FunParser.ExprContext ec = ctx.expr();
	    Type t;
	    if (ec != null) {
			t = visit(ec);
	    } else
			t = Type.VOID;
	    return t;
	}

}
