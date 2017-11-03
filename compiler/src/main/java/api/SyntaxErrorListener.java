package api;

//////////////////////////////////////////////////////////////
//
// A custom error listener, all syntax errors will be routed through here.
//
// Allows us to collect the syntax errors rather than printing them on standard output.
//
//////////////////////////////////////////////////////////////

import org.antlr.v4.runtime.*;
import java.util.ArrayList;

public class SyntaxErrorListener extends BaseErrorListener {

    // Create a static listener
    public static final SyntaxErrorListener LISTENER = new SyntaxErrorListener();

    // An ArrayList of Strings, each entry holding a syntax error
    private static ArrayList<String> syntaxErrors = new ArrayList<String>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        // Add the error to the syntax errors ArrayList
        syntaxErrors.add("line "+line+":"+charPositionInLine+" "+msg);
    }

    // Return the actual syntax errors
    public static ArrayList<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    // Clear the syntax errors ArrayList
    public static void reset() {
        syntaxErrors.clear();
    }
}
