package qbplus.client;

import org.codehaus.jackson.map.ObjectMapper;

import qbplus.DiceOperator;
import qbplus.OLAPOperator;
import qbplus.RollUpOperator;
import qbplus.SliceOperator;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
 
public class JerseyClientPost {
 
  public static void main(String[] args) {
 
	 //postRollup();
	  postDice();
  }
  
  public static void postSlice(){
	  try {
		  
			Client client = Client.create();
	 
			WebResource webResource = client.resource("http://localhost:8888/rest/operators/slice");
			//WebResource webResource = client.resource("http://qbplus-services.appspot.com/rest/operators/slice");
			
			String endpoint= "http://localhost:8890/sparql";
			String schemaGraph= "http://example.org/householdCS#";
			String instanceGraph = "http://example.org/hholdInstanceSmall#";
			String inputCube = "http://example.org/hholdInstanceSmall#dataset_hh";
			String outputCube = "http://example.org/hholdInstanceSmall#dataset_hh1";
			String outputStructure = "http://example.org/householdCS#household_withoutGeo";
			String dimension = "http://example.org/householdCS#geoDim";	
			String operMarshalled = null;
			
			OLAPOperator oper = new SliceOperator(endpoint,schemaGraph,instanceGraph,inputCube,outputCube, outputStructure, OLAPOperator.JSON,dimension);
			
			ObjectMapper mapper = new ObjectMapper();
			operMarshalled =mapper.writeValueAsString(oper);
			 
			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, operMarshalled);
	 
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
	 
			System.out.println("Output from Server ... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
  	}
  
  public static void postRollup(){
	  try {
		  
			Client client = Client.create();
	 
			WebResource webResource = client.resource("http://localhost:8888/rest/operators/rollup");
			//WebResource webResource = client.resource("http://qbplus-services.appspot.com/rest/operators/slice");
			
			String endpoint= "http://localhost:8890/sparql";
			String schemaGraph= "http://example.org/householdCS#";
			String instanceGraph = "http://example.org/hholdInstanceSmall#";
			String inputCube = "http://example.org/hholdInstanceSmall#dataset_hh";
			String outputCube = "http://example.org/hholdInstanceSmall#dataset_hh2";
			String outputStructure = "http://example.org/householdCS#household_country";
			String dimension = "http://example.org/householdCS#geoDim";	
			String level = "http://example.org/householdCS#country";
			String operMarshalled = null;
			
			OLAPOperator oper = new RollUpOperator(endpoint,schemaGraph,instanceGraph,inputCube,outputCube, outputStructure, OLAPOperator.JSON,dimension,level);
			
			ObjectMapper mapper = new ObjectMapper();
			operMarshalled =mapper.writeValueAsString(oper);
			 
			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, operMarshalled);
	 
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
	 
			System.out.println("Output from Server ... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
  }
  
  public static void postDice(){
	  try {
		  
			Client client = Client.create();
	 
			WebResource webResource = client.resource("http://localhost:8888/rest/operators/dice");
			
			String endpoint= "http://localhost:8890/sparql";
			String schemaGraph= "http://example.org/householdCS#";
			String instanceGraph = "http://example.org/hholdInstanceSmall#";
			String inputCube = "http://example.org/hholdInstanceSmall#dataset_hh";
			String outputCube = "http://example.org/hholdInstanceSmall#dataset_hh3";
			String outputStructure = "http://example.org/householdCS#household_dice1";
			
			//String condition = "http://example.org/householdCS#household < 60 && http://example.org/householdCS#year > \"2007\"@en "; 
			
			
			//String condition = "http://example.org/householdCS#household > 60"; 
			String condition = "http://example.org/householdCS#year > \"2007\" && http://example.org/householdCS#household < 60"; 
			
			String operMarshalled = null;
			
			OLAPOperator oper = new DiceOperator(endpoint,schemaGraph,instanceGraph,inputCube,outputCube, outputStructure, OLAPOperator.JSON, condition);
			
			ObjectMapper mapper = new ObjectMapper();
			operMarshalled =mapper.writeValueAsString(oper);
			 
			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, operMarshalled);
	 
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus() +", "+ response.getEntity(String.class));
			}
	 
			System.out.println("Output from Server ... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
  	}
}