package api;

//////////////////////////////////////////////////////////////
//
// Helper functions used throughout the Fun compiler.
//
//////////////////////////////////////////////////////////////

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class FunHelper {

    /**
    * Searches a JSON array looking for a JSON object with the supplied int as
    * its 'id' field.
    * @param treeNodes a representation of the ast
    * @param nodeId the hashcode of the relevant context
    * @return the JsonArray storing explanations
    */
    public static JsonArray searchTreeNodes(JsonArray treeNodes, int nodeId) {
        for (JsonElement treeNode : treeNodes) {
            JsonObject treeNodeObject = treeNode.getAsJsonObject();
            if (nodeId == treeNodeObject.get("id").getAsInt())
                return treeNodeObject.getAsJsonArray("explanations");
        }
        return null;
    }

}
