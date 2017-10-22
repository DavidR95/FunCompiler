package fun;

import java.util.*;

public class FunResponse {
    private int numSyntaxErrors;
    private int numContextualErrors;
    private ArrayList<String> syntaxErrors;
    private ArrayList<String> contextualErrors;
    private String objectCode;
    private String output;

    public FunResponse() {};

    public int getNumSyntaxErrors() {
        return numSyntaxErrors;
    }

    public void setNumSyntaxErrors(int numSyntaxErrors) {
        this.numSyntaxErrors = numSyntaxErrors;
    }

    public int getNumContextualErrors() {
        return numContextualErrors;
    }

    public void setNumContextualErrors(int numContextualErrors) {
        this.numContextualErrors = numContextualErrors;
    }

    public ArrayList<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    public void setSyntaxErrors(ArrayList<String> syntaxErrors) {
        this.syntaxErrors = syntaxErrors;
    }

    public ArrayList<String> getContextualErrors() {
        return contextualErrors;
    }

    public void setContextualErrors(ArrayList<String> contextualErrors) {
        this.contextualErrors = contextualErrors;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
