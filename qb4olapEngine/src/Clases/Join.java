package Clases;

//Clase que representa el Join de dos tablas relacionales, se almacena es una
//estructura arbolecente, donde se guarda los elemenos a su izquierda y derecha
//los cuales pueden ser Tabla o Join, se indica tambien cuales son las columnas
//por las cuales se establece la codicion de join y su alias.
public class Join implements Source{
    Source Izq;
    Source Der;
    String columnaIzq;
    String columnaDer;
    String AliasDerecha;
    String AliasIzquierda;

    public Join() {
    }

    public Source getIzq() {
        return Izq;
    }

    public void setIzq(Source Izq) {
        this.Izq = Izq;
    }

    public Source getDer() {
        return Der;
    }

    public void setDer(Source Der) {
        this.Der = Der;
    }

    public String getColumnaIzq() {
        return columnaIzq;
    }

    public void setColumnaIzq(String columnaIzq) {
        this.columnaIzq = columnaIzq;
    }

    public String getColumnaDer() {
        return columnaDer;
    }

    public void setColumnaDer(String columnaDerecha) {
        this.columnaDer = columnaDerecha;
    }

    public String getAliasDerecha() {
        return AliasDerecha;
    }

    public void setAliasDerecha(String AliasDerecha) {
        this.AliasDerecha = AliasDerecha;
    }

    public String getAliasIzquierda() {
        return AliasIzquierda;
    }

    public void setAliasIzquierda(String AliasIzquierda) {
        this.AliasIzquierda = AliasIzquierda;
    }
 
    public String PrintSelect(String Alias, String noPrintCol) {
        String sel= "";
        if(Alias.equals(""))
            sel = " " + Der.PrintSelect(AliasDerecha,"") + "," + Izq.PrintSelect(AliasIzquierda,columnaDer);
        else
            sel = " " + Der.PrintSelect(Alias,"") + "," + Izq.PrintSelect(AliasIzquierda, columnaDer);
        return sel;
    }

    public String PrintFrom() {
        String query = "(";
        query += "SELECT " + Der.PrintSelect(AliasDerecha,"") +" , " +Izq.PrintSelect(AliasIzquierda,columnaDer);
        query += " FROM " + Der.PrintFrom() + " AS " +AliasDerecha +" JOIN " + Izq.PrintFrom()+ " AS " +AliasIzquierda ;
        query += " ON " + AliasDerecha + "." +columnaDer + "=" + AliasIzquierda+ "." +columnaIzq + ")";
        return query;
    }
    
}
