$('#execute-form').submit(function(e) {
    // Get the form that was submitted
    var $form = $(this);
    // Stop the form submitting normally (i.e., don't route to action parameter)
    e.preventDefault();
    // Get the intended controller route
    var url = $form.attr('action');
    // Get csrf token from page meta-data
    var AUTH_TOKEN = $('meta[name="csrf-token"]').attr('content');
    // Serialise the form inputs, add csrf token
    var data = $form.serialize() + "&_token=" + AUTH_TOKEN;
    // Post to the controller
    $.post(url, data, function(response) {
        var response = response['response'];
        var numSyntaxErrors = response['numSyntaxErrors'];
        var syntaxErrors = response['syntaxErrors'];
        var numContextualErrors = response['numContextualErrors'];
        var contextualErrors = response['contextualErrors'];
        var astData = response['astData'];
        var objectCode = response['objectCode'];
        var output = response['output']
        $('.program-tree').text("");
        if (numSyntaxErrors > 0) {
            $('.program-tree').append("Number of syntax errors: " + numSyntaxErrors + "<br>");
            $('.program-tree').append("Syntax errors: <br>");
            $.each(syntaxErrors, function(index, syntaxError) {
                $('.program-tree').append((index+1) + ": " + syntaxError);
            });
            $('.program-tree').append("<br>");
        } else if (numContextualErrors > 0) {
            $('.program-tree').append("Number of contextual errors: " + numContextualErrors + "<br>");
            $('.program-tree').append("Contextual errors: <br>");
            $.each(contextualErrors, function(index, contextualError) {
                $('.program-tree').append((index+1) + ": " + contextualError);
            });
            $('.program-tree').append("<br>");
        } else {
            $.each(astData, function(index, node) {
                $('.program-tree').append(node['name'] + " ");
            });
        }
        $('.object-code').text("");
        $.each(objectCode, function(index, instruction) {
            $('.object-code').append(instruction + "<br>");
        });
        $('.output').text(output);
    });
});
