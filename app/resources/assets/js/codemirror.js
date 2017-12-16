"use strict";

var CodeMirror = require('codemirror');

require('codemirror/addon/mode/simple.js');
require('codemirror/addon/selection/active-line.js');

CodeMirror.defineSimpleMode("fun", {
  start: [
    {regex: /(?:func|proc|return|if|while|else|not)\b/, token: "keyword"},
    {regex: /true|false/, token: "atom"},
    {regex: /int|bool/, token: "type"},
    {regex: /0x[a-f\d]+|[-+]?(?:\.\d+|\d+\.?\d*)(?:e[-+]?\d+)?/i, token: "number"},
    {regex: /#.*/, token: "comment"},
    {regex: /[-+\/*=<>]+/, token: "operator"},
    {regex: /[\:]/, indent: true},
    {regex: /[\.]/, dedent: true},
    {regex: /[a-z$][\w$]*/, token: "variable"},
  ],
  comment: []
});

// Note that you cannot use the JQuery DOM selector when using CodeMirror
CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    styleActiveLine: true,
    mode: "fun",
    theme: "dracula"
}).setValue("int n = 15\nproc main():\n\twhile n > 1:\n\t\tn = n/2\n\t.\n.");
