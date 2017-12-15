package api;

//////////////////////////////////////////////////////////////
//
// Driver for the Fun compiler and SVM interpreter.
//
// Developed June 2012 by David Watt (University of Glasgow).
//
//////////////////////////////////////////////////////////////


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.io.InputStream;

import com.google.gson.JsonArray;

public class FunRun {

	// Response object to hold all information related to the program execution
	private static FunResponse response;

	// Executes the code specified in the program InputStream
	public static FunResponse execute(InputStream program, String executionType) {
		try {
			response = new FunResponse();
			compile(program, executionType);
		} catch (FunException e) {
			response.setOutput("Compilation failed");
		} catch (Exception e) {
			// Java-based errors, not sure what to do with these right now
		}
		return response;
	}

	// Compile a Fun source program to SVM code.
	private static void compile (InputStream source, String executionType) throws Exception {
		SyntaxErrorListener.reset();
		ANTLRInputStream inputStream = new ANTLRInputStream(source);
		FunLexer lexer = createLexer(inputStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		FunParser parser = createParser(tokens);
		ParseTree parseTree = syntacticAnalyse(parser);
		FunASTVisitor astVisitor = buildAST(parseTree, parser);
		JsonArray treeNodes = astVisitor.getTreeNodes();
		response.setTreeNodes(treeNodes);
		contextualAnalyse(parseTree,tokens);
		if (executionType.equals("cg"))
			codeGenerate(parseTree);
	}

	private static FunLexer createLexer(ANTLRInputStream inputStream) {
		FunLexer lexer = new FunLexer(inputStream);
		lexer.removeErrorListeners();
		lexer.addErrorListener(SyntaxErrorListener.LISTENER);
		return lexer;
	}

	// Create the parser object
	private static FunParser createParser(CommonTokenStream tokens)
		throws Exception {
		FunParser parser = new FunParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(SyntaxErrorListener.LISTENER);
		return parser;
	}

	// Perform syntactic analysis of a Fun source program.
	// Return the parse tree (concrete syntax tree) representation of the Fun program.
	private static ParseTree syntacticAnalyse(FunParser parser)
		throws Exception {
	    ParseTree parseTree = parser.program();
		int numErrors = parser.getNumberOfSyntaxErrors();
		List<String> errors = SyntaxErrorListener.getSyntaxErrors();
		response.setNumSyntaxErrors(numErrors);
		response.setSyntaxErrors(errors);
		if (numErrors > 0)
			throw new FunException();
		return parseTree;
	}

	// Visit the parse tree and build an AST
	private static FunASTVisitor buildAST(ParseTree parseTree, FunParser parser) {
		FunASTVisitor astVisitor = new FunASTVisitor(parser);
		astVisitor.visit(parseTree);
		return astVisitor;
	}

	// Perform contextual analysis of a Fun program.
    private static void contextualAnalyse (ParseTree parseTree, CommonTokenStream tokens)
		throws Exception {
		FunCheckerVisitor checker = new FunCheckerVisitor(tokens);
		checker.visit(parseTree);
		JsonArray nodeOrder = checker.getNodeOrder();
		response.setNodeOrder(nodeOrder);
		int numErrors = checker.getNumberOfContextualErrors();
		List<String> errors = checker.getContextualErrors();
		response.setNumContextualErrors(numErrors);
		response.setContextualErrors(errors);
		if (numErrors > 0)
			throw new FunException();
	}

	// Perform code generation of a Fun program,
	// represented by a parse tree, emitting SVM code.
	// Also print the object code.
	private static void codeGenerate (ParseTree parseTree) throws Exception  {
		FunEncoderVisitor encoder = new FunEncoderVisitor();
		encoder.visit(parseTree);
		JsonArray nodeOrder = encoder.getNodeOrder();
		response.setNodeOrder(nodeOrder);
	}

	private static class FunException extends Exception {
	}
}
