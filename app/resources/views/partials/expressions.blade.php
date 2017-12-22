<h2>Expressions</h2>
<h3>Syntax</h3>
<div class="grammar-snippet">
<pre>
expr = sec-expr ((‘<’ | ‘>’ | ‘==’ ) sec-expr)?    – binary operator application

sec-expr = prim-expr
    (( ‘+’ | ‘-’ | ‘*’ | ‘/’ ) prim-expr)*         – binary operator application

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
