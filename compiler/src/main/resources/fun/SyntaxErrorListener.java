package fun;

import org.antlr.v4.runtime.*;
import java.util.ArrayList;

public class SyntaxErrorListener extends BaseErrorListener {

    public static final SyntaxErrorListener LISTENER = new SyntaxErrorListener();

    private static ArrayList<String> syntaxErrors = new ArrayList<String>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
       syntaxErrors.add("line "+line+":"+charPositionInLine+" "+msg);
    }

    public static ArrayList<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    public static void reset() {
        syntaxErrors.clear();
    }
}
