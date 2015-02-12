package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class inline_Tables {

    private String alias;
    private HashMap<Integer,String> nombreColumna_tipo;
    //Se guardan como csv los valores de la inline table separado por , (coma)
    private List<String> valores_tabla;

    public inline_Tables() {
        this.alias = "";
        this.valores_tabla = new ArrayList<String>();
        this.nombreColumna_tipo = new HashMap <Integer, String>();
    }

    public inline_Tables(String nombre, List<String> valores, HashMap <Integer, String> nombre_tipo) {
        this.alias = nombre;
        this.valores_tabla = valores;
        this.nombreColumna_tipo = nombre_tipo;
    }
    //Geters
    public HashMap<Integer, String> getNombreColumna_tipo() {
        return nombreColumna_tipo;
    }

    public List<String> getValores_tabla() {
        return valores_tabla;
    }

    public String getAlias() {
        return alias;
    }

    //Seters
    public void setNombreColumna_tipo(HashMap<Integer, String> nombreColumna_tipo) {
        this.nombreColumna_tipo = nombreColumna_tipo;
    }

    public void setValores_tabla(List<String> valores_tabla) {
        this.valores_tabla = valores_tabla;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    

}
