package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Clase que representa un nivel de QB4OLAP, es una de las estructuras 
//principales del sistema y almacena informacion sobre su nombre, la dimension
//que lo contiene, lo niveles que son padres, su tabla de dimension (la cual
//puede ser una Tabla o una estructura del tipo Join), la columna de donde se 
//obtiene la informacion de la base de datos relacional, y los atributos del 
//mismo.
public class Nivel{

    private String nombre;
    private Dimension dimension;
    private HashMap<String,Nivel> nivelesPadre;
    private String tabla;
    private Source tablaCompuesta; //Si esta es null, queda igual (star) sino es snow
    private String columna;
    private List<String> jerarquias;
    private String nameColumn;
    private List<Atributo> atributos;
    private Atributo atr_ColumnaPrimaria;
    private Atributo atr_ColumnaNombre;


    ///////////////////////////////////////////////////////////////////////////
    // Constructores
    public Nivel(){
        nombre = "";
        dimension = new Dimension();
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = "";
        columna = "";
        jerarquias = new ArrayList<String>();
        nameColumn = "";
        atributos = new ArrayList<Atributo>();
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
    }
    
    public Nivel(String name){
        nombre = name;
        dimension = new Dimension();
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = "";
        columna = "";
        jerarquias = new ArrayList<String>();
        nameColumn = "";
        atributos =  new ArrayList<Atributo>();
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
    }
    
    public Nivel(String name,Dimension dim){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = "";
        columna = "";
        jerarquias = new ArrayList<String>();
        nameColumn = "";
        atributos =  new ArrayList<Atributo>();
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
    }
    
    public Nivel(String name,Dimension dim, List<String> jers,List<Atributo> atri){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = "";
        columna = "";
        jerarquias = jers;
        nameColumn = "";
        atributos = atri;
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
    }
    //utiliza tabla comun
    public Nivel(String name,Dimension dim,String tabla_nivel, String columna_tabla, String columna_Nombre,List<Atributo> atri){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = tabla_nivel;
        columna = columna_tabla;
        jerarquias = new ArrayList<String>();
        nameColumn = columna_Nombre;
        atributos = atri;
        atr_ColumnaPrimaria = new Atributo(name+"_PK",columna_tabla);
        atr_ColumnaNombre = new Atributo(name+"_ColNombre",columna_Nombre);
    }
    //utiliza tabla compuesta
    public Nivel(String name,Dimension dim,Source tablaC, String columna_tabla, String columna_Nombre,List<Atributo> atri){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        tablaCompuesta = tablaC;
        columna = columna_tabla;
        jerarquias = new ArrayList<String>();
        nameColumn = columna_Nombre;
        atributos = atri;
        atr_ColumnaPrimaria = new Atributo(name+"_PK",columna_tabla);
        atr_ColumnaNombre = new Atributo(name+"_ColNombre",columna_Nombre);
    }
    
    public Nivel(String name,Nivel nivelPa,Dimension dim,String tabla_nivel, String columna_tabla, String columna_Nombre,List<Atributo> atri){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        nivelesPadre.put(nivelPa.getNombre(),nivelPa);
        tabla = tabla_nivel;
        columna = columna_tabla;
        jerarquias = new ArrayList<String>();
        nameColumn = columna_Nombre;
        atributos = atri;
        atr_ColumnaPrimaria = new Atributo(name+"_PK",columna_tabla);
        atr_ColumnaNombre = new Atributo(name+"_ColNombre",columna_Nombre);
    }
    public Nivel(String name,Nivel nivelPa,Dimension dim,Source tablaC, String columna_tabla, String columna_Nombre,List<Atributo> atri){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        nivelesPadre.put(nivelPa.getNombre(),nivelPa);
        tablaCompuesta = tablaC;
        columna = columna_tabla;
        jerarquias = new ArrayList<String>();
        nameColumn = columna_Nombre;
        atributos = atri;
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
        atr_ColumnaPrimaria = new Atributo(name+"_PK",columna_tabla);
        atr_ColumnaNombre = new Atributo(name+"_ColNombre",columna_Nombre);
    }
    public Nivel(String name, Dimension dimension, String tabla, String columna, List<String> jerarquias) {
        this.nombre = name;
        this.dimension = dimension;
        this.tabla = tabla;
        this.columna = columna;
        this.jerarquias = jerarquias;
        this.nameColumn = "";
        atributos = new ArrayList<Atributo>();
        atr_ColumnaNombre = new Atributo();
        atr_ColumnaPrimaria = new Atributo(name+"_PK",columna);
    }

