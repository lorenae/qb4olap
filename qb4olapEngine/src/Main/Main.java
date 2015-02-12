package Main;

import Clases.Settings;
import Clases.manejadorCubos;
import Clases.xml_Parser;
import RDFReader.RDFReader;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Validator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Main {
    private static DocumentBuilder builder= null;
    

    static boolean validateAgainstXSD(InputStream xml, InputStream xsd)
    {
        try
        {
            SchemaFactory factory = 
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = (Validator) schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, JAXBException, FileNotFoundException, IOException {
        boolean esMondrianSchema = true;
        String dbname = "";
        String server = "";
        String driver = "";
        String urlBase = "";
        String user = "";
        String password = "";
        String port = "";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            //Es Mondrian Schama
            if(arg.equals("-M")) esMondrianSchema = true;
            //Es QB4OLAP Schema
            if(arg.equals("-Q")) esMondrianSchema = false;
            //Nombre de la Base de Datos
            if(arg.equals("-db")) dbname = args[i+1];
            //Nombre del servidor
            if(arg.equals("-s")) server = args[i+1];
            //Driver
            if(arg.equals("-d")) driver = args[i+1];
            //URL Base
            if(arg.equals("-U")) urlBase = args[i+1];
            //Usuario
            if(arg.equals("-u")) user = args[i+1];
            //Password
            if(arg.equals("-p")) password = args[i+1];
            //Password
            if(arg.equals("-P")) port = args[i+1];
        }
        //informacion obligatoria
        String error = "";
        if(urlBase.equals("")) error += " 'URL Base'";
        if(dbname.equals("")) error += " 'Nombre Base de Datos'";
        if(server.equals("")) error += " 'Servidor'";
        if(port.equals("")) error += " 'Puerto'"; 
        if(user.equals("")) error += " 'Usuario'";
        if(password.equals("")) error += " 'Password'";
        
        if(!error.equals("")){
            System.out.println("Falta informacion:"+ error);
        }else{
            //Inicializo conexion
            Settings.getInstance().setDbName(dbname);
            Settings.getInstance().setPassword(password);
            Settings.getInstance().setPort(port);
            Settings.getInstance().setServer(server);
            Settings.getInstance().setUserName(user);            
            if(!driver.isEmpty()) Settings.getInstance().setDriver(driver);
            //Inicializo las variable en las que van a ser almacenadas las estructuras
            manejadorCubos MC = new manejadorCubos();
            MC.setURI_Usuario(urlBase);
            
            if(esMondrianSchema){
                JFileChooser fileChooser = new JFileChooser(".");
                FileNameExtensionFilter filtroXML = new FileNameExtensionFilter("xml files (*.xml)", "xml");
                fileChooser.setFileFilter(filtroXML);
                int estado = fileChooser.showOpenDialog(null);
                if (estado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
        //            System.out.println(archivoSeleccionado.getParent());
                    //Valido el Mondrian Schema contra el DTD
                    File xsd = new File("./mondrian.xsd");
    //                ValidateExternalSchema ves = new ValidateExternalSchema(new FileInputStream(archivoSeleccionado));
        //            if (validadorXML(new FileInputStream(archivoSeleccionado)) == true)
        //                System.out.println("PASO VALIDACION XSD");
        //            else
        //                System.out.println("EL ARCHIVO INGRESADO NO CONTIENE UN ESQUEMA VALIDO");
                    //Comienza el parseo del xml que se selecciono
                    try {

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(archivoSeleccionado);
                        doc.getDocumentElement().normalize();

        //                System.out.println("root of xml file " + doc.getDocumentElement().getNodeName());
        //                System.out.println("==========================");

                        //Primero obtengo las dimensiones con sus niveles
                        //Recorro las jerarquias de cada dimension tomando de a uno los niveles
                        //el primer nivel que aparece no tiene padre y guardo este para que sea el padre
                        //del proximo. Cuadndo voy a agregar un nuevo nivel con su padre primero pregunto
                        //si existe. Si existe le agrego un padre mas y sino creo nomas el nivel con el padre.

                        //Obtengo Instancia del Parser del XML de Mondrian
                        xml_Parser parser = xml_Parser.getInstance();

                        //Inicio carga de los niveles de las dimensiones
                        parser.obtener_dimensiones(doc,MC);
                        //Fin carga de los niveles de las dimensiones
                        System.out.println("Dimension/es pareseadas correctamente.");

                        //Inicio carga de medidas y cubos
                        parser.obtener_Cubos(doc,MC);
                        //Fin carga de medidas y cubos
                        System.out.println("Cubo/s pareseados correctamente.");


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (estado == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Operacion cancelada por el Usuario.");
                }
                MC.imprimirRDF();
                System.out.println("Fin de la ejecucion.");
            }else{
                JFileChooser fileChooser = new JFileChooser(".");
                FileNameExtensionFilter filtroXML = new FileNameExtensionFilter("Turtle files (*.ttl)", "ttl");
                fileChooser.setFileFilter(filtroXML);
                int estado = fileChooser.showOpenDialog(null);
                if (estado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    InputStream in = new FileInputStream(archivoSeleccionado.getAbsolutePath());
                    //ValidarRDFGenerado(in);
                    Model model = ModelFactory.createDefaultModel();
                    model.read(in, null, "TURTLE");
                    RDFReader reader = new RDFReader();
                    reader.ObtenerDimensiones(model,MC);
                    reader.ObtenerAtributos(model, MC);
                    reader.ObtenerMedidas(model, MC);
                    reader.ObtenerCubos(model, MC);
                    reader.obtenerInfoTablaInstancias(model, MC);
                    in.close();
                    MC.imprimirRDF();
                    System.out.println("Fin de la ejecucion.");
                }
            }
        }
    }
}
