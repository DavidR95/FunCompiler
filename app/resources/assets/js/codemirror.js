"use strict";

// Note that you cannot use the JQuery DOM selector when using CodeMirror
var editor = CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    theme: "ambiance"
});
