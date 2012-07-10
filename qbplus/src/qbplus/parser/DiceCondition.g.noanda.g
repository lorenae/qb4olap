grammar DiceCondition;

options{
   output=AST; 
   ASTLabelType=CommonTree; 
 }
 
@header { 
import java.util.HashMap; 
import java.util.ArrayList; 
} 

@members { 
/** Points at locals table built by the parser */ 
HashMap<String,String> levels;
HashMap<String,String> measures; 
int levelCounter = 0;
int measCounter = 0;



public boolean isInteger( String input )  
{ 
   try  
   {  
      Integer.parseInt( input );  
      return (true);
   }  
   catch( Exception e)  
   {  
      return false;  
   }  
}  

public boolean isDecimal( String input )  
{  
   try  
   {  
      Float.valueOf(input);
      return true;
   }  
   catch( Exception e)  
   {  
      return false;  
   }  
}  

protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException 
{ 
  throw new MismatchedTokenException(ttype, input); 
} 
public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) 
throws RecognitionException 
{ 
  throw e; 
} 
} 


// Alter code generation so catch-clauses get replace with 
// this action. 
@rulecatch { 
catch (RecognitionException e) { 
throw e; 
} 
} 


/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
 
prog[HashMap<String,String> levels, HashMap<String,String> measures] returns [HashMap<String,String> fLevels, HashMap<String,String> fMeasures]
scope { HashMap<String,String>  foundLevels; HashMap<String,String> foundMeasures;} 
@init { 
HashMap<String,String> fLevels;
HashMap<String,String> fMeasures;
this.levels = levels; 
this.measures = measures;
$prog::foundLevels = new HashMap<String,String>();
$prog::foundMeasures = new HashMap<String,String>();
} 
@after{
fLevels =$prog::foundLevels;
fMeasures = $prog::foundMeasures;
}
: ( line )* EOF ; 

line
:expr NEWLINE; 

expr
: unary  ((AND|OR) unary)*	
; 
unary 
: (atom| NOT atom)*
; 
atom
: url=URL RELOPER value= LITERAL  
(
   {!measures.containsKey($url.text) && !levels.containsValue($url.text)}? 
	  {throw new RuntimeException("<"+$url.text+"> is neither a measure nor a level" );}
  |{measures.containsKey($url.text) &&( isInteger($value.text) || isDecimal($value.text))}? 
                            {$prog::foundMeasures.put($url.text, "?measure_"+measCounter++);} ->^( RELOPER URL LITERAL)
  |{levels.containsValue($url.text)}? {$prog::foundLevels.put($url.text, "?level_"+levelCounter++);}->^(RELOPER URL LITERAL)
 )
|LPAREN expr RPAREN ->LPAREN  expr RPAREN
;catch[RecognitionException e] { throw e; } 



/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

LPAREN : '(' ; 
RPAREN :  ')'  ; 
//BINARYOPER :('AND'|'OR');
RELOPER:('>'|'>='|'<'|'<='|'='|'!=');
NOT :  '!' ;  
AND	:	'&&';
OR	:	'||';
/*
GT :'>';
GEQ :'>=';
LT :'<';
LEQ :'<=';
NEQ :'!=';
EQ :'=';
*/
LITERAL : ('a'..'z'|'A'..'Z'|'0'..'9'|','|'.')* ; 
URL : ('a'..'z'|'A'..'Z'|'0'..'9'|':'|'/'|'.'|'-'|'#')* ; 

NEWLINE:'\r'? '\n' ; 
WS : (' '|'\t'|'\n'|'\r')+ {$channel = HIDDEN;} ; 