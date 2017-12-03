"use strict";

// Note that you cannot use the JQuery DOM selector when using CodeMirror
CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    mode: "javascript",
    theme: "ambiance"
}).setValue("int n = 15\nproc main():\nwhile n > 1:\nn = n/2 .\n.");
