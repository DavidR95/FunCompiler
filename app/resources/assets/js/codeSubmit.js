/* ==========================================================================
 * codeSubmit.js
 *
 * Submits the input program to the compiler API. Retrieves the response and if
 * successful, triggers the methods to build the AST.
 * ========================================================================== */

// Import Code Animation module
var CodeAnimation = require("./codeAnimation.js");

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
    // Post to the controller and execute body upon success
    $.post(url, data, function(responseData) {
        // Collect all response data
        var response = responseData.response;
        var numSyntaxErrors = response.numSyntaxErrors;
        var syntaxErrors = response.syntaxErrors;
        var numContextualErrors = response.numContextualErrors;
        var contextualErrors = response.contextualErrors;
        var treeNodes = response.treeNodes;
        var objectCode = response.objectCode;
        var nodeOrder = response.nodeOrder;
        // If there was syntax errors...
        if (numSyntaxErrors > 0) {
            // Build an error message and insert into an alert
            var syntaxErrorMessage = "Number of syntax errors: " +
                                     numSyntaxErrors;
            $.each(syntaxErrors, function(index, syntaxError) {
                syntaxErrorMessage += ("\n" + syntaxError);
            });
            alert(syntaxErrorMessage);
        // If the program was syntactically valid...
        } else {
            // Hide the specification section
            $("#display-specification").hide();
            // Display the AST section
            $("#display-program-tree").show();
            // Remove the active class from the 'Home' button
            $("#navbar .active").removeClass("active");
            // Display the correct containers depending on the execution type
            CodeAnimation.initialise(executionType, nodeOrder);
            // Draw the AST
            CodeAnimation.drawTree(treeNodes);
            // 'Animate' the first node in the tree
            CodeAnimation.highlightFirstNode();
        }
    // If post request return a failure...
    }).fail(function(responseData) {
        // Insert errors into an alert
        alert(responseData.responseJSON.errors.program);
    });
});
