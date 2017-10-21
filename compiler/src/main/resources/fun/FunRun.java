package fun;

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

	private static boolean tracing = false;

	private static StringBuffer sb = new StringBuffer();

	public static StringBuffer execute(InputStream program) {
	// Compile a Fun source program to SVM code,
	// then interpret it if it compiles successfully.
	// The source file name must be given as the
	// first program argument.
		try {
			InputStream source = program;
			SVM objprog = compile(source);

			sb.append("Interpretation ...");
			sb.append(objprog.interpret(tracing));
		} catch (FunException x) {
			sb.append("Compilation failed");
		} catch (Exception x) {
			sb.append("Something went wrong");
		}
		return sb;
	}

	private static SVM compile (InputStream source)
			throws Exception {
	// Compile a Fun source program to SVM code.
		FunLexer lexer = new FunLexer(
		   new ANTLRInputStream(source));
		CommonTokenStream tokens =
		   new CommonTokenStream(lexer);
		ParseTree ast =
		    syntacticAnalyse(tokens);
		contextualAnalyse(ast,tokens);
		SVM objprog = codeGenerate(ast);
		return objprog;
	}

	private static ParseTree syntacticAnalyse
			(CommonTokenStream tokens)
			throws Exception {
	// Perform syntactic analysis of a Fun source program.
	// Print any error messages.
	// Return an AST representation of the Fun program.
		FunParser parser = new FunParser(tokens);
	        ParseTree ast = parser.program();
		int errors = parser.getNumberOfSyntaxErrors();
		if (errors > 0) {
			sb.append(errors + " syntactic errors");
			throw new FunException();
		}
		return ast;
	}

    private static void contextualAnalyse (ParseTree ast, CommonTokenStream tokens)
			throws Exception {
	// Perform contextual analysis of a Fun program,
	// represented by an AST.
	// Print any error messages.
		FunCheckerVisitor checker =
		   new FunCheckerVisitor(tokens);
		checker.visit(ast);
		int errors = checker.getNumberOfContextualErrors();
		if (errors > 0) {
			sb.append(errors + " scope/type errors");
			throw new FunException();
		}
	}

	private static SVM codeGenerate (ParseTree ast)
			throws Exception  {
	// Perform code generation of a Fun program,
	// represented by an AST, emitting SVM code.
	// Also print the object code.
		FunEncoderVisitor encoder =
		   new FunEncoderVisitor();
		encoder.visit(ast);
		SVM objectprog = encoder.getSVM();
		sb.append(objectprog.showCode());
		return objectprog;
	}

	private static class FunException extends Exception {
	}

}
