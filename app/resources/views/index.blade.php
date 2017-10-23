@extends('layouts.master')
@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3">
                <h1>Fun<b>Compiler</b></h1>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed dignissim dictum tincidunt. Pellentesque sollicitudin malesuada tellus, at aliquam leo rutrum vel. Sed tellus nulla, vestibulum nec ex id, accumsan convallis velit. Quisque feugiat mattis ipsum, in accumsan sem fringilla eu. Nulla odio neque, commodo id vulputate non, vestibulum et orci. Curabitur nec risus nec tortor eleifend ultricies quis sed erat. Nam luctus arcu sit amet sodales ultrices.
                </p>
                <textarea rows="10" cols="90" name="program" form="form"></textarea>
                <form id="form" action="{{ route('execute') }}" method="post">
                    {{ csrf_field() }}
                    <input type="submit" value="Submit">
                </form>
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
@endsection
