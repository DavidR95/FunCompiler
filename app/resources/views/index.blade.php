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
                        <form id="execute-form" method="post">
                            <div class="form-group">
                                <textarea id="code-editor"
                                          name="program"
                                          autofocus></textarea>
                            </div>
                            <div class="form-submit">
                                <button id="ca-button"
                                        class="hvr-bounce-to-left"
                                        value="ca"
                                        type="submit">
                                    <b>Contextual Analysis</b>
                                </button>
                                <button id="cg-button"
                                        class="hvr-bounce-to-right"
                                        value="cg"
                                        type="submit">
                                    <b>Code Generation</b>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 table-container">
                <div class="center-container">
                    <div class="controls-container">
                        <h3><b><span></span></b></h3>
                        <div class="playback-buttons">
                            <i id="reverse-button"
                               class="glyphicon glyphicon-step-backward"></i>
                            <i id="play-button"
                               class="glyphicon glyphicon-play"></i>
                            <i id="pause-button"
                               class="glyphicon glyphicon-pause"></i>
                            <i id="forward-button"
                               class="glyphicon glyphicon-step-forward"></i>
                        </div>
                    </div>
                    <div class="program-tree-container"></div>
                </div>
            </div>
            <div class="col-md-3 table-container">
                <div class="right-contextual-container">
                    <div class="data-heading-container">
                        <h3><b>Node: </b><span></span></h3>
                    </div>
                    <div class="data-container">
                        <div class="contextual-explanations">
                            <h3><b>Code Checker Actions</b></h4>
                            <ul></ul>
                        </div>
                        <div class="type-table-container">
                            <h3><b>Type Table</b></h3>
                            <table class="table table-striped
                                                table-hover
                                                table-responsive
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
                        <h3><b>Node: </b><span></span></h3>
                    </div>
                    <div class="data-container">
                        <div class="code-template-object-code-container">
                            <div class="code-template">
                                <h3><b>Code Template</b></h3>
                                <ul></ul>
                            </div>
                            <div class="object-code">
                                <h3><b>Object Code</b></h3>
                                <ul></ul>
                            </div>
                        </div>
                        <div class="generation-explanations">
                            <h3><b>Code Generator Actions</b></h3>
                            <ul></ul>
                        </div>
                        <div class="address-table-container">
                            <h3><b>Address Table</b></h3>
                            <table class="table table-striped
                                                table-hover
                                                table-responsive
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
