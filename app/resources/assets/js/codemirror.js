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
var cm = CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    styleActiveLine: true,
    mode: "fun",
    theme: "dracula"
});
cm.setValue("int n = 15\nproc main():\n\twhile n > 1:\n\t\tn = n/2\n\t.\n.");

function getExample(exampleName) {
    return examples[exampleName];
}

$(".code-example").on("click", function() {
    cm.setValue(getExample($(this).data("example")));
});

var examples = {
    ASSIGN: "proc main():\n\tint g = 7\n\tg = g + 1\n\tg = 1 + 2 * g\n\tg = (1 + 2) * g\n\twrite(g)\n.",
    FACTORIAL: "func int fac(int n):\n\tint f = 1\n\twhile n > 1:\n\t\tf = f * n\n\t\tn = n - 1\n\t.\n\treturn f\n.",
    FUNCTION: "func int test(int n):\n\tint r = 10\n\tint s = 20\n\tint t = 30\n\twrite(s)\n\treturn r\n.\n\nproc main():\n\twrite(test(5))\n.",
    IF: "int m = 7\nproc main():\n\tint n = m - 4\n\tif m > 0: write(m) .\n\tif m < n:\n\t\tm = m + 1\n\t\twrite(m)\n\telse:\n\t\tn = n + 1\n\t\twrite(n)\n\t.\n.",
    IO: "int p = read()\n\nproc main():\n\tint q = read()\n\tint r = q + 1\n\twrite(p)\n\twrite(q + 2/5)\n\twrite(r)\n.",
    OCTAL: "proc writeoctal(int n):\n\tif n < 8:\n\t\twrite(n)\n\telse:\n\t\twriteoctal(n/8)\n\twrite(n-((n/8)*8))\n\t.\n.",
    PROC: "int total = 0\n\nproc add(int inc):\n\ttotal = total + inc\n.\n\nproc main():\n\tint i = read()\n\twhile i > 0:\n\t\tadd(i)\n\t\ti = read()\n\t.\n\twrite(total)\n.",
    SCOPE_CHECKING: "int y = x # Error\nint x = 1\nbool x = true # Error\n\nproc main():\n\tint n = 0\n\tint x = 0\n\tint n = 1 #Error\n\tx = x + y #Error\n\tp() #Error\n.",
    TYPE_CHECKING: "int n = true # Error\nbool c = 1\n\nfunc bool pos(int n):\n\treturn n # Error\n.\n\nproc main():\n\tint i = 3\n\tbool b = true\n\ti = i + 1\n\ti = b # Error\n\ti = b * 2 # Error\n\tb = i > 0\n\tif b: write(i) .\n\tif i: write(i) . # Error\n\tb = pos(true) # Error\n\twhile pos(7):\n\t\ti = i + 1\n\t.\n.",
    WHILE: "proc main():\n\tint m = read()\n\tint n = 1\n\twhile n * n < m + 1:\n\t\twrite(n * n)\n\t\tn = n + 1\n\t.\n."
}
