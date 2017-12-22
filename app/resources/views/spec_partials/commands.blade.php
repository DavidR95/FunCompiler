<h2>Commands</h2>
<h3>Syntax</h3>
<div class="grammar-snippet">
<pre>
com = ident ‘=’ expr                                        – assignment command
    | ident ‘(’ actual ‘)’                                  – procedure call
    | ‘if’ expr ‘:’ seq-com
        ( ‘.’ | ‘else’ ‘:’ seq-com ‘.’ )                    – if-command
    | ‘while’ expr ‘:’ seq-com ‘.’                          – while-command

seq-com = com *                                             – sequential command 
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
