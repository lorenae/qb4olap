package Clases;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


//Clase responsable de conexion a la base de datos mediante la tecnologia JDBC
public class JDBC {
    private Connection conn;
    public JDBC() {
        conn = null;
        try
           {
               Class.forName(Settings.getInstance().driver);
               conn = DriverManager.getConnection(Settings.getInstance().getBaseUrl(),Settings.getInstance().userName,Settings.getInstance().password);
//               System.out.println ("Database connection established");
           }
           catch (Exception e)
           {
               e.printStackTrace();
               System.err.println ("Ocurrio un error al conectarse a la Base de Datos.");
           }
           
       }

    public Connection getConnection() {
        return conn;
    }
   
    
    //aca se puede agregar el tema del precio
    public void ingresarPrenda(String nom){
        try {
            Statement st = conn.createStatement();
            String query = "INSERT INTO Prenda (nombre) VALUES ('" + nom + "',)";
            st.executeUpdate(query);
        
        
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    } 
    
    public Collection listarPrendas(){
        Collection prendas = null;
        String query = "SELECT * FROM Prendas";
        try {
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery(query);
            //seguir aca
            return prendas;
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return prendas;
        }
        
        
    } 
    
   public void CerrarConeccion(){
         try
                   {
                       conn.close ();
                       System.out.println ("Database connection terminated");
                   }
         catch (Exception e) { /* ignore close errors */ }
    }
}
