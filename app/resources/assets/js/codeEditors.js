/* ==========================================================================
 * codeEditors.js
 *
 * Creates a 'CodeMirror' code editor and defines the syntax highlighting for
 * the 'Fun' programming language.
 *
 * Stores an array of example programs which will be used to update the contents
 * of the code editor when selected.
 * ========================================================================== */

// Import CodeMirror node module
var CodeMirror = require('codemirror');
// Import the CodeMirror 'simple-mode' addon
require('codemirror/addon/mode/simple.js');
// Import the CodeMirror 'active-line' addon
require('codemirror/addon/selection/active-line.js');
// Import the CodeExamples module
var CodeExamples = require('./codeExamples.js')

// Define a 'mode' for the Fun programming language, i.e., syntax highlighting
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

// Create a CodeMirror object from a text area
var cm = CodeMirror.fromTextArea(document.getElementById("code-editor"), {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    styleActiveLine: true,
    mode: "fun",
    theme: "dracula"
});

// Set the default value of the code mirror to be the program below
cm.setValue("int n = 15\nproc main():\n\twhile n > 1:\n\t\tn = n/2\n\t.\n.");

// Bind a 'click' event listener to all links with the class 'code-example'
$(".code-example").on("click", function() {
    // Get the example name from the 'example' data-attribute and update
    cm.setValue(CodeExamples.getExample($(this).data("example")));
});

// Below are readonly code snippets used within the specification
var overview_cm = CodeMirror(document.getElementById("overview").getElementsByClassName("code-snippet")[0], {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    mode:  "fun",
    theme: "dracula",
    readOnly: "nocursor",
    value: "bool verbose = true\n\nfunc int fac(int n): # Returns n\n\tint f = 1\n\twhile n > 1:\n\t\tf = f * n\n\t\tn = n - 1\n\treturn f\n.\n\nproc main():\n\tint num = read()\n\twhile not (num == 0):\n\t\tif verbose: write(num) .\n\t\twrite(fac(num))\n\t\tnum = read()\n\t.\n."
});

var predefined_cm = CodeMirror(document.getElementById("predefined").getElementsByClassName("code-snippet")[0], {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    mode:  "fun",
    theme: "dracula",
    readOnly: "nocursor",
    value: "func int read():\t\t# Inputs and returns an integer\n\t...\nproc write(int n):\t\t# Outputs the integer n\n\t..."
});

$('.nav-tabs a').on('shown.bs.tab', function() {
    overview_cm.refresh();
    predefined_cm.refresh();
});
