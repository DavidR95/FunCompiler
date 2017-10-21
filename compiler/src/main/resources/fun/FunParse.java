//////////////////////////////////////////////////////////////
//
// Driver for the Fun syntactic analyser.
//
// Developed October 2012 by David Watt (University of Glasgow).
//
//////////////////////////////////////////////////////////////


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;
import java.io.*;

public class FunParse {

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
			ParseTree ast =
			   syntacticAnalyse(source);
		} catch (FunException x) {
			out.println("Compilation failed");
		} catch (Exception x) {
			x.printStackTrace(out);
		}
	}

	private static ParseTree syntacticAnalyse
			(InputStream source)
			throws Exception {
	// Perform syntactic analysis of a Fun source program.
	// Print any error messages.
	// Return an AST representation of the Fun program.
		out.println();
		out.println("Syntactic analysis ...");
		FunLexer lexer = new FunLexer(
		   new ANTLRInputStream(source));
		CommonTokenStream tokens = 
		   new CommonTokenStream(lexer);
		FunParser parser = new FunParser(tokens);
		ParseTree ast = parser.program();
		int errors = parser.getNumberOfSyntaxErrors();
		out.println(errors + " syntactic errors");
		if (errors > 0)
			throw new FunException();
		return ast;
	}

	private static class FunException extends Exception {
	}

}
