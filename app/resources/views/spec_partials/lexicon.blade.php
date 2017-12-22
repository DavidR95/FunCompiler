<h2>Lexicon</h2>
<h3>Syntax</h3>
<div class="grammar-snippet">
<pre>
num = digit+                             – numeral

ident = letter (letter | digit)*         – identifier

space = (‘ ’ | ‘\t’)+                    – white space

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
