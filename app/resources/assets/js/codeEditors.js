/* ==========================================================================
 * codeEditors.js
 *
 * Creates a 'CodeMirror' code editor and defines the syntax highlighting for
 * the 'Fun' programming language.
 * ========================================================================== */

// Import CodeMirror node module
var CodeMirror = require('codemirror');
// Import the CodeMirror 'simple-mode' addon
require('codemirror/addon/mode/simple.js');
// Import the CodeMirror 'active-line' addon
require('codemirror/addon/selection/active-line.js');
// Import the CodeSnippets module
var CodeSnippets = require('./codeSnippets.js')

// Bind a 'click' event listener to all links with the class 'code-example'
$(".code-example").on("click", function() {
    // Get the example name from the 'example' data-attribute and update
    code_editor.setValue(CodeSnippets.getSnippet($(this).data("example")));
});

// Event listener to trigger when a specification tab is shown
$('.nav-tabs a').on('shown.bs.tab', function() {
    // Refresh (reload) the specified CodeMirror objects
    overview_snippet.refresh();
    predefined_snippet.refresh();
});

// Define a 'mode' for the Fun programming language, i.e., syntax highlighting
CodeMirror.defineSimpleMode("fun", {
  start: [
    {regex: /(?:func|proc|return|if|while|else|not)\b/, token: "keyword"},
    {regex: /true|false/, token: "atom"},
    {regex: /int|bool/, token: "type"},
    {regex: /0x[a-f\d]+|[-+]?(?:\.\d+|\d+\.?\d*)(?:e[-+]?\d+)?/i,
        token: "number"},
    {regex: /#.*/, token: "comment"},
    {regex: /[-+\/*=<>]+/, token: "operator"},
    {regex: /[\:]/, indent: true},
    {regex: /[\.]/, dedent: true},
    {regex: /[a-z$][\w$]*/, token: "variable"},
  ],
  comment: []
});

// Create a CodeMirror object from a text area
var code_editor = CodeMirror.fromTextArea(
    document.getElementById("code-editor"), {
        lineNumbers: true,
        tabSize: 2,
        lineWrapping: true,
        styleActiveLine: true,
        mode: "fun",
        theme: "dracula"
    }
);

// Set the default value of the code mirror to be the program below
code_editor.setValue("int n = 15\nproc main():\n\twhile n > 1:\n\t\tn = n/2\n\t.\n.");

// Below are read-only code snippets used within the specification
var overview_snippet = CodeMirror(document.getElementById("overview")
                       .getElementsByClassName("code-snippet")[0], {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    mode:  "fun",
    theme: "dracula",
    readOnly: "nocursor",
    value: CodeSnippets.getSnippet("OVERVIEW")
});

var predefined_snippet = CodeMirror(document.getElementById("predefined")
                         .getElementsByClassName("code-snippet")[0], {
    lineNumbers: true,
    tabSize: 2,
    lineWrapping: true,
    mode:  "fun",
    theme: "dracula",
    readOnly: "nocursor",
    value: CodeSnippets.getSnippet("PREDEFINED")
});
