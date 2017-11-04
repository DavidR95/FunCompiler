package api;

//////////////////////////////////////////////////////////////
//
// An object to collect all relevant information relating to a program execution.
//
// This object will later be converted to JSON before being sent to the web app.
//
//////////////////////////////////////////////////////////////

import java.util.*;

public class FunResponse {
    private int numSyntaxErrors = 0;
    private int numContextualErrors = 0;
    private List<String> syntaxErrors = new LinkedList<String>();
    private List<String> contextualErrors = new LinkedList<String>();
    private List<String> objectCode = new LinkedList<String>();
    private List<String> astData = new LinkedList<String>();
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

    public List<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    public void setSyntaxErrors(List<String> syntaxErrors) {
        this.syntaxErrors = syntaxErrors;
    }

    public List<String> getContextualErrors() {
        return contextualErrors;
    }

    public void setContextualErrors(List<String> contextualErrors) {
        this.contextualErrors = contextualErrors;
    }

    public List<String> getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(List<String> objectCode) {
        this.objectCode = objectCode;
    }

    public List<String> getAstData() {
        return astData;
    }

    public void setAstData(List<String> astData) {
        this.astData = astData;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
