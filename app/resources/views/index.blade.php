@extends('layouts.master')
@section('content')
    <textarea rows="10" cols="90" name="program" form="form"></textarea>
    <form id="form" action="{{ route('execute') }}" method="post">
        {{ csrf_field() }}
        <input type="submit" value="Submit">
    </form>
    <div class="col-md-2">
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
@endsection