    public Nivel(String nombre, Dimension dimension, String tabla, String columna, List<String> jerarquias, String columnaNombre) {
        this.nombre = nombre;
        this.dimension = dimension;
        this.tabla = tabla;
        this.columna = columna;
        this.jerarquias = jerarquias;
        this.nameColumn = columnaNombre;
        atributos = new ArrayList<Atributo>();
        atr_ColumnaPrimaria = new Atributo();
        atr_ColumnaNombre = new Atributo();
        atr_ColumnaPrimaria = new Atributo(nombre+"_PK",columna);
        atr_ColumnaNombre = new Atributo(nombre+"_ColNombre",columnaNombre);
    }

    public Nivel(String nombre, Dimension dimension, HashMap<String, Nivel> nivelesPadre, String tabla, Source tablaCompuesta, String columna, List<String> jerarquias, String columnaNombre, List<Atributo> atributos) {
        this.nombre = nombre;
        this.dimension = dimension;
        this.nivelesPadre = nivelesPadre;
        this.tabla = tabla;
        this.tablaCompuesta = tablaCompuesta;
        this.columna = columna;
        this.jerarquias = jerarquias;
        this.nameColumn = columnaNombre;
        this.atributos = atributos;
        atr_ColumnaPrimaria = new Atributo(nombre+"_PK",columna);
        atr_ColumnaNombre = new Atributo(nombre+"_ColNombre",columnaNombre);
    }
    
    public Nivel(String name,Dimension dim, List<String> jers,List<Atributo> atri, Atributo attrPK, Atributo attrDsc){
        nombre = name;
        dimension = dim;
        nivelesPadre = new HashMap<String,Nivel>();
        tabla = "";
        columna = attrPK.getColumna();
        jerarquias = jers;
        nameColumn = attrDsc.getColumna();
        atributos = atri;
        atr_ColumnaPrimaria = attrPK;
        atr_ColumnaNombre = attrDsc;
    }


    ///////////////////////////////////////////////////////////////////////////
    //Gets

    public HashMap<String,Nivel> getNivelesPadre() {
        return nivelesPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public String getColumna() {
        return columna;
    }

    public String getTabla() {
        return tabla;
    }

    public List<String> getJerarquias() {
        return jerarquias;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    ///////////////////////////////////////////////////////////////////////////
    //Sets
    public void setNivelesPadre(HashMap<String,Nivel> nivelesPadre) {
        this.nivelesPadre = nivelesPadre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public void setJerarquia(List<String> jerarquias) {
        this.jerarquias = jerarquias;
    }

    public void setJerarquias(List<String> jerarquias) {
        this.jerarquias = jerarquias;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    //Mas funciones
    private boolean existePadre(String nombrePadre){
        return nivelesPadre.containsKey(nombrePadre);
    }

    public void agregarPadre(Nivel nivelPadre)
    {
        if (!existePadre(nivelPadre.getNombre()))
            nivelesPadre.put(nivelPadre.getNombre(),nivelPadre);
    }

    public void agregarJerarquia(String nomJerarquia)
    {
        jerarquias.add(nomJerarquia);
    }

    public Source getTablaCompuesta() {
        return tablaCompuesta;
    }

    public void setTablaCompuesta(Source tablaCompuesta) {
        this.tablaCompuesta = tablaCompuesta;
    }

    public Atributo getAtr_ColumnaPrimaria() {
        return atr_ColumnaPrimaria;
    }

    public void setAtr_ColumnaPrimaria(Atributo atr_ColumnaPrimaria) {
        this.atr_ColumnaPrimaria = atr_ColumnaPrimaria;
    }

    public Atributo getAtr_ColumnaNombre() {
        return atr_ColumnaNombre;
    }

    public void setAtr_ColumnaNombre(Atributo atr_ColumnaNombre) {
        this.atr_ColumnaNombre = atr_ColumnaNombre;
    }
    
    
    
}
