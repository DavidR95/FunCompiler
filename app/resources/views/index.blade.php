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
                        <h2>Overview</h2>
                        <p>
                            Fun is a simple imperative language. Its purpose is
                            to illustrate some aspects of programming
                            language concepts and implementation.
                        </p>
                        <p>
                            This application provides a means to enter any Fun
                            program into the code editor and visualise either
                            the contextual analysis phase or the code generation
                            phase of compilation.
                        </p>
                        <p>
                            Note that this application is not a general purpose
                            interpreter for the Fun language and does behave the
                            way a standard REPL might. The resulting object is
                            never executed. This does mean that any
                            syntactically correct program can be entered and
                            visualised, regardless of contextual errors,
                            infinite loops, etc...
                        </p>
                        <p>
                            Here is an example of a Fun program:
                        </p>
                        <div class="code-snippet"></div>
                        <p>
                            This program declares a global variable verbose, a
                            function fac, and a procedure main. The function fac
                            has a formal parameter, and ends by returning a
                            result. A procedure may also have aformal parameter
                            (although procedure main does not), but does not
                            return a result. Both functions and procedures may
                            declare local variables.
                        </p>
                    </div>
                    <div id="programs" class="tab-pane fade">
                        <h2>Programs</h2>
                        <h3>Syntax</h3>
                        <div class="grammar-snippet">
<pre>
prog = var-decl* proc-decl+ eof
</pre>
                        </div>
                        <h3>Scope and Type Rules</h3>
                        <p>
                            Variables declared at the program level are global
                            in scope. All procedures and functions are global in
                            scope.
                        </p>
                        <p>
                            No variable may be accessed before it is declared.
                            Likewise, no procedure or function may be called
                            before it is declared; however, a procedure or
                            function may call itself.
                        </p>
                        <p>
                            The program must include a procedure main with no
                            parameter.
                        </p>
                        <h3>Semantics</h3>
                        <p>
                            The program is run by first elaborating its global
                            variable declarations (if any) and then calling the
                            procedure main.
                        </p>
                    </div>
                    <div id="declarations" class="tab-pane fade">
                        <h2>Declarations</h2>
                        <h3>Syntax</h3>
                        <div class="grammar-snippet">
<pre>
proc-decl = ‘proc’ ident ‘(’ formal ‘)’ ‘:’
                var-decl * seq-com ‘.’                  – procedure declaration
          | ‘func’ type ident ‘(’ formal ‘)’ ‘:’
                var-decl * seq-com
                ‘return’ expr ‘.’                       – function declaration

formal = ( type ident ) ?                               – formal parameter

var-decl = type ident ‘=’ expr                          – variable declaration

type = ‘bool’
     | ‘int’
</pre>
                        </div>
                        <h3>Scope and Type Rules</h3>
                        <p>
                            Variables declared inside a procedure or function
                            are local in scope. Formal parameters are treated as
                            local variables.
                        </p>
                        <p>
                            Every variable has a declared type, either bool or
                            int; the expression in the variable declaration
                            must have the same type.
                        </p>
                        <p>
                            Likewise, every formal parameter has a declared
                            type, either bool or int.
                        </p>
                        <p>
                            A procedure has type T → void (if it has a formal
                            parameter of type T) or void → void (if it has
                            no formal parameter).
                        </p>
                        <p>
                            A function with result type T ' has type T → T '
                            (if it has a formal parameter of type T) or void → T '
                            (if it has no formal parameter). The expression
                            following ‘return’ must have type T '.
                        </p>
                        <h3>Semantics</h3>
                        <p>
                            A variable declaration is elaborated by first
                            evaluating its expression to the value v,
                            then creating a variable initialised to v, then
                            binding the identifier to the variable.
                        </p>
                        <p>
                            A procedure or function declaration is elaborated by
                            binding the identifier to the procedure or function.
                        </p>
                    </div>
                    <div id="commands" class="tab-pane fade">
                        <h2>Commands</h2>
                        <h3>Syntax</h3>
                        <div class="grammar-snippet">
<pre>
com = ident ‘=’ expr                                        – assignment command
    | ident ‘(’ actual ‘)’                                  – procedure call
    | ‘if’ expr ‘:’ seq-com
        ( ‘.’ | ‘else’ ‘:’ seq-com ‘.’ )                    – if-command
    | ‘while’ expr ‘:’ seq-com ‘.’                          – while-command
seq-com = com *
</pre>
                        </div>
                        <h3>Scope and Type Rules</h3>
                        <p>
                            In an assignment command, the identifier must be
                            bound to a variable, and the expression must have
                            the same type as that variable.
                        </p>
                        <p>
                            In an if-command, the expression must be of type
                            bool.
                        </p>
                        <p>
                            In a while-command, the expression must be of type
                            bool.
                        </p>
                        <p>
                            In a procedure call, the identifier must be bound
                            to a procedure. If the procedure has type T → void,
                            the procedure call must have an actual parameter of
                            type T. If the procedure has type void → void,
                            the procedure call must have no actual parameter.
                        </p>
                        <h3>Semantics</h3>
                        <p>
                            An assignment command is executed by first
                            evaluating its expression to the value v, then
                            storing v in the variable.
                        </p>
                        <p>
                            An if-command with no ‘else:’ is executed by first
                            evaluating its expression to the bool value b,
                            and then either (a) executing the command after ‘:’
                            if b is true, or (b) doing nothing if b is false.
                            An if-command with ‘else:’ is executed by first
                            evaluating its expression to the bool value b, and
                            then either (a) executing the command after ‘:’ if
                            b is true, or (b) executing the command after
                            ‘else:’ if b is false.
                        </p>
                        <p>
                            A while-command is executed by first evaluating its
                            expression to the bool value b, and then either
                            (a) exiting if b is false, or (b) executing the
                            command after ‘:’ and then repeating the whole
                            while command if b is true.
                        </p>
                        <p>
                            A procedure call without an actual parameter is
                            executed by first elaborating the procedure’s local
                            variable declarations (if any), then executing the
                            procedure’s sequential command, then destroying
                            any local variables. A procedure call with an actual
                            parameter is executed by first evaluating its
                            actual parameter to the value v, then creating a
                            local variable (the formal parameter) initialised to
                            v, then elaborating the procedure’s local variable
                            declarations (if any), then executing the procedure’s
                            sequential command, then destroying the formal
                            parameter and any other local variables.
                        </p>
                        <p>
                            A sequential command is executed by executing its
                            constituent commands in strict order.
                        </p>
                    </div>
                    <div id="expressions" class="tab-pane fade">
                        <h2>Expressions</h2>
                        <h3>Syntax</h3>
                        <div class="grammar-snippet">
