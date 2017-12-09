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
                                <button class="btn btn-execute" type="submit"><b>Execute</b></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 table-container">
                <div class="center-container">
                    <div class="controls-container">
                        <i id="reverse-button" class="glyphicon glyphicon-step-backward"></i>
                        <i id="play-button" class="glyphicon glyphicon-play"></i>
                        <i id="pause-button" class="glyphicon glyphicon-pause"></i>
                        <i id="forward-button" class="glyphicon glyphicon-step-forward"></i>
                        <button id="generation-button" href="#" class="btn btn-execute btn-small pull-right">Code-generation</button>
                        <button id="contextual-button" href="#" class="btn btn-execute btn-small pull-right disabled">Contextual-analysis</button>
                    </div>
                    <div class="program-tree-container"></div>
                </div>
            </div>
            <div class="col-md-3 table-container">
                <div class="right-contextual-container">
                    <div class="data-heading-container">
                        <h4><b>Node: </b><span></span></h4>
                    </div>
                    <div class="data-container">
                        <div class="contextual-explanations">
                            <h4>Code Checker Actions</h4>
                            <p></p>
                        </div>
                        <div class="type-table-container">
                            <h4>Type Table</h4>
                            <table class="table table-striped
                                                table-hover
                                                table-responsive
                                                table-bordered
                                                type-table">
                                <thead>
                                    <tr>
                                        <th>Scope</th>
                                        <th>ID</th>
                                        <th>Type</th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="right-generation-container">
                    <div class="data-heading-container">
                        <h4><b>Node: </b><span></span></h4>
                    </div>
                    <div class="data-container">
                        <div class="code-template">
                            <h4>Code Template</h4>
                            <img>
                        </div>
                        <div class="generation-explanations">
                            <h4>Code Generator Actions</h4>
                            <p></p>
                        </div>
                        <div class="address-table-container">
                            <h4>Address Table</h4>
                            <table class="table table-striped
                                                table-hover
                                                table-responsive
                                                table-bordered
                                                address-table">
                                <thead>
                                    <tr>
                                        <th>Scope</th>
                                        <th>ID</th>
                                        <th>Address</th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
