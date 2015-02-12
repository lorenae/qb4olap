package Clases;

//Atributo de QB4OLAP, en el se identifican su nombre y la columna de donde se
//obtiene informacion en la base de datos
public class Atributo {

    private String nombre;
    private String columna;

    public Atributo() {
        this.nombre = "";
        this.columna = "";
    }
    
    public Atributo(String nombre) {
        this.nombre = nombre;
        this.columna = "";
    }

    public Atributo(String nombre, String columna) {
        this.nombre = nombre;
        this.columna = columna;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




}
