package qbplus;

public class DiceOperator extends OLAPOperator {
	
	public String condition;
	
	public DiceOperator(String endpoint, String schemaGraph,
			String instanceGraph, String inputCube,String outputCube,String outputStructure, int format, String condition) {
		super(endpoint, schemaGraph, instanceGraph, inputCube, outputCube, outputStructure,format);
		this.condition=condition;
	}
	
	public DiceOperator(){
		super();
	}
	
	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
