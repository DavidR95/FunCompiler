try {
    // Import JQuery module, globally available with '$' and 'jQuery'
    window.$ = window.jQuery = require('jquery');
    // Import Bootstrap module
    require('bootstrap-sass');
    // Import CodeEditors module
    require("./codeEditors.js");
    // Import CodeSubmit module
    require("./codeSubmit.js");
} catch (e) {}
