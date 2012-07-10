// $ANTLR 3.4 /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g 2012-07-09 20:48:37
 package qbplus.parser;
import java.util.HashMap; 


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.HashMap;
@SuppressWarnings({"all", "warnings", "unchecked"})
public class EvalDiceCondition extends TreeParser {
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
    public TreeParser[] getDelegates() {
        return new TreeParser[] {};
    }

    // delegators


    public EvalDiceCondition(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }
    public EvalDiceCondition(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected StringTemplateGroup templateLib =
  new StringTemplateGroup("EvalDiceConditionTemplates", AngleBracketTemplateLexer.class);

public void setTemplateLib(StringTemplateGroup templateLib) {
  this.templateLib = templateLib;
}
public StringTemplateGroup getTemplateLib() {
  return templateLib;
}
/** allows convenient multi-value initialization:
 *  "new STAttrMap().put(...).put(...)"
 */
public static class STAttrMap extends HashMap {
  public STAttrMap put(String attrName, Object value) {
    super.put(attrName, value);
    return this;
  }
  public STAttrMap put(String attrName, int value) {
    super.put(attrName, new Integer(value));
    return this;
  }
}
    public String[] getTokenNames() { return EvalDiceCondition.tokenNames; }
    public String getGrammarFileName() { return "/home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g"; }

     
    /** Points at locals table built by the parser */ 
    HashMap<String,String> levels;
    HashMap<String,String> measures; 


    public static class prog_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "prog"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:20:1: prog[HashMap<String,String> levels, HashMap<String,String> measures] : expr EOF ;
    public final EvalDiceCondition.prog_return prog(HashMap<String,String> levels, HashMap<String,String> measures) throws RecognitionException {
        EvalDiceCondition.prog_return retval = new EvalDiceCondition.prog_return();
        retval.start = input.LT(1);


         
        this.levels = levels; 
        this.measures = measures;

        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:25:2: ( expr EOF )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:25:3: expr EOF
            {
            pushFollow(FOLLOW_expr_in_prog69);
            expr();

            state._fsp--;


            match(input,EOF,FOLLOW_EOF_in_prog71); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "prog"


    public static class expr_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "expr"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:27:1: expr : unary ( ( AND | OR ) unary )* ;
    public final EvalDiceCondition.expr_return expr() throws RecognitionException {
        EvalDiceCondition.expr_return retval = new EvalDiceCondition.expr_return();
        retval.start = input.LT(1);


        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:28:3: ( unary ( ( AND | OR ) unary )* )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:28:3: unary ( ( AND | OR ) unary )*
            {
            pushFollow(FOLLOW_unary_in_expr81);
            unary();

            state._fsp--;


            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:28:10: ( ( AND | OR ) unary )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AND||LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:28:11: ( AND | OR ) unary
            	    {
            	    if ( input.LA(1)==AND||input.LA(1)==OR ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    pushFollow(FOLLOW_unary_in_expr91);
            	    unary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class unary_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "unary"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:31:1: unary : ( atom | NOT atom )* ;
    public final EvalDiceCondition.unary_return unary() throws RecognitionException {
        EvalDiceCondition.unary_return retval = new EvalDiceCondition.unary_return();
        retval.start = input.LT(1);


        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:32:3: ( ( atom | NOT atom )* )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:32:3: ( atom | NOT atom )*
            {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:32:3: ( atom | NOT atom )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==LPAREN||LA2_0==RELOPER) ) {
                    alt2=1;
                }
                else if ( (LA2_0==NOT) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:32:4: atom
            	    {
            	    pushFollow(FOLLOW_atom_in_unary105);
            	    atom();

            	    state._fsp--;


            	    }
            	    break;
            	case 2 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:32:10: NOT atom
            	    {
            	    match(input,NOT,FOLLOW_NOT_in_unary108); 

            	    pushFollow(FOLLOW_atom_in_unary110);
            	    atom();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "unary"


    public static class atom_return extends TreeRuleReturnScope {
        public StringTemplate st;
        public Object getTemplate() { return st; }
        public String toString() { return st==null?null:st.toString(); }
    };


    // $ANTLR start "atom"
    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:35:1: atom : ( ^( RELOPER url= STRING value= STRING ) ({...}? -> newNode(value=$value.textoper=$RELOPER.textvar=measures.get($url.text))|{...}? -> newNode(value=$value.textoper=$RELOPER.textvar=levels.get($url.text))) | LPAREN expr RPAREN );
    public final EvalDiceCondition.atom_return atom() throws RecognitionException {
        EvalDiceCondition.atom_return retval = new EvalDiceCondition.atom_return();
        retval.start = input.LT(1);


        CommonTree url=null;
        CommonTree value=null;
        CommonTree RELOPER1=null;

        try {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:36:3: ( ^( RELOPER url= STRING value= STRING ) ({...}? -> newNode(value=$value.textoper=$RELOPER.textvar=measures.get($url.text))|{...}? -> newNode(value=$value.textoper=$RELOPER.textvar=levels.get($url.text))) | LPAREN expr RPAREN )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RELOPER) ) {
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
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:36:3: ^( RELOPER url= STRING value= STRING ) ({...}? -> newNode(value=$value.textoper=$RELOPER.textvar=measures.get($url.text))|{...}? -> newNode(value=$value.textoper=$RELOPER.textvar=levels.get($url.text)))
                    {
                    RELOPER1=(CommonTree)match(input,RELOPER,FOLLOW_RELOPER_in_atom124); 

                    match(input, Token.DOWN, null); 
                    url=(CommonTree)match(input,STRING,FOLLOW_STRING_in_atom128); 

                    value=(CommonTree)match(input,STRING,FOLLOW_STRING_in_atom132); 

                    match(input, Token.UP, null); 


                    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:37:2: ({...}? -> newNode(value=$value.textoper=$RELOPER.textvar=measures.get($url.text))|{...}? -> newNode(value=$value.textoper=$RELOPER.textvar=levels.get($url.text)))
                    int alt3=2;
                    switch ( input.LA(1) ) {
                    case AND:
                    case OR:
                        {
                        int LA3_1 = input.LA(2);

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
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

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
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

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 3, input);

                            throw nvae;

                        }
                        }
                        break;
                    case RELOPER:
                        {
                        int LA3_4 = input.LA(2);

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
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

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
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

                        if ( ((measures.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=1;
                        }
                        else if ( ((levels.containsKey((url!=null?url.getText():null)))) ) {
                            alt3=2;
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
                            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:38:2: {...}?
                            {
                            if ( !((measures.containsKey((url!=null?url.getText():null)))) ) {
                                throw new FailedPredicateException(input, "atom", "measures.containsKey($url.text)");
                            }

                            // TEMPLATE REWRITE
                            // 38:37: -> newNode(value=$value.textoper=$RELOPER.textvar=measures.get($url.text))
                            {
                                retval.st = templateLib.getInstanceOf("newNode",new STAttrMap().put("value", (value!=null?value.getText():null)).put("oper", (RELOPER1!=null?RELOPER1.getText():null)).put("var", measures.get((url!=null?url.getText():null))));
                            }


                            ((TokenRewriteStream)input.getTokenStream()).replace(
                              input.getTreeAdaptor().getTokenStartIndex(retval.start),
                              input.getTreeAdaptor().getTokenStopIndex(retval.start),
                              retval.st);


                            }
                            break;
                        case 2 :
                            // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:39:3: {...}?
                            {
                            if ( !((levels.containsKey((url!=null?url.getText():null)))) ) {
                                throw new FailedPredicateException(input, "atom", "levels.containsKey($url.text)");
                            }

                            // TEMPLATE REWRITE
                            // 39:37: -> newNode(value=$value.textoper=$RELOPER.textvar=levels.get($url.text))
                            {
                                retval.st = templateLib.getInstanceOf("newNode",new STAttrMap().put("value", (value!=null?value.getText():null)).put("oper", (RELOPER1!=null?RELOPER1.getText():null)).put("var", levels.get((url!=null?url.getText():null))));
                            }


                            ((TokenRewriteStream)input.getTokenStream()).replace(
                              input.getTreeAdaptor().getTokenStartIndex(retval.start),
                              input.getTreeAdaptor().getTokenStopIndex(retval.start),
                              retval.st);


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/EvalDiceCondition.g:41:2: LPAREN expr RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_atom187); 

                    pushFollow(FOLLOW_expr_in_atom189);
                    expr();

                    state._fsp--;


                    match(input,RPAREN,FOLLOW_RPAREN_in_atom191); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_expr_in_prog69 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_prog71 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_expr81 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_set_in_expr85 = new BitSet(new long[]{0x00000000000003B0L});
    public static final BitSet FOLLOW_unary_in_expr91 = new BitSet(new long[]{0x0000000000000112L});
    public static final BitSet FOLLOW_atom_in_unary105 = new BitSet(new long[]{0x00000000000002A2L});
    public static final BitSet FOLLOW_NOT_in_unary108 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_atom_in_unary110 = new BitSet(new long[]{0x00000000000002A2L});
    public static final BitSet FOLLOW_RELOPER_in_atom124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRING_in_atom128 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_STRING_in_atom132 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LPAREN_in_atom187 = new BitSet(new long[]{0x00000000000003B0L});
    public static final BitSet FOLLOW_expr_in_atom189 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RPAREN_in_atom191 = new BitSet(new long[]{0x0000000000000002L});

}