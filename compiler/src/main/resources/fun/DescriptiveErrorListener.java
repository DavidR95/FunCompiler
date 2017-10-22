package fun;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;
import java.util.ArrayList;

public class DescriptiveErrorListener extends BaseErrorListener {

    public static final DescriptiveErrorListener LISTENER = new DescriptiveErrorListener();

    private static ArrayList<String> syntaxErrors = new ArrayList<String>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
       syntaxErrors.add("line "+line+":"+charPositionInLine+" "+msg);
    }

    public static ArrayList<String> getSyntaxErrors() {
        return syntaxErrors;
    }
}
