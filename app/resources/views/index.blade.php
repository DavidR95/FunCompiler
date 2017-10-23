@extends('layouts.master')
@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-4 outer-left-container">
                <div class="inner-left-container">
                    <div class="title-container">
                        <h1>Fun<b>Compiler</b></h1>
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed facilisis egestas lacus eget semper. Aliquam erat volutpat. In aliquet sodales tortor eu ornare. Curabitur dignissim eget dolor a malesuada.
                        </p>
                    </div>
                    <div class="program-input-container">
                        <form action="{{ route('execute') }}" method="post">
                            {{ csrf_field() }}
                            <div class="form-group">
                                <textarea class="form-control" name="program" autofocus></textarea>
                            </div>
                            <div class="form-submit">
                                <button class="btn btn-success" type="submit"><b>Execute</b></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-sm-5">
            </div>
            {{-- @if (!empty($body))
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
            @endif --}}
        </div>
    </div>
@endsection
