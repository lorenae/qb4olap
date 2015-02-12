/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Braian
 */
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
    
}
