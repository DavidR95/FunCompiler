/* ==========================================================================
 * codeTemplates.js
 *
 * Defines a list of code templates that are displayed during code-generation
 * when traversing the AST
 *
 * Exposes a method to retrieve each template by name.
 * ========================================================================== */

var CodeTemplates = module.exports = {
    // Retrieve a code template by name
    getTemplate: function(templateName) {
        return templates[templateName];
    }
}

// Defines a list of pre-defined code templates
var templates = {
    ASSN: [
        "Code to evaluate expr",
        "STOREG d or STOREL d"
    ],

    CMPEQ: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "CMPEQ"
    ],

    DIV: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "DIV"
    ],

    FALSE: [
        "LOADC"
    ],

    FORMAL: [
        "COPYARG"
    ],

    FUNC: [
        "Code to evaluate formal declarations",
        "Code to evaluate variable declarations",
        "Code to execute com",
        "Code to evaluate return expr",
        "RETURN"
    ],

    FUNCCALL: [
        "Code to evaluate expr",
        "CALL d"
    ],

    GT: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "GT"
    ],

    ID: [
        "LOADG d or LOADC d"
    ],

    IF: [
        "Code to evaluate expr",
        "JUMPF 'exit_address'",
        "Code to evaluate com",
        "Label: 'exit_address'"
    ],

    IFELSE: [
        "Code to evaluate expr",
        "JUMPF 'else_address'",
        "Code to evaluate com1",
        "JUMP 'exit_address'",
        "Label: 'else_address'",
        "Code to evaluate com 2",
        "Label: 'exit_address'"
    ],

    LT: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "LT"
    ],

    MINUS: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "SUB"
    ],

    NOFORMAL: [
        "COPYARG"
    ],

    NOT: [
        "Code to evaluate expr",
        "INV"
    ],

    NUM: [
        "LOADC"
    ],

    PLUS: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "ADD"
    ],

    PROC: [
        "Code to evaluate formal declarations",
        "Code to evaluate variable declarations",
        "Code to execute com",
        "RETURN"
    ],

    PROCCALL: [
        "Code to evaluate expr",
        "CALL d"
    ],

    PROG: [
        "Code to evaluate variable declarations",
        "CALL",
        "HALT",
        "Code to evaluate procedure declarations"
    ],

    SEQ: [
        "Code to execute com"
    ],

    MUL: [
        "Code to evaluate expr1",
        "Code to evaluate expr2",
        "TIMES"
    ],

    TRUE: [
        "LOADC"
    ],

    VAR: [
        "Code to evaluate expr"
    ],

    WHILE: [
        "Label: 'start_address'",
        "Code to evaluate expr",
        "JUMPF exit_address",
        "Code to execute com",
        "JUMP start_address",
        "Label: 'exit_address'"
    ]
}
