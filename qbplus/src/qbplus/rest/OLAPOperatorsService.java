package qbplus.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

import qbplus.DiceOperator;
import qbplus.RollUpOperator;
import qbplus.SPARQLQuery;
import qbplus.QueryBuilder;
import qbplus.SliceOperator;
import qbplus.OLAPOperator;
import qbplus.parser.DiceConditionLexer;
import qbplus.parser.DiceConditionParser;
import qbplus.parser.EvalDiceCondition;

@Path("/operators")
public class OLAPOperatorsService {

	
	@POST
	@Path("/slice")
	public Response executeSlice(SliceOperator oper){
		
		SPARQLQuery schemaQuery = null;
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();
		
      try { 
    	
    	  String prefix =   "prefix qb:<http://purl.org/linked-data/cube#> "+
    			  			"prefix qbp:<http://purl.org/olap#> ";
    	  
    	  //retrieve schema metadata
    	  String query1 = 	"select * from <"+ oper.instanceGraph+"> from <"+oper.schemaGraph+"> "+
    			  			"where {<"+oper.inputCube+"> qb:structure ?struct . "+
    			  			"?struct qb:component ?comp . "+
    			  			"{?comp qb:measure ?measure . "+
    			  			"?comp qbp:hasAggregateFunction ?func} UNION "+
    			  			"{?comp qbp:level ?level ."+
    			  			"?level qbp:inDimension ?dim}}";
    	  
    	  schemaQuery = new SPARQLQuery(prefix+query1, oper.endpoint, oper.schemaGraph, OLAPOperator.JSON);  
    	  
    	  //process schema metadata
    	  JsonNode rootNode = mapper.readValue(schemaQuery.executeQuery(), JsonNode.class);
     	  HashMap<String,String> dimLevel = new HashMap<String,String>();
    	  HashMap<String,String> measures = new HashMap<String,String>();
    	  String structure = null;
    	  for(Iterator<JsonNode> nodes = rootNode.path("results").path("bindings").getElements(); 
    			  nodes.hasNext(); ) {
    		  JsonNode node = nodes.next();
    		  String dim = node.path("dim").path("value").getTextValue();
    		  String level = node.path("level").path("value").getTextValue();
    		  String measure = node.path("measure").path("value").getTextValue();
    		  String func = node.path("func").path("value").getTextValue();
    		  if (structure == null){structure = node.path("struct").path("value").getTextValue();}
    		  if (dim != null){ dimLevel.put(dim, level);}
    		  if (measure != null){measures.put(measure, func);}
    		}
    	  		 
		//the seeds and counters for variables name generation
		String levelSeed = "?level_";
		String measureSeed = "?measure_";
		String agSeed = "?aggMeasure_";
		int levelCounter = 1;
		int measureCounter = 1;
		//an array to store level values to build the id of slice facts
		ArrayList<String> levelsForId = new ArrayList<String>();
		
		//queries initialisation
		//qInner and qOuter build the instances graph, qSchema builds the 
		//schema graph
		QueryBuilder qInner = new QueryBuilder(QueryBuilder.select, false);
		QueryBuilder qOuter = new QueryBuilder(QueryBuilder.construct, true);
		QueryBuilder qSchema = new QueryBuilder(QueryBuilder.construct, false);
		
		qSchema.addResultFormat("<"+structure+"> a qb:DataStructureDefinition",1);
		qOuter.addPrefix("qb:<http://purl.org/linked-data/cube#>");
		qOuter.addPrefix("qbp:<http://purl.org/olap#>");
		qOuter.addPrefix("fn:<http://www.w3.org/2005/xpath-functions#>");
		qOuter.addResultFormat("?oi qb:dataSet <"+oper.outputCube+">", 0);
		qOuter.addResultFormat("<"+oper.outputCube+"> qb:structure <"+oper.outputStructure+">", 0);
		qInner.addGraphPattern("?d qb:structure <"+structure+">");
		qInner.addGraphPattern("?o qb:dataSet ?d");

		for (Iterator<String> dims = dimLevel.keySet().iterator(); dims.hasNext();){
			String currentDim = dims.next(); 
			if ( !currentDim.equals(oper.dimension)){
				String li = levelSeed+levelCounter;
				levelCounter++;
				String currentLevel = dimLevel.get(currentDim);
				qOuter.addResultFormat("?oi <"+currentLevel+"> "+li, 1);
				// results structure
				qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qbp:level <"+currentLevel+"> ]", 0);
				// results instances
				qInner.addResultFormat(li, 0);
				qInner.addGraphPattern("?o <"+currentLevel+"> "+li);
				qInner.addGroupBy(li);
				levelsForId.add(li);		
			}
		}
		for (Iterator<String> mes = measures.keySet().iterator(); mes.hasNext();){
			String currentMeasure = mes.next();
			String currentAggFunc = measures.get(currentMeasure);
			String mi = measureSeed+measureCounter;
			String agi= agSeed+measureCounter;
			measureCounter++;
			// results structure
			qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qb:measure <"+currentMeasure+"> ; qbp:hasAggregateFunction  <"+currentAggFunc+"> ]", 0);
			// results instances
			qOuter.addResultFormat("?oi <"+currentMeasure+"> "+agi, 1);
			qInner.addResultFormat("("+this.getAggregateFunction(currentAggFunc)+"("+mi+") as "+agi+")", 1);
			qInner.addGraphPattern("?o <"+currentMeasure+"> "+mi);	
		}
		//build the expression to populate the ?oi variable
		// the outputcube + _ + each level member in the levelsForId	
		String idStr =  getIdExpression(oper, levelsForId);
				
