package api;

//////////////////////////////////////////////////////////////
//
// An object to collect all relevant information relating to a program execution.
//
// This object will later be converted to JSON before being sent to the web app.
//
//////////////////////////////////////////////////////////////

import java.util.List;
import java.util.LinkedList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class FunResponse {
    private int numSyntaxErrors = 0;
    private int numContextualErrors = 0;
    private List<String> syntaxErrors = new LinkedList<String>();
    private List<String> contextualErrors = new LinkedList<String>();
    private List<String> objectCode = new LinkedList<String>();
    private JsonArray treeNodes = new JsonArray();
    private String output = "";

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

    public void setTreeNodes(JsonArray treeNodes) {
        this.treeNodes = treeNodes;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public static JsonObject searchTreeNodes(JsonArray treeNodes, int nodeId) {
        for (JsonElement treeNode : treeNodes) {
            JsonObject treeNodeObject = treeNode.getAsJsonObject();
            int id = treeNodeObject.get("id").getAsInt();
            if (id == nodeId)
                return treeNodeObject;
        }
        return null;
    }
}
