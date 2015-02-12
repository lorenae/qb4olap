/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion_Virtuoso {

    
    public static void main(String[] args) {
        
        String s = null;
        
//        try {
//            Process p;
//            p = Runtime.getRuntime().exec("curl --digest --user demo:demo --verbose --url \" http://localhost:8890/sparql-graph-crud-auth?graph-uri=urn:graph:update:test:put\" -T books.ttl");
            
            
        try {
            
            ProcessBuilder shell = new ProcessBuilder("curl","--digest","--user", "demo:demo", "--verbose","--url", "http://localhost:8890/sparql-graph-crud-auth?graph-uri=urn:graph:update:test:put","-X","POST","-T", "books.ttl");
            final Process p = shell.start();
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Salida de la linea de comandos:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Errores de la linea de comando (Si hay):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(Conexion_Virtuoso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
