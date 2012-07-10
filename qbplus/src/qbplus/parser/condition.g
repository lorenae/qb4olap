grammar condition;
options{
   output=AST; 
   ASTLabelType=CommonTree; 
 }
/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
prog :expr; 
expr : unary  ((AND|OR) unary)*	
; 
unary : (atom| NOT atom)*
; 
atom
: url RELOPER string  ->^(RELOPER url string)
|url RELOPER NUM  ->^(RELOPER url NUM)		
|LPAREN expr RPAREN ->LPAREN  expr RPAREN
;

url	:	'http'(CHAR)*;
string	:	' " ' (CHAR)* ' " ';


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
AND	:	'&&';
OR	:	'||';
LPAREN : '(' ; 
RPAREN :  ')'  ; 
//BINARYOPER :('AND'|'OR');
RELOPER:('>'|'>='|'<'|'<='|'='|'!=');
NOT :  '!' ;  
CHAR: ('a'..'z'|'A'..'Z'|'0'..'9'|':'|'/'|'.'|'-'|'#'|'@'|);

NUM:  ('0'..'9'|'.')* ;

NEWLINE:'\r'? '\n' ; 
WS : (' '|'\t'|'\n'|'\r')+ {$channel = HIDDEN;} ; 

