tree grammar EvalDiceCondition;

options{
   tokenVocab=DiceCondition; // reuse token types 
  ASTLabelType=CommonTree; // $label will have type CommonTree 
  output=template; 
  rewrite=true;
 }

@header { 
import java.util.HashMap; 
} 

@members { 
/** Points at locals table built by the parser */ 
HashMap<String,String> levels;
HashMap<String,String> measures; 
}

prog[HashMap<String,String> levels, HashMap<String,String> measures]
@init { 
this.levels = levels; 
this.measures = measures;
}
 :expr EOF ; 

expr
: unary  ((AND|OR) unary)*
;
 
unary 
: (atom| NOT atom)*
; 

atom
: ^( RELOPER url=STRING value=STRING)
(
 {measures.containsKey($url.text)}? ->newNode( value={$value.text}, oper={$RELOPER.text}, var={measures.get($url.text)})
 |{levels.containsKey($url.text)}?  ->newNode( value={$value.text}, oper={$RELOPER.text}, var={levels.get($url.text)})
) 
|LPAREN expr RPAREN
;