package qbplus;

public class OLAPOperator {
	
	public static final int JSON = 20;
	
	public String endpoint;
	public String schemaGraph;
	public String instanceGraph;
	public String inputCube;
	public String outputCube;
	public String outputStructure;
	public int format;
	
	
	public OLAPOperator(){
		
	}
	
	public OLAPOperator(String endpoint, String schemaGraph,
			String instanceGraph, String inputCube, String outputCube, String outputStructure, int format) {
		super();
		this.endpoint = endpoint;
		this.schemaGraph = schemaGraph;
		this.instanceGraph = instanceGraph;
		this.inputCube = inputCube;
		this.outputCube = outputCube;
		this.outputStructure = outputStructure;
		this.format = format;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getSchemaGraph() {
		return schemaGraph;
	}
	public void setSchemaGraph(String schemaGraph) {
		this.schemaGraph = schemaGraph;
	}
	public String getInstanceGraph() {
		return instanceGraph;
	}
	public void setInstanceGraph(String instanceGraph) {
		this.instanceGraph = instanceGraph;
	}
	
	public int getFormat() {
		return format;
	}
	public void setFormat(int format) {
		this.format = format;
	}
	
	public String getInputCube() {
		return inputCube;
	}

	public void setInputCube(String inputCube) {
		this.inputCube = inputCube;
	}
	
	public String getOutputCube() {
		return outputCube;
	}

	public void setOutputCube(String outputCube) {
		this.outputCube = outputCube;
	}

	public String getOutputStructure() {
		return outputStructure;
	}

	public void setOutputStructure(String outputStructure) {
		this.outputStructure = outputStructure;
	}

	@Override
	public String toString() {
		return "OLAPOperator [endpoint=" + endpoint + ", schemaGraph="
				+ schemaGraph + ", instanceGraph=" + instanceGraph + ", format="+ format +", inputCube="+ inputCube +", outputCube="+ outputCube+", outputStructure="+ outputStructure+ "]";
	}
	

}
