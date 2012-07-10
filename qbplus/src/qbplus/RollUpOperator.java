package qbplus;

public class RollUpOperator extends OLAPOperator {
	
	public String dimension;
	public String level;
	
	public RollUpOperator(String endpoint, String schemaGraph,
			String instanceGraph, String inputCube,String outputCube,String outputStructure, int format, String dimension, String level) {
		super(endpoint, schemaGraph, instanceGraph, inputCube, outputCube, outputStructure,format);
		this.dimension = dimension;
		this.level = level;
	}
	
	public RollUpOperator(){
		super();
	}
	
	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	


}
