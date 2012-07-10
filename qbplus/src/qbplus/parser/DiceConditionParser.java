// $ANTLR 3.4 /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g 2012-07-09 23:43:37
package qbplus.parser; 
import java.util.HashMap; 
import java.util.ArrayList; 


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class DiceConditionParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "LPAREN", "NEWLINE", "NOT", "OR", "RELOPER", "RPAREN", "STRING", "WS"
    };

    public static final int EOF=-1;
    public static final int AND=4;
    public static final int LPAREN=5;
    public static final int NEWLINE=6;
    public static final int NOT=7;
    public static final int OR=8;
    public static final int RELOPER=9;
    public static final int RPAREN=10;
    public static final int STRING=11;
    public static final int WS=12;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public DiceConditionParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public DiceConditionParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return DiceConditionParser.tokenNames; }
    public String getGrammarFileName() { return "/home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g"; }

     
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


    protected static class prog_scope {
        HashMap<String,String>  foundLevels;
        HashMap<String,String> foundMeasures;
    }
    protected Stack prog_stack = new Stack();


    public static class prog_return extends ParserRuleReturnScope {
        public HashMap<String,String> fLevels;
        public HashMap<String,String> fMeasures;
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prog"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:73:1: prog[HashMap<String,String> levels, HashMap<String,String> measures] returns [HashMap<String,String> fLevels, HashMap<String,String> fMeasures] : expr ;
    public final DiceConditionParser.prog_return prog(HashMap<String,String> levels, HashMap<String,String> measures) throws RecognitionException {
        prog_stack.push(new prog_scope());
        DiceConditionParser.prog_return retval = new DiceConditionParser.prog_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        DiceConditionParser.expr_return expr1 =null;



         
        HashMap<String,String> fLevels;
        HashMap<String,String> fMeasures;
        this.levels = levels; 
        this.measures = measures;
        ((prog_scope)prog_stack.peek()).foundLevels = new HashMap<String,String>();
        ((prog_scope)prog_stack.peek()).foundMeasures = new HashMap<String,String>();

        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:88:2: ( expr )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:88:2: expr
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_expr_in_prog84);
            expr1=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr1.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);


            retval.fLevels =((prog_scope)prog_stack.peek()).foundLevels;
            retval.fMeasures = ((prog_scope)prog_stack.peek()).foundMeasures;

        }
         
        catch (RecognitionException e) { 
        throw e; 
        } 

        finally {
        	// do for sure before leaving
            prog_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "prog"


    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:90:1: expr : unary ( ( AND | OR ) unary )* ;
    public final DiceConditionParser.expr_return expr() throws RecognitionException {
        DiceConditionParser.expr_return retval = new DiceConditionParser.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set3=null;
        DiceConditionParser.unary_return unary2 =null;

        DiceConditionParser.unary_return unary4 =null;


        CommonTree set3_tree=null;

        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:90:6: ( unary ( ( AND | OR ) unary )* )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:90:8: unary ( ( AND | OR ) unary )*
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_unary_in_expr93);
            unary2=unary();

            state._fsp--;

            adaptor.addChild(root_0, unary2.getTree());

            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:90:15: ( ( AND | OR ) unary )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AND||LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:90:16: ( AND | OR ) unary
            	    {
            	    set3=(Token)input.LT(1);

            	    if ( input.LA(1)==AND||input.LA(1)==OR ) {
            	        input.consume();
            	        adaptor.addChild(root_0, 
            	        (CommonTree)adaptor.create(set3)
            	        );
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unary_in_expr103);
            	    unary4=unary();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unary4.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
         
        catch (RecognitionException e) { 
        throw e; 
        } 

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class unary_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "unary"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:1: unary : ( atom | NOT atom )* ;
    public final DiceConditionParser.unary_return unary() throws RecognitionException {
        DiceConditionParser.unary_return retval = new DiceConditionParser.unary_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token NOT6=null;
        DiceConditionParser.atom_return atom5 =null;

        DiceConditionParser.atom_return atom7 =null;


        CommonTree NOT6_tree=null;

        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:7: ( ( atom | NOT atom )* )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:9: ( atom | NOT atom )*
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:9: ( atom | NOT atom )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==LPAREN||LA2_0==STRING) ) {
                    alt2=1;
                }
                else if ( (LA2_0==NOT) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:10: atom
            	    {
            	    pushFollow(FOLLOW_atom_in_unary116);
            	    atom5=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom5.getTree());

            	    }
            	    break;
            	case 2 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:92:16: NOT atom
            	    {
            	    NOT6=(Token)match(input,NOT,FOLLOW_NOT_in_unary119); 
            	    NOT6_tree = 
            	    (CommonTree)adaptor.create(NOT6)
            	    ;
            	    adaptor.addChild(root_0, NOT6_tree);


            	    pushFollow(FOLLOW_atom_in_unary121);
            	    atom7=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom7.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
         
        catch (RecognitionException e) { 
        throw e; 
        } 

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "unary"


    public static class atom_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "atom"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:94:1: atom : (url= STRING RELOPER value= STRING ({...}?|{...}? -> ^( RELOPER $url $value) |{...}? -> ^( RELOPER $url $value) ) | LPAREN expr RPAREN -> LPAREN expr RPAREN );
    public final DiceConditionParser.atom_return atom() throws RecognitionException {
        DiceConditionParser.atom_return retval = new DiceConditionParser.atom_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token url=null;
        Token value=null;
        Token RELOPER8=null;
        Token LPAREN9=null;
        Token RPAREN11=null;
        DiceConditionParser.expr_return expr10 =null;


        CommonTree url_tree=null;
        CommonTree value_tree=null;
        CommonTree RELOPER8_tree=null;
        CommonTree LPAREN9_tree=null;
        CommonTree RPAREN11_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_RELOPER=new RewriteRuleTokenStream(adaptor,"token RELOPER");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:95:3: (url= STRING RELOPER value= STRING ({...}?|{...}? -> ^( RELOPER $url $value) |{...}? -> ^( RELOPER $url $value) ) | LPAREN expr RPAREN -> LPAREN expr RPAREN )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==STRING) ) {
                alt4=1;
            }
            else if ( (LA4_0==LPAREN) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:95:3: url= STRING RELOPER value= STRING ({...}?|{...}? -> ^( RELOPER $url $value) |{...}? -> ^( RELOPER $url $value) )
                    {
                    url=(Token)match(input,STRING,FOLLOW_STRING_in_atom134);  
                    stream_STRING.add(url);


                    RELOPER8=(Token)match(input,RELOPER,FOLLOW_RELOPER_in_atom136);  
                    stream_RELOPER.add(RELOPER8);


                    value=(Token)match(input,STRING,FOLLOW_STRING_in_atom140);  
                    stream_STRING.add(value);


                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:97:4: ({...}?|{...}? -> ^( RELOPER $url $value) |{...}? -> ^( RELOPER $url $value) )
                    int alt3=3;
                    switch ( input.LA(1) ) {
                    case AND:
                    case OR:
                        {
                        int LA3_1 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 1, input);

                            throw nvae;

                        }
                        }
                        break;
                    case EOF:
                        {
                        int LA3_2 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 2, input);

                            throw nvae;

                        }
                        }
                        break;
                    case RPAREN:
                        {
                        int LA3_3 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 3, input);

                            throw nvae;

                        }
                        }
                        break;
                    case STRING:
                        {
                        int LA3_4 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 4, input);

                            throw nvae;

                        }
                        }
                        break;
                    case LPAREN:
                        {
                        int LA3_5 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 5, input);

                            throw nvae;

                        }
                        }
                        break;
                    case NOT:
                        {
                        int LA3_6 = input.LA(2);

                        if ( ((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                            alt3=2;
                        }
                        else if ( ((levels.containsValue((url!=null?url.getText():null)))) ) {
                            alt3=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 6, input);

                            throw nvae;

                        }
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 0, input);

                        throw nvae;

                    }

                    switch (alt3) {
                        case 1 :
                            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:98:4: {...}?
                            {
                            if ( !((!measures.containsKey((url!=null?url.getText():null)) && !levels.containsValue((url!=null?url.getText():null)))) ) {
                                throw new FailedPredicateException(input, "atom", "!measures.containsKey($url.text) && !levels.containsValue($url.text)");
                            }

                            throw new RuntimeException("<"+(url!=null?url.getText():null)+"> is neither a measure nor a level" );

                            }
                   
                        case 2 :
                            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:100:4: {...}?
                            {
                            if ( !((measures.containsKey((url!=null?url.getText():null)) &&( isInteger((value!=null?value.getText():null)) || isDecimal((value!=null?value.getText():null))))) ) {
                                throw new FailedPredicateException(input, "atom", "measures.containsKey($url.text) &&( isInteger($value.text) || isDecimal($value.text))");
                            }

                            ((prog_scope)prog_stack.peek()).foundMeasures.put((url!=null?url.getText():null), "?measure_"+measCounter++);

                            // AST REWRITE
                            // elements: value, url, RELOPER
                            // token labels: value, url
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_value=new RewriteRuleTokenStream(adaptor,"token value",value);
                            RewriteRuleTokenStream stream_url=new RewriteRuleTokenStream(adaptor,"token url",url);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 101:95: -> ^( RELOPER $url $value)
                            {
                                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:101:97: ^( RELOPER $url $value)
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot(
                                stream_RELOPER.nextNode()
                                , root_1);

                                adaptor.addChild(root_1, stream_url.nextNode());

                                adaptor.addChild(root_1, stream_value.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                            }


                            retval.tree = root_0;

                            }
                            break;
                        case 3 :
                            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:102:4: {...}?
                            {
                            if ( !((levels.containsValue((url!=null?url.getText():null)))) ) {
                                throw new FailedPredicateException(input, "atom", "levels.containsValue($url.text)");
                            }

                            ((prog_scope)prog_stack.peek()).foundLevels.put((url!=null?url.getText():null), "?level_"+levelCounter++);

                            // AST REWRITE
                            // elements: value, RELOPER, url
                            // token labels: value, url
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_value=new RewriteRuleTokenStream(adaptor,"token value",value);
                            RewriteRuleTokenStream stream_url=new RewriteRuleTokenStream(adaptor,"token url",url);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 102:101: -> ^( RELOPER $url $value)
                            {
                                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:102:103: ^( RELOPER $url $value)
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot(
                                stream_RELOPER.nextNode()
                                , root_1);

                                adaptor.addChild(root_1, stream_url.nextNode());

                                adaptor.addChild(root_1, stream_value.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                            }


                            retval.tree = root_0;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:104:2: LPAREN expr RPAREN
                    {
                    LPAREN9=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom226);  
                    stream_LPAREN.add(LPAREN9);


                    pushFollow(FOLLOW_expr_in_atom228);
                    expr10=expr();

                    state._fsp--;

                    stream_expr.add(expr10.getTree());

                    RPAREN11=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom230);  
                    stream_RPAREN.add(RPAREN11);


                    // AST REWRITE
                    // elements: expr, LPAREN, RPAREN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 104:21: -> LPAREN expr RPAREN
                    {
                        adaptor.addChild(root_0, 
                        stream_LPAREN.nextNode()
                        );

                        adaptor.addChild(root_0, stream_expr.nextTree());

                        adaptor.addChild(root_0, 
                        stream_RPAREN.nextNode()
                        );

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException e) {
             throw e; 
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_expr_in_prog84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_expr93 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_set_in_expr97 = new BitSet(new long[]{0x00000000000009B0L});
    public static final BitSet FOLLOW_unary_in_expr103 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_atom_in_unary116 = new BitSet(new long[]{0x00000000000008A2L});
    public static final BitSet FOLLOW_NOT_in_unary119 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_atom_in_unary121 = new BitSet(new long[]{0x00000000000008A2L});
    public static final BitSet FOLLOW_STRING_in_atom134 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RELOPER_in_atom136 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_STRING_in_atom140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom226 = new BitSet(new long[]{0x00000000000009B0L});
    public static final BitSet FOLLOW_expr_in_atom228 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RPAREN_in_atom230 = new BitSet(new long[]{0x0000000000000002L});

}