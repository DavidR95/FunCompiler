var CodeExamples = module.exports = {
    getExample: function(exampleName) {
        return examples[exampleName];
    }
}

var examples = {
    IF: "int m = 7\nproc main():\n\tint n = m - 4\n\tif m > 0: write(m)\n\t\
    if m < n:\n\t\tm = m + 1\n\t\twrite(m)\n\telse:\n\t\tn = n + 1\n\t\t\
    write(n)\n\t.\n."
}
