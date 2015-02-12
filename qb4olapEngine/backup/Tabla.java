/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Braian
 */
public class Tabla implements Source{
    private String Nombre;
    private String Alias;
    private Tabla TablaJoinIzq;
    private String ColumnaJoinIzq;
    private Tabla TablaJoinDer;
    private String ColumnaJoinDer;

    public Tabla(String Nombre, Tabla TablaJoinIzq, String ColumnaJoinIzq, Tabla TablaJoinDer, String ColumnaJoinDer) {
        this.Nombre = Nombre;
        this.TablaJoinIzq = TablaJoinIzq;
        this.ColumnaJoinIzq = ColumnaJoinIzq;
        this.TablaJoinDer = TablaJoinDer;
        this.ColumnaJoinDer = ColumnaJoinDer;
    }

    public Tabla(String Nombre) {
        this.Nombre = Nombre;
    }
    public Tabla() {
    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Tabla getTablaJoinIzq() {
        return TablaJoinIzq;
    }

    public void setTablaJoinIzq(Tabla TablaJoinIzq) {
        this.TablaJoinIzq = TablaJoinIzq;
    }

    public String getColumnaJoinIzq() {
        return ColumnaJoinIzq;
    }

    public void setColumnaJoinIzq(String ColumnaJoinIzq) {
        this.ColumnaJoinIzq = ColumnaJoinIzq;
    }

    public Tabla getTablaJoinDer() {
        return TablaJoinDer;
    }

    public void setTablaJoinDer(Tabla TablaJoinDer) {
        this.TablaJoinDer = TablaJoinDer;
    }

    public String getColumnaJoinDer() {
        return ColumnaJoinDer;
    }

    public void setColumnaJoinDer(String ColumnaJoinDer) {
        this.ColumnaJoinDer = ColumnaJoinDer;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String Alias) {
        this.Alias = Alias;
    }
    
    
}
