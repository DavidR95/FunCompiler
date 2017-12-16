var CodeTemplates = module.exports = {
    getTemplate: function(templateName) {
        return templates[templateName];
    }
}

var templates = {
    WHILE: [
        "Code to evaluate expr",
        "JUMPF",
        "Code to execute com",
        "JUMP"
    ]
}
