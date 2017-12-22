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
