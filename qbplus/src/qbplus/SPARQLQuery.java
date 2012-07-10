package qbplus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;

public class SPARQLQuery {
	
	public String query;
	public String endpoint;
	public String defaultGraph;
	public int format;
	
	public SPARQLQuery(String query, String endpoint,int format) {
		super();
		this.query = query;
		this.endpoint = endpoint;
		this.format = format;
	}
	
	public SPARQLQuery(String query, String endpoint, String defaultGraph,
			int format) {
		super();
		this.query = query;
		this.endpoint = endpoint;
		this.defaultGraph = defaultGraph;
		this.format = format;
	}
	
	public String query(){
		return this.query;
	}
	
	public String getEndpoint(){
		return this.endpoint;
	}
	
	public String getDefaultGraph(){
		return this.defaultGraph;
	}
	
	public int getFormat(){
		return this.format;
	}
	
	public void setQuery(String query){
		this.query = query;
	}
	
	public void setEndpoint(String endpoint){
		this.endpoint = endpoint;
	}
	
	public void setDefaultGraph(String defaultGraph){
		this.defaultGraph = defaultGraph;
	}
	
	public void setFormat(int format){
		this.format = format;
	}

	//Builds an HTTP request to pose the query against a Virtuoso SPARQL endpoint.
	private URL toHTTPQuery() {
		String encQuery = null;
		String outputFormat = null;
		URL res = null;
		try{
			encQuery = URLEncoder.encode(this.query, "UTF-8");
			if (this.format == OLAPOperator.JSON) {
				outputFormat = "JSON";
			}
			
			/*
			res = new URL(this.endpoint+"?default-graph-uri="+this.defaultGraph
					+"&query="+encQuery+"&output="+outputFormat+"&timeout=0");
			*/
			res = new URL(this.endpoint+"?query="+encQuery+"&output="+outputFormat+"&timeout=0");
			
		}catch(Exception e){
			e.printStackTrace(System.err);
		}	
		return res;
	}
	
	//public BufferedReader executeQuery(){
	public String executeQuery(){
		
		HttpURLConnection connection = null;
		OutputStreamWriter wr = null;
		BufferedReader rd  = null;
		StringBuilder sb = null;
		String line = null;
		String res = null;
		URL url = this.toHTTPQuery();
		
		try{
			//Set up the initial connection
	        connection = (HttpURLConnection)url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        connection.setReadTimeout(10000);
	        connection.connect();
	      
	        //get the output stream writer and write the output to the server
	        wr = new OutputStreamWriter(connection.getOutputStream());
	        wr.write("");
	        wr.flush();
	      
	        //read the result from the server
	        rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
	        sb = new StringBuilder();
	      
	        while ((line = rd.readLine()) != null){
	            sb.append(line + '\n');
	        }
	        res = sb.toString();
	        
		} catch(Exception e){
			e.printStackTrace(System.err);
		} finally{  
	          //close the connection, set all objects to null
			connection.disconnect();
	        rd = null;
	        sb = null;
	        wr = null;
	        connection = null; 
	    }
		//return rd;
		return res;
	}
	
	@Override
	public String toString() {
		return "SPARQLQuery [endpoint=" + this.endpoint + ", defaultGraph=" 
				+ this.defaultGraph + ", query=" + this.query + ", format="+this.format + "]";
	}
}
