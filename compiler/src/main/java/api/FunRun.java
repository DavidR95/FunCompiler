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
import java.util.*;
import java.io.*;

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
		} catch (Exception e) {
			// Java-based errors, not sure what to do with these right now
		}
		return response;
	}

	// Compile a Fun source program to SVM code.
	private static SVM compile (InputStream source) throws Exception {
		// Remove any old error messages
		SyntaxErrorListener.reset();
		FunLexer lexer = new FunLexer(new ANTLRInputStream(source));
		// Remove the default error listeners
		lexer.removeErrorListeners();
		// Add a new customer listener
		lexer.addErrorListener(SyntaxErrorListener.LISTENER);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ParseTree ast = syntacticAnalyse(tokens);
		contextualAnalyse(ast,tokens);
		SVM objprog = codeGenerate(ast);
		return objprog;
	}

	// Perform syntactic analysis of a Fun source program.
	// Print any error messages.
	// Return an AST representation of the Fun program.
	private static ParseTree syntacticAnalyse(CommonTokenStream tokens)
		throws Exception {
		FunParser parser = new FunParser(tokens);
		// Remove the default error listeners
		parser.removeErrorListeners();
		// Add a new custom listener
		parser.addErrorListener(SyntaxErrorListener.LISTENER);
	    ParseTree ast = parser.program();
		StringBuilder json = treeToFlatData(ast, parser);
		System.err.println(json);
		int numErrors = parser.getNumberOfSyntaxErrors();
		// Retrieve all syntax errors reported
		ArrayList<String> errors = SyntaxErrorListener.getSyntaxErrors();
		// Set the number of syntax errors in the response object
		response.setNumSyntaxErrors(numErrors);
		// Set the actual syntax errors in the response object
		response.setSyntaxErrors(errors);
		return ast;
	}

	// Perform contextual analysis of a Fun program,
	// represented by an AST.
	// Print any error messages.
    private static void contextualAnalyse (ParseTree ast, CommonTokenStream tokens)
		throws Exception {
		FunCheckerVisitor checker = new FunCheckerVisitor(tokens);
		// Remove any old error messages
		checker.reset();
		checker.visit(ast);
		int numErrors = checker.getNumberOfContextualErrors();
		// Retrieve all contextual errors reported
		ArrayList<String> errors = checker.getContextualErrors();
		// Set the number of contextual errors in the response object
		response.setNumContextualErrors(numErrors);
		// Set the actual contextual errors in the response object
		response.setContextualErrors(errors);
	}

	// Perform code generation of a Fun program,
	// represented by an AST, emitting SVM code.
	// Also print the object code.
	private static SVM codeGenerate (ParseTree ast)	throws Exception  {
		FunEncoderVisitor encoder = new FunEncoderVisitor();
		encoder.visit(ast);
		SVM objectprog = encoder.getSVM();
		// Pass the response object
		objectprog.showCode(response);
		return objectprog;
	}

	private static class FunException extends Exception {
	}

	private static StringBuilder treeToFlatData(ParseTree ast, Parser parser) {
		StringBuilder b = new StringBuilder();
		List<ParseTree> nodes = Trees.getDescendants(ast);
		for (ParseTree node : nodes) {
			int id = node.hashCode();
			String name = Trees.getNodeText(node, parser);
			ParseTree parent_node = node.getParent();
			int parent_id = (parent_node != null ? parent_node.hashCode() : -1);
			b.append("id: " + id + ", name: " + name + ", parent_id: " + parent_id);
			b.append("\n");
		}
		return b;
	}

}
