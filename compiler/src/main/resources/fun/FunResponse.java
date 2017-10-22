package fun;

import java.util.ArrayList;

public class FunResponse {
    private int numSyntaxErrors = 0;
    private int numContextualErrors = 0;
    private ArrayList<String> syntaxErrors = new ArrayList<String>();
    private ArrayList<String> contextualErrors = new ArrayList<String>();
    private String objectCode = "";
    private String output = "";

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
