"use strict";

require([
    "codemirror/lib/codemirror", "codemirror/mode/javascript/javascript"
], function(CodeMirror) {
    // Note that you cannot use the JQuery DOM selector when using CodeMirror
    CodeMirror.fromTextArea(document.getElementById("code-editor"), {
        lineNumbers: true,
        mode: "javascript",
        theme: "ambiance"
    });
});