<pre>
expr = sec-expr ( (‘<’ | ‘>’ | ‘==’ ) sec-expr ) ? – binary operator application

sec-expr = prim-expr
    ( ( ‘+’ | ‘-’ | ‘*’ | ‘/’ ) prim-expr ) *      – binary operator application

prim-expr = ‘false’
          | ‘true’
          | num                                    – numeral
          | ident                                  – variable
          | ident ‘(’ actual ‘)’                   – function call
          | ‘not’ prim-expr                        – unary operator application
          | ‘(’ expr ‘)’                           – parenthesized expression

actual = expr?                                     – actual parameter
</pre>
                        </div>
                        <h3>Scope and Type Rules</h3>
                        <p>
                            A unary operator application has one sub-expression
                             whose type must be consistent with the type of
                            the operator. The type of the unary operator
                            application is determined by the type of the
                            operator. The unary operator ‘not’ has type
                            bool → bool.
                        </p>
                        <p>
                            A binary operator application has two
                            sub-expressions whose types must be consistent with
                            the type of the operator. The type of the binary
                            operator application is determined by the type of
                            the operator.
                        </p>
                        <p>
                            The binary operators ‘+’, ‘-’, ‘*’, and ‘/’ have
                            type (int × int) → int. The binary operators ‘==’,
                            ‘<’, and ‘>’ have type (int × int) → bool.
                        </p>
                        <p>
                            In a function call, the identifier must be bound to
                            a function. If the function has type T → T ', the
                            function call must have an actual parameter of type
                            T. If the function has type void → T ', the
                            function call must have no actual parameter. In
                            either case the type of the function call is T '
                        </p>
                        <h3>Semantics</h3>
                        <p>
                            A unary operator application is evaluated by first
                            evaluating its sub-expression to the value v, then
                            applying the unary operator to v.
                        </p>
                        <p>
                            A binary operator application is evaluated by first
                            evaluating its two sub-expressions to the values v1
                            and v2, then applying the binary operator to v1 and
                            v2.
                        </p>
                        <p>
                            A function call without an actual parameter is
                            evaluated by first elaborating the function’s local
                            variable declarations (if any), then executing the
                            function’s sequential command, then evaluating the
                            ‘return’ expression to the value v', then destroying
                            any local variables. A function call with an
                            actual parameter is evaluated by first evaluating
                            its actual parameter to the value v, then creating
                            a local variable (formal parameter) initialised to
                            v, then elaborating the function’s local variable
                            declarations (if any), then executing the function’s
                            sequential command, then evaluating the ‘return’
                            expression to the value v', then destroying the
                            formal parameter and any other local variables.
                            In either case, the value of the function call is v'.
                        </p>
                    </div>
                    <div id="lexicon" class="tab-pane fade">
                        <h2>Lexicon</h2>
                        <h3>Syntax</h3>
                        <div class="grammar-snippet">
<pre>
num = digit+                             – numeral

ident = letter ( letter | digit ) *      – identifier

space = ( ‘ ’ | ‘\t’ ) +                 – white space

eol = ‘\r’ ? ‘\n’                        – end-of-line

comment = ‘#’ comment-char * ‘\r’ ? ‘\n’

comment-char = …                         – any character other than ‘\r’ or ‘\n’

digit = ‘0’ | ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’

letter = ‘A’ | ‘B’ | ‘C’ | ‘D’ | ‘E’ | ‘F’ | ‘G’ | ‘H’ | ‘I’ |
         ‘J’ | ‘K’ | ‘L’ | ‘M’ | ‘N’ | ‘O’ | ‘P’ | ‘Q’ | ‘R’ |
         ‘S’ | ‘T’ | ‘U’ | ‘V’ | ‘W’ | ‘X’ | ‘Y’ | ‘Z’ |
         ‘a’ | ‘b’ | ‘c’ | ‘d’ | ‘e’ | ‘f’ | ‘g’ | ‘h’ | ‘i’ |
         ‘j’ | ‘k’ | ‘l’ | ‘m’ | ‘n’ | ‘o’ | ‘p’ | ‘q’ | ‘r’ |
         ‘s’ | ‘t’ | ‘u’ | ‘v’ | ‘w’ | ‘x’ | ‘y’ | ‘z’
</pre>
                        </div>
                        <p>
                            Spaces, ends-of-lines, and comments do not influence
                             the program’s phrase structure. They are there
                            for human readers only.
                        </p>
                    </div>
                    <div id="predefined" class="tab-pane fade">
                        <h2>Predefined</h2>
                        <p>
                            Fun has two predefined procedures and functions:
                        </p>
                        <div class="code-snippet"></div>
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
