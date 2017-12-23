/* ==========================================================================
 * codeHelpers.js
 *
 * Provides helper methods used to manipulate the response returned from the
 * API to build a data structure in which D3 can form a tree.
 * ========================================================================== */

var CodeHelpers = module.exports = {
    // Converts a flat data structure into a parent-child tree
    buildTree: function(data) {
        var dataMap = CodeHelpers.mapData(data);
        var treeData = [];
        data.forEach(function(node) {
            var parent = dataMap[node.parent_id];
            if (parent) {
                (parent.children || (parent.children = [])).push(node);
            } else {
                treeData.push(node);
            }
        });
        return treeData;
    },
    
    // Modifies the array index to be the ID of the node
    mapData: function(data) {
        var dataMap = data.reduce(function(map, node) {
            map[node.id] = node;
            return map;
        }, {});
        return dataMap;
    }
}
