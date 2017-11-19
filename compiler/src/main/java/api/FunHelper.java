package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class FunHelper {

    public static JsonArray searchTreeNodes(JsonArray treeNodes, int nodeId) {
        for (JsonElement treeNode : treeNodes) {
            JsonObject treeNodeObject = treeNode.getAsJsonObject();
            int id = treeNodeObject.get("id").getAsInt();
            if (id == nodeId)
                return treeNodeObject.getAsJsonArray("explanations");
        }
        return null;
    }

}
