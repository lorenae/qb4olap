package Clases;

import java.util.ArrayList;
import java.util.List;

//Clase que representa una Jerarquia de QB4OLAP se almacena su nombre, el nombre
//de la dimension donde se encuentra y los niveles dentro de la misma.
public class Jerarquia {

    private String nombre;
    private String dimension;
    private List<Nivel> nivelesMiembro;

    //Constructores

    public Jerarquia() {
        this.nombre = "";
        this.dimension = "";
        this.nivelesMiembro = new ArrayList<Nivel>();
    }
    
    public Jerarquia(String nombre, String dimension) {
        this.nombre = nombre;
        this.dimension = dimension;
        this.nivelesMiembro = new ArrayList<Nivel>();
    }

    public Jerarquia(String nombre, String dimension, List<Nivel> nivelesMiembro) {
        this.nombre = nombre;
        this.dimension = dimension;
        this.nivelesMiembro = nivelesMiembro;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<Nivel> getNivelesMiembro() {
        return nivelesMiembro;
    }

    public void setNivelesMiembro(List<Nivel> nivelesMiembro) {
        this.nivelesMiembro = nivelesMiembro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
