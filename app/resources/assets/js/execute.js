require("./codemirror.js");

var Tree = require("./tree.js");

$("#execute-form").submit(function(e) {
    // Get the form that was submitted
    var $form = $(this);
    // Stop the form submitting normally (i.e., don't route to action parameter)
    e.preventDefault();
    // Get the intended controller route
    var url = $form.attr("action");
    // Get csrf token from page meta-data
    var AUTH_TOKEN = $("meta[name='csrf-token']").attr("content");
    // Serialise the form inputs, add csrf token
    var data = $form.serialize() + "&_token=" + AUTH_TOKEN;
    // Post to the controller
    $.post(url, data, function(responseData) {
        $(".right-contextual-container").css("display", "table");
        $(".center-container").css("display", "table");
        var response = responseData.response;
        var numSyntaxErrors = response.numSyntaxErrors;
        var syntaxErrors = response.syntaxErrors;
        var numContextualErrors = response.numContextualErrors;
        var contextualErrors = response.contextualErrors;
        var treeNodes = response.treeNodes;
        var objectCode = response.objectCode;
        var output = response.output;
        var contextualNodeOrder = response.contextualNodeOrder;
        var generationNodeOrder = response.generationNodeOrder;
        $(".program-tree-container").text("");
        if (numSyntaxErrors > 0) {
            $(".program-tree-container").append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $(".program-tree-container").append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $(".program-tree-container").append((index + 1) + ": " + syntaxError);
            });
            $(".program-tree-container").append("<br>");
        } else {
            Tree.drawTree(treeNodes);
            Tree.setNodeOrder(contextualNodeOrder);
            Tree.setUpSwitchListeners(contextualNodeOrder, generationNodeOrder);
            Tree.setUpPlaybackListeners();
        }
    }).fail(function(responseData) {
        alert(responseData.responseJSON.errors.program);
    });
});
