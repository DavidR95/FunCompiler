let mix = require('laravel-mix');

mix.js('resources/assets/js/app.js', 'public/js')
   .sass('resources/assets/sass/app.scss', 'public/css')
   .js('resources/assets/js/send-execute-request.js', 'public/js')
   .js('resources/assets/js/codemirror.js', 'public/js')
   .js('resources/assets/js/main.js', 'public/js');
