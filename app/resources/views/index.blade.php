<!doctype html>
<html lang="{{ app()->getLocale() }}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Laravel</title>

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css">

        <!-- Styles -->
        <style>
            html, body {
                background-color: #fff;
                color: black;
                font-family: 'Raleway', sans-serif;
                height: 100vh;
                margin: 0;
            }

            .full-height {
                height: 100vh;
            }

            .flex-center {
                align-items: center;
                display: flex;
                justify-content: center;
            }

            .position-ref {
                position: relative;
            }

            .top-right {
                position: absolute;
                right: 10px;
                top: 18px;
            }

            .content {
                text-align: center;
            }

            .title {
                font-size: 30px;
            }

            .links > a {
                color: #636b6f;
                padding: 0 25px;
                font-size: 12px;
                font-weight: 600;
                letter-spacing: .1rem;
                text-decoration: none;
                text-transform: uppercase;
            }

            .m-b-md {
                margin-bottom: 30px;
            }
        </style>
    </head>
    <body>
        <div class="flex-center position-ref full-height">
            <div class="content">
                <textarea rows="10" cols="90" name="program" form="form"></textarea>
                <form id="form" action="{{ route('execute') }}" method="post">
                    {{ csrf_field() }}
                    <input type="submit" value="Submit">
                </form>
                <div class="title m-b-md">
                    @if (!empty($body))
                        Number of Syntax Errors: {{ $body['numSyntaxErrors'] }}<br>
                        Number of Contextual Errors: {{ $body['numContextualErrors'] }}<br>
                        Syntax Errors:
                        @foreach ($body['syntaxErrors'] as $error)
                            {{ $error }},
                        @endforeach
                        <br>
                        Contextual Errors:
                        @foreach ($body['contextualErrors'] as $error)
                            {{ $error }},
                        @endforeach
                        <br>
                        Object Code:<br>
                        @foreach ($body['objectCode'] as $code)
                            {{ $code }}<br>
                        @endforeach
                        Output: {{ $body['output'] }}
                    @endif
                </div>
            </div>
        </div>
    </body>
</html>
