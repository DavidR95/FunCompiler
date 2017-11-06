@extends('layouts.master')
@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-4 table-container">
                <div class="left-container">
                    <div class="title-container">
                        <h1>Fun<b>Compiler</b></h1>
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed facilisis egestas lacus eget semper. Aliquam erat volutpat. In aliquet sodales tortor eu ornare. Curabitur dignissim eget dolor a malesuada.
                        </p>
                    </div>
                    <div class="program-input-container">
                        <form id="execute-form" action="{{ route('execute') }}" method="post">
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
            <div class="col-sm-5 table-container">
                <div class="center-container">
                    <div class="controls-container">
                        <p>Scope-checking | Type-checking | Code-generation</p>
                    </div>
                    <div class="program-tree-container">
                        <div class="program-tree"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 table-container">
                <div class="right-container">
                    <div class="object-code-heading-container">
                        <p>Object Code</p>
                    </div>
                    <div class="object-code-output-container">
                        <div class="object-code-container">
                            <div class="object-code"></div>
                        </div>
                        <div class="output-container">
                            <div class="output-heading-container">
                                <p>Output</p>
                            </div>
                            <div class="output"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
@section('scripts')
    <script src="{{ asset('js/send-execute-request.js') }}"></script>
@endsection
