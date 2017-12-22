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
