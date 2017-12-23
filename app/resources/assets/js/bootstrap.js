try {
    // Require JQuery, globally available with '$' and 'jQuery'
    window.$ = window.jQuery = require('jquery');
    // Require Bootstrap
    require('bootstrap-sass');
    // Require CodeEditors
    require("./codeEditors.js");
    // Require Execute
    require("./execute.js");
} catch (e) {}
