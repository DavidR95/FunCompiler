package api;

//////////////////////////////////////////////////////////////
//
// An object to collect all relevant information relating to a program execution.
//
// This object will later be converted to JSON before being sent to the web app.
//
//////////////////////////////////////////////////////////////

import java.util.List;

import com.google.gson.JsonArray;

public class FunResponse {
    private int numSyntaxErrors;
    private int numContextualErrors;
    private List<String> syntaxErrors;
    private List<String> contextualErrors;
    private List<String> objectCode;
    private JsonArray contextualAnimationOrder;
    private JsonArray treeNodes;
    private String output;

    public void setNumSyntaxErrors(int numSyntaxErrors) {
        this.numSyntaxErrors = numSyntaxErrors;
    }

    public void setNumContextualErrors(int numContextualErrors) {
        this.numContextualErrors = numContextualErrors;
    }

    public void setSyntaxErrors(List<String> syntaxErrors) {
        this.syntaxErrors = syntaxErrors;
    }

    public void setContextualErrors(List<String> contextualErrors) {
        this.contextualErrors = contextualErrors;
    }

    public void setObjectCode(List<String> objectCode) {
        this.objectCode = objectCode;
    }

    public void setContextualAnimationOrder(JsonArray contextualAnimationOrder) {
        this.contextualAnimationOrder = contextualAnimationOrder;
    }

    public void setTreeNodes(JsonArray treeNodes) {
        this.treeNodes = treeNodes;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
