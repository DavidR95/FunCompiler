/* ==========================================================================
 * codeSubmit.js
 *
 * Submits the input program to the compiler API. Retrieves the response and if
 * successful, triggers the methods to build the AST.
 * ========================================================================== */

// Import Tree module
var Tree = require("./tree.js");

// Indicates which button was pressed, contextual analysis or code generation
var executionType;

// Event listener to trigger when either submit button is pressed
$("button[type='submit']").click(function() {
    // Contextual analysis or code generation
    executionType = $(this).val();
    // Modify background-ground to show active
    $(this).siblings().css("background-color", "#333545")
    $(this).css("background-color", "#035a80");
});

// Event listener to trigger when form is submitted
$("#execute-form").submit(function(e) {
    // Get the form that was submitted
    var $form = $(this);
    // Stop the form submitting normally (i.e., don't route to action parameter)
    e.preventDefault();
    // Get csrf token from page meta-data
    var AUTH_TOKEN = $("meta[name='csrf-token']").attr("content");
    // Create the url to use within the post request
    var url = "/" + executionType;
    // Serialise the form inputs, add csrf token
    var data = $form.serialize() + "&_token=" + AUTH_TOKEN;
    // Post to the controller
    $.post(url, data, function(responseData) {
        $("#display-specification").hide();
        $("#display-program-tree").show();
        $("#navbar .active").removeClass("active");
        var response = responseData.response;
        var numSyntaxErrors = response.numSyntaxErrors;
        var syntaxErrors = response.syntaxErrors;
        var numContextualErrors = response.numContextualErrors;
        var contextualErrors = response.contextualErrors;
        var treeNodes = response.treeNodes;
        var objectCode = response.objectCode;
        var output = response.output;
        var nodeOrder = response.nodeOrder;
        if (numSyntaxErrors > 0) {
            $(".program-tree-container").append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $(".program-tree-container").append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $(".program-tree-container").append((index + 1) + ": " + syntaxError);
            });
            $(".program-tree-container").append("<br>");
        } else {
            Tree.initialise(executionType, nodeOrder);
            Tree.drawTree(treeNodes);
            Tree.highlightFirstNode();
        }
    }).fail(function(responseData) {
        alert(responseData.responseJSON.errors.program);
    });
});
