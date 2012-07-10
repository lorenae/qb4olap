package qbplus;

public class SliceOperator extends OLAPOperator {
	
	
	public String dimension;

	public SliceOperator(String endpoint, String schemaGraph,
			String instanceGraph, String inputCube,String outputCube,String outputStructure, int format, String dimension) {
		super(endpoint, schemaGraph, instanceGraph, inputCube, outputCube, outputStructure,format);
		this.dimension = dimension;
	}
	
	public SliceOperator(){
		super();
	}
	
	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	

}
