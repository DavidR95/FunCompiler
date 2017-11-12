package api;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;

import java.util.*;

public class FunASTVisitor extends AbstractParseTreeVisitor<Type> implements FunVisitor<Type> {

    private Parser parser;
    private Stack<String> parentNodes = new Stack<String>();

    public FunASTVisitor(Parser parser) {
        this.parser = parser;
    }

    /**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProg(FunParser.ProgContext ctx) {
        parentNodes.push("PROG");
        System.err.println("PROG");
	    visitChildren(ctx);
        parentNodes.pop();
	    return null;
	}

    /**
     * Visit a parse tree produced by the {@code proc}
     * labeled alternative in {@link FunParser#proc_decl}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitProc(FunParser.ProcContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code func}
     * labeled alternative in {@link FunParser#proc_decl}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitFunc(FunParser.FuncContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code formal}
     * labeled alternative in {@link FunParser#formal_decl}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitFormal(FunParser.FormalContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code var}
     * labeled alternative in {@link FunParser#var_decl}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitVar(FunParser.VarContext ctx) {
        System.err.println("VAR, parent: " + parentNodes.peek());
        parentNodes.push("VAR");
        visit(ctx.type());
        System.err.println(ctx.ID() + ", parent: " + parentNodes.peek());
        visit(ctx.expr());
        parentNodes.pop();
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code bool}
     * labeled alternative in {@link FunParser#type}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitBool(FunParser.BoolContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code int}
     * labeled alternative in {@link FunParser#type}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitInt(FunParser.IntContext ctx) {
        System.err.println("INT, parent: " + parentNodes.peek());
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code assn}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitAssn(FunParser.AssnContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code proccall}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitProccall(FunParser.ProccallContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code if}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitIf(FunParser.IfContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code while}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitWhile(FunParser.WhileContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code seq}
     * labeled alternative in {@link FunParser#seq_com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitSeq(FunParser.SeqContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by {@link FunParser#expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitExpr(FunParser.ExprContext ctx) {
        visit(ctx.e1);
        if (ctx.e2 != null) {
        visit(ctx.e2);
        }
        return null;
    }

    /**
     * Visit a parse tree produced by {@link FunParser#sec_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitSec_expr(FunParser.Sec_exprContext ctx) {
        visit(ctx.e1);
        if (ctx.e2 != null) {
        visit(ctx.e2);
        }
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code false}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitFalse(FunParser.FalseContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code true}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitTrue(FunParser.TrueContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code num}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitNum(FunParser.NumContext ctx) {
        System.err.println(ctx.getText() + ", parent: " + parentNodes.peek());
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code id}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitId(FunParser.IdContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code funccall}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitFunccall(FunParser.FunccallContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code not}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitNot(FunParser.NotContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code parens}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitParens(FunParser.ParensContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by {@link FunParser#actual}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Type visitActual(FunParser.ActualContext ctx) {
        return null;
    }
}
