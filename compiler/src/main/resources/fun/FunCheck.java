//////////////////////////////////////////////////////////////
//
// Driver for the Fun typechecker
//
// Developed June 2012 by David Watt (University of Glasgow).
//
//////////////////////////////////////////////////////////////


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;
import java.io.*;

public class FunCheck {

	private static boolean tracing = false;

	private static PrintStream out = System.out;

	public static void main(String[] args) {
	// Compile a Fun source program to SVM code, 
	// then interpret it if it compiles successfully.
	// The source file name must be given as the 
	// first program argument.
		try {
			if (args.length == 0)
				throw new FunException();
			InputStream source =
			   new FileInputStream(args[0]);
			check(source);
		} catch (FunException x) {
			out.println("Contextual analysis failed");
		} catch (Exception x) {
			x.printStackTrace(out);
		}
	}

	private static void check (InputStream source)
			throws Exception {
	// Contextually analyse a Fun program
		FunLexer lexer = new FunLexer(
		   new ANTLRInputStream(source));
		CommonTokenStream tokens = 
		   new CommonTokenStream(lexer);
		ParseTree ast =
		    syntacticAnalyse(tokens);
		contextualAnalyse(ast,tokens);
	}

	private static ParseTree syntacticAnalyse
			(CommonTokenStream tokens)
			throws Exception {
	// Perform syntactic analysis of a Fun source program.
	// Print any error messages.
	// Return an AST representation of the Fun program.
		out.println();
		out.println("Syntactic analysis ...");
		FunParser parser = new FunParser(tokens);
	        ParseTree ast = parser.program();
		int errors = parser.getNumberOfSyntaxErrors();
		out.println(errors + " syntactic errors");
		if (errors > 0)
			throw new FunException();
		return ast;
	}

    private static void contextualAnalyse (ParseTree ast, CommonTokenStream tokens)
			throws Exception {
	// Perform contextual analysis of a Fun program, 
	// represented by an AST.
	// Print any error messages.
		out.println("Contextual analysis ...");
		FunCheckerVisitor checker =
		   new FunCheckerVisitor(tokens);
		checker.visit(ast);
		int errors = checker.getNumberOfContextualErrors();
		out.println(errors + " scope/type errors");
		out.println();
		if (errors > 0)
			throw new FunException();
	}

	private static class FunException extends Exception {
	}

}
