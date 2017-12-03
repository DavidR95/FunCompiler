@extends('layouts.master')
@section('content')
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3 table-container">
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
                                <textarea id="code-editor" name="program" autofocus></textarea>
                            </div>
                            <div class="form-submit">
                                <button class="btn btn-success" type="submit"><b>Execute</b></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 table-container">
                <div class="center-container">
                    <div class="controls-container">
                        <!-- change how styling works here -->
                        <p class="pull-left" id="play-button">Play</p>
                        <p class="pull-right">Contextual-analysis | Code-generation</p>
                    </div>
                    <div class="program-tree-container">
                        <div class="program-tree"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 table-container">
                <div class="right-container">
                    <div class="data-heading-container">
                        <p>Data</p>
                    </div>
                    <div class="data-container">
                        <div class="data">
                            <div class="typeTable"></div>
                            <br>
                            <div class="explanations"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
@section('scripts')
    <script src="{{ asset('js/codemirror.js') }}"></script>
    <script src="{{ asset('js/send-execute-request.js') }}"></script>
@endsection
