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
	public static FunResponse execute(InputStream program) {
		try {
			// Create a blank Response object for each execution
			response = new FunResponse();
			SVM objprog = compile(program);
			objprog.interpret(response);
		} catch (FunException e) {
			response.setOutput("Compilation failed");
		} catch (Exception e) {
			// Java-based errors, not sure what to do with these right now
		}
		return response;
	}

	// Compile a Fun source program to SVM code.
	private static SVM compile (InputStream source) throws Exception {
		// Remove any old error messages
		SyntaxErrorListener.reset();
		// Convert the source code into an ANTLR input stream
		ANTLRInputStream inputStream = new ANTLRInputStream(source);
		// Create the lexer object
		FunLexer lexer = createLexer(inputStream);
		// Create a token stream using the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Create the parser object
		FunParser parser = createParser(tokens);
		// Carry out syntactic analysis and create the parse tree
		ParseTree parseTree = syntacticAnalyse(parser);
		// Visit the parse tree to build a repesentation of the AST
		FunASTVisitor astVisitor = buildAST(parseTree, parser);
		// Retrieve the flat data structure representing the AST
		JsonArray treeNodes = astVisitor.getTreeNodes();
		response.setTreeNodes(treeNodes);
		contextualAnalyse(parseTree,tokens);
		SVM objprog = codeGenerate(parseTree);
		return objprog;
	}

	private static FunLexer createLexer(ANTLRInputStream inputStream) {
		FunLexer lexer = new FunLexer(inputStream);
		// Remove the default error listeners
		lexer.removeErrorListeners();
		// Add a new customer listener
		lexer.addErrorListener(SyntaxErrorListener.LISTENER);
		return lexer;
	}

	// Create the parser object
	private static FunParser createParser(CommonTokenStream tokens)
		throws Exception {
		FunParser parser = new FunParser(tokens);
		// Remove the default error listeners
		parser.removeErrorListeners();
		// Add a new custom listener
		parser.addErrorListener(SyntaxErrorListener.LISTENER);
		return parser;
	}

	// Perform syntactic analysis of a Fun source program.
	// Return the parse tree (concrete syntax tree) representation of the Fun program.
	private static ParseTree syntacticAnalyse(FunParser parser)
		throws Exception {
	    ParseTree parseTree = parser.program();
		int numErrors = parser.getNumberOfSyntaxErrors();
		// Retrieve all syntax errors reported
		List<String> errors = SyntaxErrorListener.getSyntaxErrors();
		// Set the number of syntax errors in the response object
		response.setNumSyntaxErrors(numErrors);
		// Set the actual syntax errors in the response object
		response.setSyntaxErrors(errors);
		if (numErrors > 0)
			throw new FunException();
		return parseTree;
	}

	// Visit the parse tree and build an AST
	private static FunASTVisitor buildAST(ParseTree parseTree, FunParser parser) {
		// Create a visitor to walk to parse tree and construct an AST
		FunASTVisitor astVisitor = new FunASTVisitor(parser);
		// Walk the parse tree
		astVisitor.visit(parseTree);
		return astVisitor;
	}

	// Perform contextual analysis of a Fun program.
    private static void contextualAnalyse (ParseTree parseTree, CommonTokenStream tokens)
		throws Exception {
		FunCheckerVisitor checker = new FunCheckerVisitor(tokens);
		// Remove any old error messages
		checker.reset();
		checker.visit(parseTree);
		JsonArray animationOrder = checker.getAnimationOrder();
		response.setContextualAnimationOrder(animationOrder);
		int numErrors = checker.getNumberOfContextualErrors();
		// Retrieve all contextual errors reported
		List<String> errors = checker.getContextualErrors();
		// Set the number of contextual errors in the response object
		response.setNumContextualErrors(numErrors);
		// Set the actual contextual errors in the response object
		response.setContextualErrors(errors);
		if (numErrors > 0)
			throw new FunException();
	}

	// Perform code generation of a Fun program,
	// represented by a parse tree, emitting SVM code.
	// Also print the object code.
	private static SVM codeGenerate (ParseTree parseTree) throws Exception  {
		FunEncoderVisitor encoder = new FunEncoderVisitor();
		encoder.visit(parseTree);
		SVM objectprog = encoder.getSVM();
		// Pass the response object
		objectprog.showCode(response);
		return objectprog;
	}

	private static class FunException extends Exception {
	}
}
