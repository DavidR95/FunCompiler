"use strict";

var CodeMirror = require('codemirror');

require('codemirror/addon/mode/simple');

CodeMirror.defineSimpleMode("simplemode", {
  start: [
    {regex: /(?:func|proc|return|if|while|else|not)\b/, token: "keyword"},
    {regex: /true|false/, token: "atom"},
    {regex: /int|bool/, token: "type"},
    {regex: /0x[a-f\d]+|[-+]?(?:\.\d+|\d+\.?\d*)(?:e[-+]?\d+)?/i, token: "number"},
    {regex: /#.*/, token: "comment"},
    {regex: /[-+\/*=<>]+/, token: "operator"},
    {regex: /[\:]/, indent: true},
    {regex: /[a-z$][\w$]*/, token: "variable"},
  ],
  comment: []
});

// Note that you cannot use the JQuery DOM selector when using CodeMirror
CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    mode: "simplemode",
    theme: "dracula"
}).setValue("int n = 15\nproc main():\nwhile n > 1:\nn = n/2 .\n.");
