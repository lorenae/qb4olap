package qbplus.test;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;



import qbplus.parser.DiceConditionLexer;
import qbplus.parser.DiceConditionParser;
import qbplus.parser.EvalDiceCondition;


public class __Test__ {

    public static void main(String args[]) throws Exception {
    	
    	//FileReader groupFileR = new FileReader("/home/lorena/workspace/qbplus/src/qbplus/test/DiceCondition.stg"); 
    	//StringTemplateGroup templates = new StringTemplateGroup();
    	
    	String temp = "group DiceCondition; newNode(value,oper, var) ::= \" <var> <oper> <value> \" ";
    	StringTemplateGroup templates = new StringTemplateGroup(new StringReader(temp));
        
        HashMap<String,String> levels = new HashMap<String,String>();
        HashMap<String,String> measures = new HashMap<String,String>();
    	
    	levels.put("http://example.org/householdCS#year","http://example.org/householdCS#year");
    	measures.put("http://example.org/householdCS#household","http://example.org/householdCS#household");
    	
    	try{
        // PARSE INPUT AND BUILD AST 
        ANTLRFileStream input = new ANTLRFileStream("/home/lorena/workspace/qbplus/src/qbplus/test/Test_input.txt", "UTF8"); 
        ANTLRStringStream inputStr = new ANTLRStringStream("http://example.org/householdCS#year = 2007@en" );
        
        
        DiceConditionLexer lex = new DiceConditionLexer(inputStr);
        
        //CommonTokenStream tokens = new CommonTokenStream(lex);
        TokenRewriteStream tokens = new TokenRewriteStream(lex); 
        DiceConditionParser parser = new DiceConditionParser(tokens);
        // load the group file ByteCode.stg, put in templates var 
        DiceConditionParser.prog_return r = parser.prog(levels, measures);
        
        System.out.println("NIVELES : "+ r.fLevels.toString());
        System.out.println("MEDIDAS : "+ r.fMeasures.toString());
        	
     
        
        
    	// WALK RESULTING TREE 
    	CommonTree t = (CommonTree)r.getTree(); // get tree from parser 
    	System.out.println(t.toStringTree());
    	
    	// Create a tree node stream from resulting tree 
    	CommonTreeNodeStream nodes = new CommonTreeNodeStream(t); 
    	// tell it where it can find the token objects 
    	nodes.setTokenStream(tokens);
    	EvalDiceCondition walker = new EvalDiceCondition(nodes); // create a tree parser
    	// where to find templates 
    	walker.setTemplateLib(templates);
    	
    	// invoke rule prog, passing in information from parser 
    	EvalDiceCondition.prog_return r2 = walker.prog(r.fLevels, r.fMeasures);
    	
    	// EMIT BYTE CODES 
    	// get template from return values struct 
    	StringTemplate output = (StringTemplate) r2.getTemplate(); 
    	
    	if (output != null){
    		System.out.println(output.toString()); // render full template 
    	}
    	
    	System.out.println(tokens.toString());
    	
    	} catch (Exception e){
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    	}
    	
    	
        	
        	
        	
        
    }
}