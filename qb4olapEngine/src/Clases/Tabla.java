package Clases;

import java.util.ArrayList;
import java.util.List;

//Clase que representa la tabla de una base de datos relacional, se almacena su
//nombre y los atributos que contiene.
public class Tabla implements Source{
    private String Nombre;
    private List<String> Atributos;

    public Tabla() {
        Atributos= new ArrayList<String>();
    }
    

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public List<String> getAtributos() {
        return Atributos;
    }

    public void setAtributos(List<String> Atributos) {
        this.Atributos = Atributos;
    }

   public void addAtributo(String attr){
       Atributos.add(attr);
   }

    public String PrintSelect(String Alias,String noPrintCol) {
        String sel= " ";
        for (String a : Atributos) {
            if(!a.equals(noPrintCol))
                sel += Alias+"."+ a + ",";
        }
        sel= sel.substring(0, sel.length()-1);
        sel += " ";
        return sel;
    }

    public String PrintFrom() {
        return " " + Nombre + " ";
    }
    
}
