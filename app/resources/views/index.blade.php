@extends('layouts.master')
@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3">
                <h1>Fun<b>Compiler</b></h1>
                <div class="program-input-container">
                    <textarea name="program" form="program-form"></textarea>
                    <form id="program-form" action="{{ route('execute') }}" method="post">
                        {{ csrf_field() }}
                        <input class="btn btn-success" type="submit" value="Execute">
                    </form>
                </div>
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
