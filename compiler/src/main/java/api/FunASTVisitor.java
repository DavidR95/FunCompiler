package api;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FunASTVisitor extends AbstractParseTreeVisitor<Void> implements FunVisitor<Void> {

    private Parser parser;
    private JsonArray data_array = new JsonArray();
    private Stack<Integer> parentNodes = new Stack<Integer>();

    public FunASTVisitor(Parser parser) {
        this.parser = parser;
    }

    public JsonArray getAST() {
        return data_array;
    }

    private int createJsonObject(Object ctx, String name) {
        JsonObject data_object = new JsonObject();
        int id = ctx.hashCode();
        int parent_id = (parentNodes.empty() ? -1 : parentNodes.peek());
        data_object.addProperty("id", id);
        data_object.addProperty("name", name);
        data_object.addProperty("parent_id", parent_id);
        data_array.add(data_object);
        return id;
    }

    /**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProg(FunParser.ProgContext ctx) {
        createJsonObject(ctx, "PROG");
        parentNodes.push(ctx.hashCode());
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
    public Void visitProc(FunParser.ProcContext ctx) {
        createJsonObject(ctx, "PROC");
        parentNodes.push(ctx.hashCode());
        createJsonObject(ctx.ID(), ctx.ID().getText());
        FunParser.Formal_declContext fd = ctx.formal_decl();
		visit(ctx.formal_decl());
        List<FunParser.Var_declContext> var_decl = ctx.var_decl();
        for (FunParser.Var_declContext vd : var_decl)
            visit(vd);
        visit(ctx.seq_com());
        parentNodes.pop();
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code func}
     * labeled alternative in {@link FunParser#proc_decl}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitFunc(FunParser.FuncContext ctx) {
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
            createJsonObject(ctx, "FORMAL");
            parentNodes.push(ctx.hashCode());
		    visit(tc);
            createJsonObject(ctx.ID(), ctx.ID().getText());
            parentNodes.pop();
	    } else {
            createJsonObject(ctx, "NOFORMAL");
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
        createJsonObject(ctx, "VAR");
        parentNodes.push(ctx.hashCode());
        visit(ctx.type());
        createJsonObject(ctx.ID(), ctx.ID().getText());
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
        createJsonObject(ctx, "INT");
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code assn}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitAssn(FunParser.AssnContext ctx) {
        createJsonObject(ctx, "ASSN");
        parentNodes.push(ctx.hashCode());
        createJsonObject(ctx.ID(), ctx.ID().getText());
	    visit(ctx.expr());
        parentNodes.pop();
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code proccall}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitProccall(FunParser.ProccallContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code if}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitIf(FunParser.IfContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code while}
     * labeled alternative in {@link FunParser#com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitWhile(FunParser.WhileContext ctx) {
        createJsonObject(ctx, "WHILE");
        parentNodes.push(ctx.hashCode());
        visit(ctx.expr());
	    visit(ctx.seq_com());
	    parentNodes.pop();
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code seq}
     * labeled alternative in {@link FunParser#seq_com}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitSeq(FunParser.SeqContext ctx) {
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
            createJsonObject(ctx.op, ctx.op.getText());
            parentNodes.push(ctx.op.hashCode());
            visit(ctx.e1);
            visit(ctx.e2);
            parentNodes.pop();
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
            createJsonObject(ctx.op, ctx.op.getText());
            parentNodes.push(ctx.op.hashCode());
            visit(ctx.e1);
            visit(ctx.e2);
            parentNodes.pop();
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
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code true}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitTrue(FunParser.TrueContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code num}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitNum(FunParser.NumContext ctx) {
        createJsonObject(ctx, ctx.getText());
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code id}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitId(FunParser.IdContext ctx) {
        createJsonObject(ctx, ctx.getText());
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code funccall}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitFunccall(FunParser.FunccallContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code not}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitNot(FunParser.NotContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by the {@code parens}
     * labeled alternative in {@link FunParser#prim_expr}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitParens(FunParser.ParensContext ctx) {
        return null;
    }

    /**
     * Visit a parse tree produced by {@link FunParser#actual}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Void visitActual(FunParser.ActualContext ctx) {
        return null;
    }
}
