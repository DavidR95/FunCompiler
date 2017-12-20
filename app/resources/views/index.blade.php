@extends('layouts.master')
@section('content')
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Fun<b>Compiler</b></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Default</a></li>
                <li><a href="#">Static top</a></li>
                <li><a href="#">Fixed top</a></li>
            </ul>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-3 table-container">
                <div class="left-container">
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
            <div class="col-lg-6 table-container">
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
            <div class="col-lg-3 table-container">
                <div class="right-contextual-container">
                    <div class="data-heading-container">
                        <h3><b>Node: </b><span></span></h3>
                    </div>
                    <div class="data-container">
                        <div class="contextual-explanations">
                            <h3><b>Code Checker Actions</b></h3>
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
                        <div class="generation-explanations">
                            <h3><b>Code Generator Actions</b></h3>
                            <ul></ul>
                        </div>
                        <div class="code-template-object-code-container">
                            <div class="code-template">
                                <h3><b>Code<br>Template</b></h3>
                                <ul></ul>
                            </div>
                            <div class="object-code">
                                <h3><b>Object<br>Code</b></h3>
                                <ul></ul>
                            </div>
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
