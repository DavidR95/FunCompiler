package api;

//////////////////////////////////////////////////////////////
//
// An object to collect all relevant information relating to a program compilation.
//
// This object will later be converted to JSON before being sent to the web app.
//
// Developed September 2017 - March 2018 by David Robertson.
//
//////////////////////////////////////////////////////////////

import java.util.List;

import com.google.gson.JsonArray;

public class FunResponse {
    
    // Number of syntax errors
    private int numSyntaxErrors;
    // Number of contextual errors
    private int numContextualErrors;
    // Actual syntax errors
    private List<String> syntaxErrors;
    // Actual contextual errors
    private List<String> contextualErrors;
    // Flat representation of the input program's AST
    private JsonArray treeNodes;
    // Defines the augmentations and the order in which the AST nodes should be visited
    private JsonArray nodeOrder;

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

    public void setTreeNodes(JsonArray treeNodes) {
        this.treeNodes = treeNodes;
    }

    public void setNodeOrder(JsonArray nodeOrder) {
        this.nodeOrder = nodeOrder;
    }
}
