

package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Clase para representa un Cubo multidemensional, se almacena su nombre, sus 
//medidas de agregacion, las columnas que son claves foraneas a los niveles
//mas bajo de cada una de las dimensiones relacionadas, los niveles involucrados
//y el nombre de la tabla de hechos.
public class cubo {

    private List<String> medidasAgregacion;
    private HashMap <String, String> niveles;
    private HashMap <String, String> dimension_Columna; //nombre dim, nombre columna
    private String nombre;
    private String tabla;


    ////////////////////////////////////////////////////////////////////////////
    //Constructors

    public cubo() {
        this.medidasAgregacion = new ArrayList<String>();
        this.niveles = new HashMap <String, String>();
        this.nombre = "";
        this.tabla = "";
        this.dimension_Columna = new HashMap <String, String>();
    }

    public cubo(String nombre) {
        this.medidasAgregacion = new ArrayList<String>();
        this.niveles = new HashMap <String, String>();
        this.nombre = nombre;
        this.tabla = "";
        this.dimension_Columna = new HashMap <String, String>();
    }

    public cubo(String nombre, String tabla) {
        this.medidasAgregacion = new ArrayList<String>();
        this.niveles = new HashMap <String, String>();
        this.nombre = nombre;
        this.tabla = tabla;
        this.dimension_Columna = new HashMap <String, String>();
    }

    public cubo(List<String> medidasAgregacion, HashMap <String, String> niveles, String nombre) {
        this.medidasAgregacion = medidasAgregacion;
        this.niveles = niveles;
        this.nombre = nombre;
        this.tabla = "";
        this.dimension_Columna = new HashMap <String, String>();
    }

    public cubo(List<String> medidasAgregacion, HashMap<String, String> niveles, String nombre, String tabla) {
        this.medidasAgregacion = medidasAgregacion;
        this.niveles = niveles;
        this.nombre = nombre;
        this.tabla = tabla;
        this.dimension_Columna = new HashMap <String, String>();
    }

    public cubo(List<String> medidasAgregacion, HashMap<String, String> niveles, HashMap<String, String> dimension_Columna, String nombre, String tabla) {
        this.medidasAgregacion = medidasAgregacion;
        this.niveles = niveles;
        this.dimension_Columna = dimension_Columna;
        this.nombre = nombre;
        this.tabla = tabla;
    }

    ////////////////////////////////////////////////////////////////////////////
    //Geters
    public List<String> getMedidasAgregacion() {
        return medidasAgregacion;
    }

   public String getNombre() {
        return nombre;
    }

    public HashMap <String, String> getNiveles() {
        return niveles;
    }

    public String getTabla() {
        return tabla;
    }

    public HashMap<String, String> getDimension_Columna() {
        return dimension_Columna;
    }

    ////////////////////////////////////////////////////////////////////////////
    //Seters

    public void setNiveles(HashMap <String, String> niveles) {
        this.niveles = niveles;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public void setDimension_Columna(HashMap<String, String> dimension_Columna) {
        this.dimension_Columna = dimension_Columna;
    }

    public void setMedidasAgregacion(List<String> medidasAgregacion) {
        this.medidasAgregacion = medidasAgregacion;
    }

    
    
}
