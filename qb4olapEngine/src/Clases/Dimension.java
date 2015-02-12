package Clases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Dimension de QB4OLAP, se almacena su nombre, los niveles y jerarquias que la
//componen
public class Dimension {

    private String nombre;
    private List<Nivel> nivelesMiembro;
    private List<Jerarquia> jerarquiasMiembro;

    ///////////////////////////////////////////////////////////////////////////
    // Constructores
    public Dimension() {
        nombre = "";
        nivelesMiembro = new ArrayList<Nivel>();
    }

    public Dimension(String name) {
        nombre = name;
        nivelesMiembro = new ArrayList<Nivel>();
    }

    public Dimension(String name, List<Nivel> niveles) {
        nombre = name;
        nivelesMiembro = niveles;
    }

    public Dimension(String nombre, List<Nivel> nivelesMiembro, List<Jerarquia> jerarquiasMiembro) {
        this.nombre = nombre;
        this.nivelesMiembro = nivelesMiembro;
        this.jerarquiasMiembro = jerarquiasMiembro;
    }

    ///////////////////////////////////////////////////////////////////////////
    //Gets
    public List<Nivel> getNivelesMiembro() {
        return nivelesMiembro;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Jerarquia> getJerarquiasMiembro() {
        return jerarquiasMiembro;
    }

    ///////////////////////////////////////////////////////////////////////////
    //Sets
    public void setNivelesMiembro(List<Nivel> niveles) {
        this.nivelesMiembro = niveles;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setJerarquiasMiembro(List<Jerarquia> jerarquiasMiembro) {
        this.jerarquiasMiembro = jerarquiasMiembro;
    }


    public String getMenorNivel() {
        Iterator<Nivel> iterador = nivelesMiembro.iterator();
        String nivelBase = "";
        boolean encontre = false;
        while (iterador.hasNext() && (!encontre)) {
            Nivel nivelActual = iterador.next();
            nivelBase = nivelActual.getNombre();
            boolean encontre2 = false;
            for (Nivel nivelActual2 : nivelesMiembro)  {
                if (nivelActual2.getNivelesPadre().containsKey(nivelActual.getNombre()) && nivelActual.getNombre()!=nivelActual2.getNombre())
                    encontre2 = true;
            }
            if (!encontre2)
                encontre = true;
        }
        return nivelBase;
    }
    
    public Nivel getMenorNivel2() {
        Iterator<Nivel> iterador = nivelesMiembro.iterator();
        Nivel nivelBase = null;
        boolean encontre = false;
        while (iterador.hasNext() && (!encontre)) {
            Nivel nivelActual = iterador.next();
            nivelBase = nivelActual;
            boolean encontre2 = false;
            for (Nivel nivelActual2 : nivelesMiembro)  {
                if (nivelActual2.getNivelesPadre().containsKey(nivelActual.getNombre()) && nivelActual.getNombre()!=nivelActual2.getNombre())
                    encontre2 = true;
            }
            if (!encontre2)
                encontre = true;
        }
        return nivelBase;
    }
}
