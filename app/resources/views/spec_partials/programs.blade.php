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
