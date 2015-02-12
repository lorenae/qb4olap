package Clases;

//Esta clase representa la medida de un cubo, se almacena su nombre, y la 
//columna en la fact table
public class medida {
    private String nombre;
    private String columna_Fact_Table;
    private String data_Type;
    private String sql_Medida_Calculada;

    ///////////////////////////////////////////////////////////////////////////
    // Constructores
    public medida(){
        this.nombre = "";
        this.columna_Fact_Table = "";
        this.data_Type = "";
        this.sql_Medida_Calculada = "";
    }
    public medida(String name){
        nombre = name;
        this.columna_Fact_Table = "";
        this.data_Type = "";
        this.sql_Medida_Calculada = "";
    }

    public medida(String nombre, String columna_Fact_Table) {
        this.nombre = nombre;
        this.columna_Fact_Table = columna_Fact_Table;
        this.data_Type = "";
        this.sql_Medida_Calculada = "";

    }

    public medida(String nombre, String columna_Fact_Table, String data_Type) {
        this.nombre = nombre;
        this.columna_Fact_Table = columna_Fact_Table;
        this.data_Type = data_Type;
        this.sql_Medida_Calculada = "";
    }

    public medida(String nombre, String columna_Fact_Table, String data_Type, String sql_Medida_Calculada) {
        this.nombre = nombre;
        this.columna_Fact_Table = columna_Fact_Table;
        this.data_Type = data_Type;
        this.sql_Medida_Calculada = sql_Medida_Calculada;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Geters

    public String getNombre() {
        return nombre;
    }

    public String getColumna_Fact_Table() {
        return columna_Fact_Table;
    }

    public String getData_Type() {
        return data_Type;
    }

    public String getSql_Medida_Calculada() {
        return sql_Medida_Calculada;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Seters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColumna_Fact_Table(String columna_Fact_Table) {
        this.columna_Fact_Table = columna_Fact_Table;
    }

    public void setData_Type(String data_Type) {
        this.data_Type = data_Type;
    }

    public void setSql_Medida_Calculada(String sql_Medida_Calculada) {
        this.sql_Medida_Calculada = sql_Medida_Calculada;
    }
    
}
