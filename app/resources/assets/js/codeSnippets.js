/* ==========================================================================
 * codeSnippets.js
 *
 * Defines a list of pre-defined Fun program which can be selected and
 * auto-loaded into the code editor.
 *
 * Exposes a method to retrieve each snippet by name.
 * ========================================================================== */

var CodeSnippets = module.exports = {
    // Retrieve a code snippet by name
    getSnippet: function(snippetName) {
        return snippets[snippetName];
    }
}

// Defines a list of pre-defined Fun programs
var snippets = {
    ASSIGN:         "proc main():\n\tint g = 7\n\tg = g + 1\n\t" +
                    "g = 1 + 2 * g\n\tg = (1 + 2) * g\n\twrite(g)\n.",

    DEFAULT:        "int n = 15\nproc main():\n\twhile n > 1:\n\t\t" +
                    "n = n/2\n\t.\n.",

    FACTORIAL:      "func int fac(int n):\n\tint f = 1\n\twhile n > 1:\n\t\t" +
                    "f = f * n\n\t\tn = n - 1\n\t.\n\treturn f\n.\n\n" +
                    "proc main():\n\twrite(fac(10))\n.",

    FUNCTION:       "func int test(int n):\n\tint r = 10\n\tint s = 20\n\t" +
                    "int t = 30\n\twrite(s)\n\treturn r\n.\n\n" +
                    "proc main():\n\twrite(test(5))\n.",

    IF:             "proc main():\n\tint m = 7\n\tint n = 3\n\t" +
                    "if m < n:\n\t\t" +
                    "m = m + 1\n\t\twrite(m)\n\telse:\n\t\tn = n + 1\n\t\t" +
                    "write(n)\n\t.\n.",

    IO:             "int p = read()\n\nproc main():\n\tint q = read()\n\t" +
                    "write(p)\n\twrite(q + 2/5)\n.",

    OCTAL:          "proc writeoctal(int n):\n\tif n < 8:\n\t\twrite(n)\n\t" +
                    "else:\n\t\twriteoctal(n/8)\n\t\t" +
                    "write(n-((n/8)*8))\n\t.\n.\n\nproc main():\n\t" +
                    "writeoctal(8)\n.",

    OVERVIEW:       "bool verbose = true\n\n" +
                    "func int fac(int n): # Returns n\n\tint f = 1\n\t" +
                    "while n > 1:\n\t\tf = f * n\n\t\tn = n - 1\n\t" +
                    "return f\n.\n\nproc main():\n\tint num = read()\n\t" +
                    "while not (num == 0):\n\t\t" +
                    "if verbose: write(num) .\n\t\twrite(fac(num))\n\t\t" +
                    "num = read()\n\t.\n.",

    PREDEFINED:     "func int read():\t\t# Inputs and returns an integer\n\t" +
                    "...\nproc write(int n):\t\t# Outputs the integer n\n\t...",

    PROC:           "int total = 0\n\nproc add(int inc):\n\t" +
                    "total = total + inc\n.\n\n" +
                    "proc main():\n\tint i = read()\n\twhile i > 0:\n\t\t" +
                    "add(i)\n\t\ti = read()\n\t.\n\twrite(total)\n.",

    SCOPE_CHECKING: "int y = x # Error\nbool x = true # Error\n\n" +
                    "proc main():\n\tint n = 0\n\tint x = 0\n\t" +
                    "int n = 1 #Error\n\tx = x + y #Error\n\tp() #Error\n.",

    TYPE_CHECKING:  "int n = true # Error\nbool c = 1\n\n" +
                    "func bool pos(int n):\n\treturn n # Error\n.\n\n" +
                    "proc main():\n\tint i = 3\n\tbool b = true\n\t" +
                    "i = i + 1\n\ti = b # Error\n\ti = b * 2 # Error\n\t" +
                    "b = i > 0\n\tif b: write(i) .\n\t" +
                    "if i: write(i) . # Error\n\tb = pos(true) # Error\n\t" +
                    "while pos(7):\n\t\ti = i + 1\n\t.\n.",

    WHILE:          "proc main():\n\tint m = read()\n\tint n = 1\n\t" +
                    "while n * n < m + 1:\n\t\twrite(n * n)\n\t\t" +
                    "n = n + 1\n\t.\n."
}
