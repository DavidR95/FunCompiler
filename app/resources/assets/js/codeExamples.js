/* ==========================================================================
 * codeExamples.js
 *
 * Defines a list of example Fun program which can be selected and auto-loaded
 * into the code editor.
 *
 * Exposes a method to retrieve each example by name.
 * ========================================================================== */

var CodeExamples = module.exports = {
    // Retrieve a code example by name
    getExample: function(exampleName) {
        return examples[exampleName];
    }
}

// Defines a list of example Fun programs
var examples = {
    ASSIGN: "proc main():\n\tint g = 7\n\tg = g + 1\n\tg = 1 + 2 * g\n\t" +
            "g = (1 + 2) * g\n\twrite(g)\n.",
    FACTORIAL: "func int fac(int n):\n\tint f = 1\n\twhile n > 1:\n\t\t" +
               "f = f * n\n\t\tn = n - 1\n\t.\n\treturn f\n.",
    FUNCTION: "func int test(int n):\n\tint r = 10\n\tint s = 20\n\t" +
              "int t = 30\n\twrite(s)\n\treturn r\n.\n\nproc main():\n\t" +
              "write(test(5))\n.",
    IF: "proc main():\n\tint m = 7\n\tint n = 3\n\tif m < n:\n\t\t" +
        "m = m + 1\n\t\twrite(m)\n\telse:\n\t\tn = n + 1\n\t\twrite(n)\n\t.\n.",
    IO: "int p = read()\n\nproc main():\n\tint q = read()\n\twrite(p)\n\t" +
        "write(q + 2/5)\n.",
    OCTAL: "proc writeoctal(int n):\n\tif n < 8:\n\t\twrite(n)\n\t" +
           "else:\n\t\twriteoctal(n/8)\n\t\twrite(n-((n/8)*8))\n\t.\n.",
    PROC: "int total = 0\n\nproc add(int inc):\n\ttotal = total + inc\n.\n\n" +
          "proc main():\n\tint i = read()\n\twhile i > 0:\n\t\tadd(i)\n\t\t" +
          "i = read()\n\t.\n\twrite(total)\n.",
    SCOPE_CHECKING: "int y = x # Error\nbool x = true # Error\n\n" +
                    "proc main():\n\tint n = 0\n\tint x = 0\n\t" +
                    "int n = 1 #Error\n\tx = x + y #Error\n\tp() #Error\n.",
    TYPE_CHECKING: "int n = true # Error\nbool c = 1\n\n" +
                   "func bool pos(int n):\n\treturn n # Error\n.\n\n" +
                   "proc main():\n\tint i = 3\n\tbool b = true\n\t" +
                   "i = i + 1\n\ti = b # Error\n\ti = b * 2 # Error\n\t" +
                   "b = i > 0\n\tif b: write(i) .\n\t" +
                   "if i: write(i) . # Error\n\tb = pos(true) # Error\n\t" +
                   "while pos(7):\n\t\ti = i + 1\n\t.\n.",
    WHILE: "proc main():\n\tint m = read()\n\tint n = 1\n\t" +
           "while n * n < m + 1:\n\t\twrite(n * n)\n\t\tn = n + 1\n\t.\n."
}
