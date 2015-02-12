package RDFReader;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.InputStream;

public class ValidadorRDF {

    private ValidadorRDF() {
    }

    public static void ValidarRDFGenerado(InputStream in) {
        Model model = ModelFactory.createDefaultModel();
        model.read(in, null, "TURTLE");       
        String queryString = " prefix qb: <http://purl.org/linked-data/cube#> "
                + " prefix qb4o: <http://purl.org/qb4olap/cubes#> "
                + " prefix nw: <http://www.fing.edu.uy/inco/cubes/schemas/northwind#> "
                + " SELECT ?dsd ?l "
                + " FROM   <http://www.fing.edu.uy/inco/cubes/schemas/northwind> "
                + " WHERE { ?dsd a qb:DataStructureDefinition ."
                + " ?dsd qb:component ?c. "
                + " ?c qb4o:level ?l "
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource dsd = soln.getResource("dsd");
                Resource l = soln.getResource("l");
                System.out.println(dsd.toString());
                System.out.println(l.toString());
            }
        } finally {
            qexec.close();
        }
    }
}
