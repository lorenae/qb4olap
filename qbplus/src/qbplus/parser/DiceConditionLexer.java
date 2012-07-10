// $ANTLR 3.4 /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g 2012-07-09 23:43:37
package qbplus.parser;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class DiceConditionLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public DiceConditionLexer() {} 
    public DiceConditionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DiceConditionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g"; }

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:113:5: ( '&&' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:113:7: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:114:4: ( '||' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:114:6: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:115:8: ( '(' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:115:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:116:8: ( ')' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:116:11: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "RELOPER"
    public final void mRELOPER() throws RecognitionException {
        try {
            int _type = RELOPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:8: ( ( '>' | '>=' | '<' | '<=' | '=' | '!=' ) )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:9: ( '>' | '>=' | '<' | '<=' | '=' | '!=' )
            {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:9: ( '>' | '>=' | '<' | '<=' | '=' | '!=' )
            int alt1=6;
            switch ( input.LA(1) ) {
            case '>':
                {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='=') ) {
                    alt1=2;
                }
                else {
                    alt1=1;
                }
                }
                break;
            case '<':
                {
                int LA1_2 = input.LA(2);

                if ( (LA1_2=='=') ) {
                    alt1=4;
                }
                else {
                    alt1=3;
                }
                }
                break;
            case '=':
                {
                alt1=5;
                }
                break;
            case '!':
                {
                alt1=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }

            switch (alt1) {
                case 1 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:10: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 2 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:14: '>='
                    {
                    match(">="); 



                    }
                    break;
                case 3 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:19: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 4 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:23: '<='
                    {
                    match("<="); 



                    }
                    break;
                case 5 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:28: '='
                    {
                    match('='); 

                    }
                    break;
                case 6 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:117:32: '!='
                    {
                    match("!="); 



                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RELOPER"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:118:5: ( '!' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:118:8: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:119:8: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ':' | '/' | '.' | '-' | '#' | '\"' | '@' )* )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:119:10: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ':' | '/' | '.' | '-' | '#' | '\"' | '@' )*
            {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:119:10: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | ':' | '/' | '.' | '-' | '#' | '\"' | '@' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '\"' && LA2_0 <= '#')||(LA2_0 >= '-' && LA2_0 <= ':')||(LA2_0 >= '@' && LA2_0 <= 'Z')||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:
            	    {
            	    if ( (input.LA(1) >= '\"' && input.LA(1) <= '#')||(input.LA(1) >= '-' && input.LA(1) <= ':')||(input.LA(1) >= '@' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:121:8: ( ( '\\r' )? '\\n' )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:121:9: ( '\\r' )? '\\n'
            {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:121:9: ( '\\r' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\r') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:121:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:122:4: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:122:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:122:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:8: ( AND | OR | LPAREN | RPAREN | RELOPER | NOT | STRING | NEWLINE | WS )
        int alt5=9;
        switch ( input.LA(1) ) {
        case '&':
            {
            alt5=1;
            }
            break;
        case '|':
            {
            alt5=2;
            }
            break;
        case '(':
            {
            alt5=3;
            }
            break;
        case ')':
            {
            alt5=4;
            }
            break;
        case '<':
        case '=':
        case '>':
            {
            alt5=5;
            }
            break;
        case '!':
            {
            int LA5_6 = input.LA(2);

            if ( (LA5_6=='=') ) {
                alt5=5;
            }
            else {
                alt5=6;
            }
            }
            break;
        case '\r':
            {
            int LA5_8 = input.LA(2);

            if ( (LA5_8=='\n') ) {
                int LA5_9 = input.LA(3);

                if ( ((LA5_9 >= '\t' && LA5_9 <= '\n')||LA5_9=='\r'||LA5_9==' ') ) {
                    alt5=9;
                }
                else {
                    alt5=8;
                }
            }
            else {
                alt5=9;
            }
            }
            break;
        case '\n':
            {
            int LA5_9 = input.LA(2);

            if ( ((LA5_9 >= '\t' && LA5_9 <= '\n')||LA5_9=='\r'||LA5_9==' ') ) {
                alt5=9;
            }
            else {
                alt5=8;
            }
            }
            break;
        case '\t':
        case ' ':
            {
            alt5=9;
            }
            break;
        default:
            alt5=7;
        }

        switch (alt5) {
            case 1 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:10: AND
                {
                mAND(); 


                }
                break;
            case 2 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:14: OR
                {
                mOR(); 


                }
                break;
            case 3 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:17: LPAREN
                {
                mLPAREN(); 


                }
                break;
            case 4 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:24: RPAREN
                {
                mRPAREN(); 


                }
                break;
            case 5 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:31: RELOPER
                {
                mRELOPER(); 


                }
                break;
            case 6 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:39: NOT
                {
                mNOT(); 


                }
                break;
            case 7 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:43: STRING
                {
                mSTRING(); 


                }
                break;
            case 8 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:50: NEWLINE
                {
                mNEWLINE(); 


                }
                break;
            case 9 :
                // /home/lorena/workspace/qbplus/src/qbplus/parser/DiceCondition.g:1:58: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}