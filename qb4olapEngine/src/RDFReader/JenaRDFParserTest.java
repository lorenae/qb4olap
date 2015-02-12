
package RDFReader;

import Clases.manejadorCubos;
import static RDFReader.ValidadorRDF.ValidarRDFGenerado;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JenaRDFParserTest {

    public static void main(String[] args) throws IOException {
        
        //Inicializo las variable en las que van a ser almacenadas las estructuras
        manejadorCubos MC = new manejadorCubos();
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
            reader.obtenerNiveles(model,MC);
            reader.ObtenerAtributos(model, MC);
            reader.ObtenerMedidas(model, MC);
            reader.ObtenerCubos(model, MC);
            reader.obtenerInfoTablaInstancias(model, MC);
            in.close();
        }
        /*JFileChooser fileChooser2 = new JFileChooser(".");
        FileNameExtensionFilter filtroXML2 = new FileNameExtensionFilter("Turtle files (*.ttl)", "ttl");
        fileChooser.setFileFilter(filtroXML2);
        int estado2 = fileChooser2.showOpenDialog(null);
        if (estado2 == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado2 = fileChooser.getSelectedFile();
            InputStream in2 = new FileInputStream(archivoSeleccionado2.getAbsolutePath());
            //ValidarRDFGenerado(in);
            Model modelDB = ModelFactory.createDefaultModel();
            modelDB.read(in2, null, "TURTLE");
            RDFReader readerDB = new RDFReader();
            readerDB.obtenerInfoTablaInstancias(modelDB,MC);
        }
        */
    } 
}
