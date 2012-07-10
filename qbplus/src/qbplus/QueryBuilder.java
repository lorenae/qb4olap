package qbplus;

public class QueryBuilder {

	
	public String prefix = "";
	public String resultType = "";
    public String resultFormat = "";
    public String graphPattern = "";
    public String filter = "";
    public String groupBy = "";
    public Boolean hasSubquery = false;
    public String subquery = "";
    
    public static final String select = "SELECT";
    public static final String construct = "CONSTRUCT";
    


	public QueryBuilder(String resultType, Boolean hasSubquery) {
		super();
		this.resultType = resultType;
		this.hasSubquery = hasSubquery;
	}

	/**
	 * Returns a valid SPARQL query formed by the query parts
	 */
    @Override
    public String toString(){
    	
		 String query = "";
		 
		 if (this.graphPattern.equals("")){
			 this.graphPattern = this.filter;
		 }else{
			 if(!(this.filter.equals(""))){this.graphPattern = this.graphPattern + this.filter; }
		 }
		 if (!(this.prefix.equals(""))){
			 query = this.prefix;
		 }
		 if (this.resultType.equals(QueryBuilder.select)){
			 query = query+" SELECT "+this.resultFormat;
		 }
		 if (this.resultType.equals(QueryBuilder.construct)){
			 query = query+" CONSTRUCT { "+this.resultFormat+" }";
		 }
		 if (this.hasSubquery){
			 query = query+" WHERE {{ "+this.subquery+" }}";
		 }else{
			 query = query+" WHERE { "+this.graphPattern+" }";
		 }
		 if (!(this.groupBy.equals(""))){
			 query = query+" GROUP BY "+this.groupBy;
		 }	 
		return query;
	}
    
    //adds value to the prefix section of the query
    public void addPrefix(String value){
    	this.prefix = this.prefix+" PREFIX "+value; 
    }
    
    //adds a variable to the result of a query.
    //If place = 0 it adds the variable at the beginning, otherwise it adds it to the end.
  	public void addResultFormat(String variable, int place){
  		if (this.resultType.equals(QueryBuilder.select)){
  			this.resultFormat= (place == 0) ? variable+" "+this.resultFormat : this.resultFormat+" "+variable;
  		}
  		else if(this.resultType.equals(QueryBuilder.construct)){
  			if(!this.resultFormat.equals("")){
  				this.resultFormat= (place == 0) ? variable+" . "+this.resultFormat : this.resultFormat+" . "+variable;
  			}
  			else{
  				this.resultFormat = variable; 
  			}
  		}
  	 }
  	
  	//adds the graph pattern gp to the graph pattern section of the query
  	public void addGraphPattern(String gp){
  		this.graphPattern = (!this.graphPattern.equals("")) ? this.graphPattern+" . "+gp : this.graphPattern+gp;	
	}
  	
  //adds a filter condition to the query
  	public void addFilter(String condition){
  		this.filter = " FILTER( "+condition+")";
  	}

  	//adds a variable to the group by clause of the query
	public void addGroupBy(String variable){
		this.groupBy = this.groupBy+" "+variable; 
	 } 
	 
	public String getNewVariable(String seed, int counter){	
		return "?"+seed+counter;
	}
	
	public void addSubquery(String subquery){
		this.subquery = subquery;
	}


}