		//include the id generation in the inner query
		qInner.addResultFormat(idStr, 0);
		qOuter.addSubquery(qInner.toString());
		
		SPARQLQuery instancesQuery = new SPARQLQuery(qOuter.toString(), oper.endpoint, OLAPOperator.JSON);  
		SPARQLQuery newSchQuery = new SPARQLQuery(qSchema.toString(), oper.endpoint, OLAPOperator.JSON);
		
		String schemaGraph = newSchQuery.executeQuery();
		String instancesGraph = instancesQuery.executeQuery();
  	  	//I manually merge the two JSON trees
		String merged = this.mergeJson(schemaGraph, instancesGraph);
  	  	
  	  	
		response = Response.status(201).entity(merged).build();
    	            
      } catch (Exception e) {
          e.printStackTrace(System.err);  
      } 
		return response;
	}

	
	@POST
	@Path("/dice")
	public Response executeDice(DiceOperator oper){
		
		SPARQLQuery schemaQuery = null;
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();
		
      try { 
    	
    	  String prefix =   "prefix qb:<http://purl.org/linked-data/cube#> "+
    			  			"prefix qbp:<http://purl.org/olap#> ";
    	  
    	  //retrieve schema metadata
    	  String query1 = 	"select * from <"+ oper.instanceGraph+"> from <"+oper.schemaGraph+"> "+
    			  			"where {<"+oper.inputCube+"> qb:structure ?struct . "+
    			  			"?struct qb:component ?comp . "+
    			  			"{?comp qb:measure ?measure . "+
    			  			"?comp qbp:hasAggregateFunction ?func} UNION "+
    			  			"{?comp qbp:level ?level ."+
    			  			"?level qbp:inDimension ?dim}}";
    	  
    	  schemaQuery = new SPARQLQuery(prefix+query1, oper.endpoint, oper.schemaGraph, OLAPOperator.JSON);  
    	  
    	  //process schema metadata
    	  JsonNode rootNode = mapper.readValue(schemaQuery.executeQuery(), JsonNode.class);
     	  HashMap<String,String> dimLevel = new HashMap<String,String>();
    	  HashMap<String,String> measures = new HashMap<String,String>();
    	  String structure = null;
    	  for(Iterator<JsonNode> nodes = rootNode.path("results").path("bindings").getElements(); 
    			  nodes.hasNext(); ) {
    		  JsonNode node = nodes.next();
    		  String dim = node.path("dim").path("value").getTextValue();
    		  String level = node.path("level").path("value").getTextValue();
    		  String measure = node.path("measure").path("value").getTextValue();
    		  String func = node.path("func").path("value").getTextValue();
    		  if (structure == null){structure = node.path("struct").path("value").getTextValue();}
    		  if (dim != null){ dimLevel.put(dim, level);}
    		  if (measure != null){measures.put(measure, func);}
    		}
    	  		
    	    //TODO control that the schema info has been retrieved.
    	  	if (!dimLevel.isEmpty() && !measures.isEmpty()){
      	  
		    	//Before going on the dice condition has to be parsed and transformed. 
		    	//The string contains the filter condition transformed
		    	//The hashmaps contain founded levels and measures and their assigned variables
		    	  
		    	Pair<String, Pair<HashMap<String,String>, HashMap<String,String>>> parseResults = this.parseDiceCondition(oper.condition, dimLevel, measures);
				  
		    	String condition = parseResults.first;
		    	HashMap<String,String> diceLevels = parseResults.second.first;
		    	HashMap<String,String> diceMeasures = parseResults.second.second;
		    	
		    	//for each level found in the dice condition we need to add a bgp in the where clause
		    	//to state that diceLevels.get(level) (which is the name of the variable) is the rdfs:label of the level
		    	//also, we have to check if a variable exists for each level and measure and use these instead of creating new ones
	    	  
			    //the seeds and counters for variables name generation
				String levelSeed = "?l_";
				String measureSeed = "?m_";
				int levelCounter = 1;
				int measureCounter = 1;
				
				
				//queries initialisation
				//qOuter build the instances graph, qSchema builds the 
				//schema graph
				QueryBuilder qOuter = new QueryBuilder(QueryBuilder.construct, false);
				QueryBuilder qSchema = new QueryBuilder(QueryBuilder.construct, false);
				
				qSchema.addResultFormat("<"+structure+"> a qb:DataStructureDefinition",1);
				qOuter.addPrefix("qb:<http://purl.org/linked-data/cube#>");
				qOuter.addPrefix("qbp:<http://purl.org/olap#>");
				qOuter.addPrefix("fn:<http://www.w3.org/2005/xpath-functions#>");
				qOuter.addPrefix("rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
				qOuter.addResultFormat("?o qb:dataSet <"+oper.outputCube+">", 0);
				qOuter.addResultFormat("<"+oper.outputCube+"> qb:structure <"+oper.outputStructure+">", 0);
				qOuter.addGraphPattern("?d qb:structure <"+structure+">");
				qOuter.addGraphPattern("?o qb:dataSet ?d");
	
				for (Iterator<String> dims = dimLevel.keySet().iterator(); dims.hasNext();){
					String currentDim = dims.next(); 
					String currentLevel = dimLevel.get(currentDim);
					String li = levelSeed+levelCounter;
					levelCounter++;
					qOuter.addResultFormat("?o <"+currentLevel+"> "+li, 1);
					// results structure
					qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qbp:level <"+currentLevel+"> ]", 0);
					// results instances
					qOuter.addGraphPattern("?o <"+currentLevel+"> "+li);
					if(diceLevels.containsKey(currentLevel)){
						String bgp = li+" rdfs:label "+diceLevels.get(currentLevel);
						qOuter.addGraphPattern(bgp);
					}
				}
	
				for (Iterator<String> mes = measures.keySet().iterator(); mes.hasNext();){
					String currentMeasure = mes.next();
					String currentAggFunc = measures.get(currentMeasure);
					String mi = null;
					if(diceMeasures.containsKey(currentMeasure)){
						mi = diceMeasures.get(currentMeasure);
					}else{
						mi = measureSeed+measureCounter;
						measureCounter++;
					}
	
					// results structure
					qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qb:measure <"+currentMeasure+"> ; qbp:hasAggregateFunction  <"+currentAggFunc+"> ]", 0);
					// results instances
					qOuter.addResultFormat("?o <"+currentMeasure+"> "+mi, 1);
					qOuter.addGraphPattern("?o <"+currentMeasure+"> "+mi);	
				}
				
				//if there is a valid DICE condition to apply
				if (condition != null){
					qOuter.addFilter(condition);
				}
				
				
				SPARQLQuery instancesQuery = new SPARQLQuery(qOuter.toString(), oper.endpoint, OLAPOperator.JSON);  
				SPARQLQuery newSchQuery = new SPARQLQuery(qSchema.toString(), oper.endpoint, OLAPOperator.JSON);
				
				String schemaGraph = newSchQuery.executeQuery();
				String instancesGraph = instancesQuery.executeQuery();
				
				System.out.println(instancesQuery.toString());
		  	  	//Manually merge the two JSON trees
				String merged = this.mergeJson(schemaGraph, instancesGraph);
		  	  	
				response = Response.status(201).entity(merged).build();
    	  }else{
    	  		response = Response.status(400).entity("Could not get info on cube schema").build();
    	  }

    	  }catch (RuntimeException e){
    		  //e.printStackTrace();
    		  response = Response.status(400).entity("Parse error in condition "+ e.getMessage()).build();
    	  }
    	  catch (Exception e) {
    		  //e.printStackTrace();
    		  response = Response.status(500).entity(e.getMessage()).build();  
          } 
		return response;
	}

	
	@POST
	@Path("/rollup")
	public Response executeRollup(RollUpOperator oper){
		
		SPARQLQuery schemaQuery = null;
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();
		
      try { 
    	
    	  String prefix =   "prefix qb:<http://purl.org/linked-data/cube#> "+
    			  			"prefix qbp:<http://purl.org/olap#> ";
    	  
    	  //retrieve schema metadata
    	  String query1 = 	"select ?struct ?measure ?func ?dim ?level1 ?level2 ?dimbase ?levelbase "+
    			  			"from <"+ oper.instanceGraph+"> from <"+oper.schemaGraph+"> "+
    			  			"where {<"+oper.inputCube+"> qb:structure ?struct . "+
    			  			"?struct qb:component ?comp . "+
    			  			"{?comp qb:measure ?measure . "+
    			  			"?comp qbp:hasAggregateFunction ?func} UNION "+
    			  			"{?comp qbp:level ?level ."+
    			  			"?level qbp:inDimension ?dim ."+
    			  			"?level1 qbp:inDimension ?dim ."+
    			  			"?level2 qbp:inDimension ?dim ."+
    			  			"?level1 qbp:parentLevel ?level2 } UNION"+
    			  			"{?comp qbp:level ?levelbase ."+
    			  			"?levelbase qbp:inDimension ?dimbase}}";
    	  schemaQuery = new SPARQLQuery(prefix+query1, oper.endpoint, oper.schemaGraph, OLAPOperator.JSON);  
    	  
    	  //process schema metadata
    	  JsonNode rootNode = mapper.readValue(schemaQuery.executeQuery(), JsonNode.class);
    	  //for each dimension an array with all the levels, from most specific to most generic
     	  HashMap<String, ArrayList<String>> dimensions = new HashMap<String,ArrayList<String>>();
    	  HashMap<String,String> measures = new HashMap<String,String>();
    	  String structure = null;
    	  for(Iterator<JsonNode> nodes = rootNode.path("results").path("bindings").getElements(); 
    			  nodes.hasNext(); ) {
    		  JsonNode node = nodes.next();
    		  String dim = node.path("dim").path("value").getTextValue();
    		  String level1 = node.path("level1").path("value").getTextValue();
    		  String level2 = node.path("level2").path("value").getTextValue();
    		  String measure = node.path("measure").path("value").getTextValue();
    		  String func = node.path("func").path("value").getTextValue();
    		  String dimbase = node.path("dimbase").path("value").getTextValue();
    		  String levelbase = node.path("levelbase").path("value").getTextValue();
    		  if (structure == null){structure = node.path("struct").path("value").getTextValue();}
    		  if (measure != null){measures.put(measure, func);}
    		  if (dim != null && level1 != null && level2 != null){
    			  ArrayList<String> levels = new ArrayList<String>();
    			  if(!dimensions.containsKey(dim)){
    				  levels.add(level1);
    				  levels.add(level2);
    			  }else{
    				  levels = dimensions.get(dim);
    				  if (!levels.contains(level1)) {levels.add(level1);}
    				  if (!levels.contains(level2)) {levels.add(level2);}
    			  }
    			  dimensions.put(dim, levels);  
    		  }
    		  if (dimbase != null && levelbase != null){
    			  ArrayList<String> levels = new ArrayList<String>();
    			  if(!dimensions.containsKey(dimbase)){
    				  levels.add(levelbase);
    				  dimensions.put(dimbase, levels);
    			  }
    		  }
    		}
    	  		 
		//the seeds and counters for variables name generation
		String levelSeed = "?level_";
		String parentLevelMemSeed = "?plm_";
		String measureSeed = "?measure_";
		String agSeed = "?aggMeasure_";
		int levelCounter = 1;
		int measureCounter = 1;
		int parentLevelMemCounter = 1;
		//an array to store level values to build the id of slice facts
		ArrayList<String> levelsForId = new ArrayList<String>();
		
		//queries initialisation
		//qInner and qOuter build the instances graph, qSchema builds the 
		//schema graph
		QueryBuilder qInner = new QueryBuilder(QueryBuilder.select, false);
		QueryBuilder qOuter = new QueryBuilder(QueryBuilder.construct, true);
		QueryBuilder qSchema = new QueryBuilder(QueryBuilder.construct, false);
		
		qSchema.addResultFormat("<"+structure+"> a qb:DataStructureDefinition",1);
		qOuter.addPrefix("qb:<http://purl.org/linked-data/cube#>");
		qOuter.addPrefix("qbp:<http://purl.org/olap#>");
		qOuter.addPrefix("skos:<http://www.w3.org/2004/02/skos/core#>");
		qOuter.addPrefix("fn:<http://www.w3.org/2005/xpath-functions#>");
		qOuter.addResultFormat("?oi qb:dataSet <"+oper.outputCube+">", 0);
		qOuter.addResultFormat("<"+oper.outputCube+"> qb:structure <"+oper.outputStructure+">", 0);
		qInner.addGraphPattern("?d qb:structure <"+structure+">");
		qInner.addGraphPattern("?o qb:dataSet ?d");

		//add the base levels for each dimensions, except the rollup dimension
		for (Iterator<String> dims = dimensions.keySet().iterator(); dims.hasNext();){
			String currentDim = dims.next(); 
			if ( !currentDim.equals(oper.dimension)){
				String li = levelSeed+levelCounter;
				levelCounter++;
				String currentLevel = dimensions.get(currentDim).get(0);
				qOuter.addResultFormat("?oi <"+currentLevel+"> "+li, 1);
				// results structure
				qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qbp:level <"+currentLevel+"> ]", 0);
				// results instances
				qInner.addResultFormat(li, 0);
				qInner.addGraphPattern("?o <"+currentLevel+"> "+li);
				qInner.addGroupBy(li);
				levelsForId.add(li);		
			}
		}
		//apply aggregate functions to each measure in the cube
		for (Iterator<String> mes = measures.keySet().iterator(); mes.hasNext();){
			String currentMeasure = mes.next();
			String currentAggFunc = measures.get(currentMeasure);
			String mi = measureSeed+Integer.toString(measureCounter);
			String agi= agSeed+Integer.toString(measureCounter);
			measureCounter++;
			// results structure
			qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qb:measure <"+currentMeasure+"> ; qbp:hasAggregateFunction  <"+currentAggFunc+"> ]", 0);
			// results instances
			qOuter.addResultFormat("?oi <"+currentMeasure+"> "+agi, 1);
			qInner.addResultFormat("("+this.getAggregateFunction(currentAggFunc)+"("+mi+") as "+agi+")", 1);
			qInner.addGraphPattern("?o <"+currentMeasure+"> "+mi);	
		}
		
		//traverse the hierarchy in the rollup dimension, from base level up to target level.
		//add target level to the results, the structure and the group by clause.
		
		ArrayList<String> levelsPath = dimensions.get(oper.dimension);
		String plmi = null;
		if (levelsPath != null){
			Iterator<String> levels = levelsPath.iterator();
			boolean target = false;
			while (levels.hasNext() && !target){
				String lmi = levelSeed+levelCounter;
				String cLevel = levels.next();
				target = cLevel.equals(oper.level);
				//add a triple for all the levels, except the base level
				if(! cLevel.equals(levelsPath.get(0))){
					qInner.addGraphPattern(plmi+" qbp:inLevel <"+cLevel+"> ");
				}
				if(! cLevel.equals(oper.level)){
					String newPlmi = parentLevelMemSeed+parentLevelMemCounter;
					parentLevelMemCounter++;
					if(plmi != null){
						qInner.addGraphPattern(plmi+" skos:broader "+newPlmi);
						plmi = newPlmi;
					}else{
						plmi = newPlmi;
						qInner.addGraphPattern(lmi+" skos:broader "+plmi);	
					}
				}
				//only for the base level
				if(cLevel.equals(levelsPath.get(0))){
					qInner.addGraphPattern("?o <"+cLevel+"> "+lmi);
				}	
			}
			levelsForId.add(plmi);
		}
		
		//build the expression to populate the ?oi variable
		// the outputcube + _ + each level member in the levelsForId	
		String idStr =  getIdExpression(oper, levelsForId);
				
		//include the id generation in the inner query
		qInner.addResultFormat(idStr, 0);
		qSchema.addResultFormat("<"+oper.outputStructure+"> qb:component [qbp:level <"+oper.level+"> ]", 0);
		qInner.addGroupBy(plmi);
		qInner.addResultFormat(plmi, 1);
		qOuter.addResultFormat("?oi <"+oper.level+"> "+plmi, 1);
		qOuter.addSubquery(qInner.toString());
		
		
		SPARQLQuery instancesQuery = new SPARQLQuery(qOuter.toString(), oper.endpoint, OLAPOperator.JSON);  
		SPARQLQuery newSchQuery = new SPARQLQuery(qSchema.toString(), oper.endpoint, OLAPOperator.JSON);
		
		System.out.println(qOuter.toString());
		System.out.println(qSchema.toString());
		
		String schemaGraph = newSchQuery.executeQuery();
		String instancesGraph = instancesQuery.executeQuery();
  	  	//I manually merge the two JSON trees
		String merged = this.mergeJson(schemaGraph, instancesGraph);
  	  	
  	  	
		response = Response.status(201).entity(merged).build();
    	            
      } catch (Exception e) {
          e.printStackTrace(System.err);  
      } 
		return response;
	}
	
	private String getAggregateFunction(String aggFunction){
		if (aggFunction.equals("http://purl.org/olap#sum")){return "SUM";}
		else if (aggFunction.equals("http://purl.org/olap#avg")){return "AVG";}
		else if (aggFunction.equals("http://purl.org/olap#count")){return "COUNT";}
		else if (aggFunction.equals("http://purl.org/olap#min")){return "MIN";}
		else if (aggFunction.equals("http://purl.org/olap#max")){return "MAX";}
		else return null;
		}

	private String getIdExpression(OLAPOperator oper, ArrayList<String> levelsForId){
		
		String res = null;
		res = "(iri(fn:concat(\""+ oper.outputCube+"_\", bif:md5(fn:concat(";
		
		for(Iterator<String> levels= levelsForId.iterator();levels.hasNext();){	
			String level = levels.next();
			res = (levels.hasNext())? res+level+",":res+level ;
		}
		
		res = res+")))) AS ?oi )";
		return res;
	}
	
	private String mergeJson(String tree1, String tree2){
		
		String result = tree2;
		
		try{
			ObjectMapper t1mapper = new ObjectMapper();
			ObjectMapper t2mapper = new ObjectMapper();
			JsonNode t1Node = t1mapper.readValue(tree1, JsonNode.class);
			JsonNode t2Node = t2mapper.readValue(tree2, JsonNode.class);
			ArrayNode bindT2 = (ArrayNode) t2Node.path("results").path("bindings");
			
	    	for(Iterator<JsonNode> nodes = t1Node.path("results").path("bindings").getElements(); 
	    			  nodes.hasNext(); ) {
	    		  JsonNode node = nodes.next();
	    		  bindT2.add(node); 
	    	}
	    	result = t2Node.toString();
			
		}
		catch(Exception e){
			e.printStackTrace(System.err);
		}

		return result;
		
	}
	
	private Pair<String, Pair<HashMap<String,String>, HashMap<String,String>>> parseDiceCondition(String diceCondition, HashMap<String,String> levels, HashMap<String,String> measures) throws RuntimeException{
		
		String res = null;
		HashMap<String,String> foundLevels = null;
		HashMap<String,String> foundMeasures = null;
		
		String temp = "group DiceCondition; newNode(value,oper, var) ::= \" <var> <oper> <value> \" ";
		StringTemplateGroup templates = new StringTemplateGroup(new StringReader(temp));
        
    	try{
            // PARSE INPUT AND BUILD AST 
            ANTLRStringStream input = new ANTLRStringStream(diceCondition);
            DiceConditionLexer lex = new DiceConditionLexer(input);
            TokenRewriteStream tokens = new TokenRewriteStream(lex); 
            DiceConditionParser parser = new DiceConditionParser(tokens);
            DiceConditionParser.prog_return r = parser.prog(levels, measures);
            
            foundLevels = r.fLevels;
            foundMeasures = r.fMeasures;
            	
        	// WALK RESULTING TREE 
        	CommonTree t = (CommonTree)r.getTree(); // get tree from parser 
        	CommonTreeNodeStream nodes = new CommonTreeNodeStream(t); 
        	nodes.setTokenStream(tokens);
        	EvalDiceCondition walker = new EvalDiceCondition(nodes); // create a tree parse
        	walker.setTemplateLib(templates);
        	EvalDiceCondition.prog_return r2 = walker.prog(foundLevels, foundMeasures);
        	res = tokens.toString();
        	
        	} catch (Exception e){
        		e.printStackTrace();
        		throw new RuntimeException(e.getMessage());
        	}
    	
    	Pair<HashMap<String,String>, HashMap<String,String>> maps = new Pair<HashMap<String,String>, HashMap<String,String>>(foundLevels, foundMeasures);
		
		return new Pair<String, Pair<HashMap<String,String>, HashMap<String,String>>>(res, maps);
	}
	
}
