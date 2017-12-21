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
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Examples<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="code-example" data-example="ASSIGN" href="#">Assign</a></li>
                        <li><a class="code-example" data-example="FACTORIAL" href="#">Factorial</a></li>
                        <li><a class="code-example" data-example="FUNCTION" href="#">Function</a></li>
                        <li><a class="code-example" data-example="IF" href="#">If</a></li>
                        <li><a class="code-example" data-example="IO" href="#">IO</a></li>
                        <li><a class="code-example" data-example="OCTAL" href="#">Octal</a></li>
                        <li><a class="code-example" data-example="PROC" href="#">Procedure</a></li>
                        <li><a class="code-example" data-example="SCOPE_CHECKING" href="#">Scope Checking</a></li>
                        <li><a class="code-example" data-example="TYPE_CHECKING" href="#">Type Checking</a></li>
                        <li><a class="code-example" data-example="WHILE" href="#">While</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-3 col-container">
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
            <div id="display-specification" class="col-lg-9 col-container">
                <ul class="nav nav-tabs nav-justified">
                    <li class="active"><a data-toggle="tab" href="#overview">Overview</a></li>
                    <li><a data-toggle="tab" href="#programs">Programs</a></li>
                    <li><a data-toggle="tab" href="#declarations">Declarations</a></li>
                    <li><a data-toggle="tab" href="#commands">Commands</a></li>
                    <li><a data-toggle="tab" href="#expressions">Expressions</a></li>
                    <li><a data-toggle="tab" href="#lexicon">Lexicon</a></li>
                    <li><a data-toggle="tab" href="#predefined">Predefined</a></li>
                </ul>
                <div class="tab-content">
                    <div id="overview" class="tab-pane fade in active">
                        <p>Overview</p>
                    </div>
                    <div id="programs" class="tab-pane fade">
                        <p>Programs</p>
                    </div>
                    <div id="declarations" class="tab-pane fade">
                        <p>Declarations</p>
                    </div>
                    <div id="commands" class="tab-pane fade">
                        <p>Commands</p>
                    </div>
                    <div id="expressions" class="tab-pane fade">
                        <p>Expressions</p>
                    </div>
                    <div id="lexicon" class="tab-pane fade">
                        <p>Lexicon</p>
                    </div>
                    <div id="predefined" class="tab-pane fade">
                        <p>Predefined</p>
                    </div>
                </div>
            </div>
            <div id="display-program-tree" class="col-lg-6 col-container">
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
            <div id="display-contextual-container" class="col-lg-3 col-container">
                <div class="data-heading-container">
                    <h3><b>Node: </b><span></span></h3>
                </div>
                <div class="contextual-explanations">
                    <h3><b>Code Checker Actions</b></h3>
                    <ul></ul>
                </div>
                <div class="type-table-container">
                    <h3><b>Type Table</b></h3>
                    <div class="table-wrapper">
                        <table class="table table-striped
                                            table-hover
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
            <div id="display-generation-container" class="col-lg-3 col-container">
                <div class="data-heading-container">
                    <h3><b>Node: </b><span></span></h3>
                </div>
                <div class="generation-explanations">
                    <h3><b>Code Generator Actions</b></h3>
                    <ul></ul>
                </div>
                <div class="code-template">
                    <h3><b>Code<br>Template</b></h3>
                    <ul></ul>
                </div>
                <div class="object-code">
                    <h3><b>Object<br>Code</b></h3>
                    <ul></ul>
                </div>
                <div class="address-table-container">
                    <h3><b>Address Table</b></h3>
                    <div class="table-wrapper">
                        <table class="table table-striped
                                            table-hover
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
@endsection
