package Clases;

//Interface para las clases Tabla y Join, de esta manera estas clases pueden
//ser tratadas de la misma manera independiente del objeto. Cada objeto conoce
//su responsabilidad
public interface Source {
    public String PrintSelect(String Alias,String noPrintCol);
    public String PrintFrom();
}
