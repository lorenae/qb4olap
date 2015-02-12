package RDFReader;

import Clases.Atributo;
import Clases.Dimension;
import Clases.JDBC;
import Clases.Jerarquia;
import Clases.Join;
import Clases.Nivel;
import Clases.Source;
import Clases.Tabla;
import Clases.cubo;
import Clases.manejadorCubos;
import Clases.medida;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RDFReader {

    private final String qbDimension = "http://purl.org/linked-data/cube#DimensionProperty";
    private final String qbMeasure = "http://purl.org/linked-data/cube#MeasureProperty";
    private final String qbMeasureDef = "http://purl.org/linked-data/cube#measure";
    private final String qbDataStructureDefinition = "http://purl.org/linked-data/cube#DataStructureDefinition";
    private final String qb4oInDimension = "http://purl.org/qb4olap/cubes#inDimension";
    private final String qb4oHierarchy = "http://purl.org/qb4olap/cubes#HierarchyProperty";
    private final String qb4ohierarchyComponent = "http://purl.org/qb4olap/cubes#hierarchyComponent";
    private final String qb4oLevelDef = "http://purl.org/qb4olap/cubes#level";
    private final String qb4oLevel = "http://purl.org/qb4olap/cubes#LevelProperty";
    private final String qbAttribute = "http://purl.org/linked-data/cube#AttributeProperty";
    private final String qb4oLevelInHierarchy = "http://purl.org/qb4olap/cubes#LevelInHierarchy";
    private final String qb4oHierarchyStep = "http://purl.org/qb4olap/cubes#HierarchyStep";
    private final String qb4ohasHierarchy = "http://purl.org/qb4olap/cubes#hasHierarchy";
    private final String qb4ohasAttribute = "http://purl.org/qb4olap/cubes#hasAtribute";
    private final String qb4oInHierarchy = "http://purl.org/qb4olap/cubes#inHierarchy";
    private final String qb4olevelComponent = "http://purl.org/qb4olap/cubes#levelComponent";
    private final String qb4ochildLevel = "http://purl.org/qb4olap/cubes#childLevel";
    private final String qb4oparentLevel = "http://purl.org/qb4olap/cubes#parentLevel";
    private final String qbComponent = "http://purl.org/linked-data/cube#component";
    private final String qb4oaggregateFunction = "http://purl.org/qb4olap/cubes#hasAggregateFunction";
    private final String dbmapAttributeColumn = "http://www.example.com/dbmap#attributeColumn";
    private final String dbmaphasSource = "http://www.example.com/dbmap#hasSource";
    private final String dbmapisDsc = "http://www.example.com/dbmap#isDsc";
    private final String uriAtributos = "http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/attributes#";
    private final String rdfsLabel = "http://www.w3.org/2000/01/rdf-schema#label";
    private final String qb4oHasId = "http://purl.org/qb4olap/cubes#hasID";
    private final String qb4oEsFK = "http://www.example.com/dbmap#isFK";
    private final String dbmapEsDsc = "http://www.example.com/dbmap#isDsc";
    private final String dbmapJoin = "http://www.example.com/dbmap#Join";
    private final String dbmapJoinRight = "http://www.example.com/dbmap#joinRightColumn";
    private final String dbmapJoinLeft = "http://www.example.com/dbmap#joinLeftColumn";
    private final String dbmapMeasureColumn = "http://www.example.com/dbmap#measureColumn";
    private final String dbmapCubeTable  = "http://www.example.com/dbmap#cubeTable";
    private final String dbmapLevelColumn  = "http://www.example.com/dbmap#levelColumn";
    public void RDFReader() {

    }

    private void printTriple(Resource subject, Property predicate, RDFNode object) {
        System.out.print(subject.toString());
        System.out.print(" " + predicate.toString() + " ");
        if (object instanceof Resource) {
            System.out.print(object.toString());
        } else {
            // object is a literal
            System.out.print(" \"" + object.toString() + "\"");
        }
        System.out.println(" .");
    }

    private List<Source> JoinIterativo(Model model) throws SQLException{
        Property propJoinLeft = ResourceFactory.createProperty(dbmapJoinLeft);
        Property propJoinRight = ResourceFactory.createProperty(dbmapJoinRight);
        Property propHasSource = ResourceFactory.createProperty(dbmaphasSource);
        Property rdfsLabelPred = ResourceFactory.createProperty(rdfsLabel);
        Resource typeJoin = ResourceFactory.createResource(dbmapJoin);
        Resource subject;
        Property predicate;
        RDFNode object;
        List<Source> sources = new ArrayList<Source>();
        StmtIterator iterJ = model.listStatements(null,RDF.type , (RDFNode) typeJoin);
        while (iterJ.hasNext()) {
            Statement statement = iterJ.next();
            subject = statement.getSubject();
            Join j = new Join();
                //Tabla Izq
                StmtIterator iter = model.listStatements(statement.getSubject(),propJoinLeft , (RDFNode) null);
                Tabla tIzq = new Tabla();
                while (iter.hasNext()) {
                    Statement statement1 = iter.next();
                    //obtengo nombre col Izq
                    StmtIterator iterColName = model.listStatements(statement1.getObject().asResource(),rdfsLabelPred , (RDFNode) null);
                    while (iterColName.hasNext()) {
                        Statement statement3 = iterColName.next();
                        j.setColumnaIzq(statement3.getObject().toString());
                    }
                    //Tomo tabla
                    StmtIterator iter4 = model.listStatements(statement1.getObject().asResource(),propHasSource , (RDFNode) null);
                    while (iter4.hasNext()) {
                        Statement statement2 = iter4.next();
                        //tabla objeto
                        object = statement2.getObject();
                        //obtengo nombre tabla
                        StmtIterator iter5 = model.listStatements(object.asResource(),rdfsLabelPred , (RDFNode) null);
                        while (iter5.hasNext()) {
                            Statement statement3 = iter5.next();
                            tIzq.setNombre(statement3.getObject().toString());
                        }
                    }
                }
                fillTableAttr(tIzq);
                j.setIzq(tIzq);
                j.setAliasIzquierda(tIzq.getNombre());
                //Tabla Der
                iter = model.listStatements(statement.getSubject(),propJoinRight , (RDFNode) null);
                Tabla tDer = new Tabla();
                while (iter.hasNext()) {
                    Statement statement1 = iter.next();
                    //obtengo nombre col Izq
                    StmtIterator iterColName = model.listStatements(statement1.getObject().asResource(),rdfsLabelPred , (RDFNode) null);
                    while (iterColName.hasNext()) {
                        Statement statement3 = iterColName.next();
                        j.setColumnaDer(statement3.getObject().toString());
                    }
                    //Tomo tabla
                    StmtIterator iter4 = model.listStatements(statement1.getObject().asResource(),propHasSource , (RDFNode) null);
                    while (iter4.hasNext()) {
                        Statement statement2 = iter4.next();
                        //tabla objeto
                        object = statement2.getObject();
                        //obtengo nombre tabla
                        StmtIterator iter5 = model.listStatements(object.asResource(),rdfsLabelPred , (RDFNode) null);
                        while (iter5.hasNext()) {
                            Statement statement3 = iter5.next();
                            tDer.setNombre(statement3.getObject().toString());
                        }
                    }
                }
                fillTableAttr(tDer);
                j.setDer(tDer);
                j.setAliasDerecha(tDer.getNombre());
                sources.add(j);
        }
        return integrarSources(sources);
        
    }
    private List<Source> integrarSources(List<Source> sources) {
        if(sources.isEmpty()){
            return new ArrayList<Source>();
        }else{
            Iterator<Source> iter = sources.iterator();
            Source source = iter.next();
            boolean integro = false;
            while (iter.hasNext()) {
                Source elem = iter.next();
                integro = compararEIntegrar(source,elem);    
                if(integro) sources.remove(elem);
            }
            List<Source> aRetornar = integrarSources(sources.subList(1, sources.size()));
            aRetornar.add(source);
            return aRetornar;
        }
    }
    
    //integra en source aIntegrar si es posible, se supone que la integracion a
    //source es unica
    private boolean compararEIntegrar(Source source, Source aIntegrar){
        
        boolean integre = false;
        if(((Join)source).getDer().getClass().equals(Join.class)){
            integre = compararEIntegrar(((Join)source).getDer(), aIntegrar);
        }else{
            if(pertenece(((Tabla)((Join)source).getDer()).getNombre(), aIntegrar)){
                ((Join)source).setDer(aIntegrar);
                return true;
            }else
                return false;
        }
        if(((Join)source).getIzq().getClass().equals(Join.class) && !integre ){
            integre = compararEIntegrar(((Join)source).getIzq(), aIntegrar);
        }else{
            if(pertenece(((Tabla)((Join)source).getIzq()).getNombre(), aIntegrar)){
                ((Join)source).setIzq(aIntegrar);
                return true;
            }else
                return false;
        }
        return integre;
    }
    
    //nombre de Tabla, Source
    private boolean pertenece(String t, Source s){
        if(s.getClass().equals(Join.class)){
            return pertenece(t,((Join)s).getDer()) || pertenece(t,((Join)s).getIzq());
        }else{
            return ((Tabla)s).getNombre().equals(t);
        }            
    }
    
    private void fillTableAttr(Tabla t) throws SQLException{
        JDBC jdbc = new JDBC();
        Connection con = jdbc.getConnection();
        java.sql.Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM "+ t.getNombre());
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i =1; i <= rsmd.getColumnCount(); i++) {
            t.addAtributo(rsmd.getColumnName(i));
        }
    }
    
    private Source getTablaJoins(Model model, String tabla) throws SQLException {
        Source source = null;
        List<Source> sources = JoinIterativo(model);
        for (Source iter : sources) {
            if(pertenece(tabla, iter))
                source = iter;
        }
        return source;
    }
    public void obtenerInfoTablaInstancias(Model model, manejadorCubos MC) {
        HashMap<String, Dimension> dimensiones = MC.getDimensiones();
        Property attrColDbmap = ResourceFactory.createProperty(dbmapAttributeColumn);
        Property propIsDsc = ResourceFactory.createProperty(dbmapEsDsc);
        Property rdfsLabelPred = ResourceFactory.createProperty(rdfsLabel);
        Property propAttrCol = ResourceFactory.createProperty(dbmapAttributeColumn);
        Resource subject;
        Property predicate;
        RDFNode object;
      //  for (Dimension dim : dimensiones.values()) {
            //Obtengo los niveles de la dimension
        Collection<Nivel> niveles = MC.getNiveles().values();
        HashMap<String, String> tablas = new HashMap<String, String>();
        for (Nivel nivel : niveles) {
            System.out.println("ENTRE NIVEL");
            //Obtengo Atributo Nombre
            StmtIterator iterAttrDsc = model.listStatements(null,propIsDsc , (RDFNode) null);
            while (iterAttrDsc.hasNext()) {
                Statement elemAttr = iterAttrDsc.next();
                subject = elemAttr.getSubject();
                object = elemAttr.getObject();
                String nomNivel = subject.toString().split("#")[1];
                if(nomNivel.equals(nivel.getNombre())){
                     StmtIterator iterAttr = model.listStatements(object.asResource(),propAttrCol , (RDFNode) null);
                     while (iterAttr.hasNext()) {
                        Statement statement = iterAttr.next();
                        object = statement.getObject();
                        StmtIterator labeliter = model.listStatements(object.asResource(),rdfsLabelPred , (RDFNode) null);
                        if(!labeliter.hasNext()) nivel.setNameColumn(nivel.getNameColumn());
                        while (labeliter.hasNext()) {
                             Statement statmentLabel = labeliter.next();
                             nivel.setNameColumn(statmentLabel.getObject().toString());
                        }
                     }
                }
            }
            //Obtengo atributo primario
            Atributo colPK = nivel.getAtr_ColumnaPrimaria();
            String nombre_colPK = colPK.getNombre();
            Resource attrActualUri = ResourceFactory.createResource(uriAtributos + nombre_colPK);
            StmtIterator iterAttributes = model.listStatements(attrActualUri, attrColDbmap, (RDFNode) null);
            while (iterAttributes.hasNext()) {
                Statement elemAttr = iterAttributes.nextStatement();
                subject = elemAttr.getSubject();
                predicate = elemAttr.getPredicate();
                //Columna primaria de la tabla
                object = elemAttr.getObject();
                Resource PKColumna = object.asResource();
                printTriple(subject, predicate, object);
                

                //Obtengo el nombre de la tabla
                Property hasSourceDbmap = ResourceFactory.createProperty(dbmaphasSource);
                StmtIterator iterColumnas = model.listStatements(object.asResource(), hasSourceDbmap, (RDFNode) null);
                while (iterColumnas.hasNext()) {
                    Statement elemTable = iterColumnas.nextStatement();
                    //Obtengo el objeto tabla
                    object = elemTable.getObject();
                    StmtIterator iterNomTabla = model.listStatements(object.asResource(), rdfsLabelPred, (RDFNode) null);
                    while (iterNomTabla.hasNext()) {
                        Statement elemNomTable = iterNomTabla.nextStatement();
                        //Obtengo el nombre de la tabla
                        object = elemNomTable.getObject();
                        String nombreTabla = object.toString();
                        nivel.setTabla(nombreTabla);
                        try {
                            //Obtengo TablaCompuesta (Join) si la tiene.
                            nivel.setTablaCompuesta(getTablaJoins(model, nombreTabla));
                        } catch (SQLException ex) {
                            Logger.getLogger(RDFReader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }    
            }
            

            //Doy de alta las tablas para los niveles de la dimension actual
            if (tablas.size() == 1) {

            } else {

            }
         }
    }

    public String obtenerColumnaAtributo(Model model, String atributoStr) {
        Property attrColDbmap = ResourceFactory.createProperty(dbmapAttributeColumn);
        Property rdfsLabelPred = ResourceFactory.createProperty(rdfsLabel);
        Resource atributo = ResourceFactory.createResource(atributoStr);
        Resource subject;
        Property predicate;
        RDFNode object;
        String nombreColumna = "";
        //Obtengo mapeo del atributo que paso por parametro
        StmtIterator iterAttributes = model.listStatements(atributo, attrColDbmap, (RDFNode) null);
        while (iterAttributes.hasNext()) {
            Statement elemAttr = iterAttributes.nextStatement();
            subject = elemAttr.getSubject();
            predicate = elemAttr.getPredicate();
            object = elemAttr.getObject();
            //printTriple(subject, predicate, object);

            //Obtengo el nombre de la columna
            StmtIterator iterNomColumna = model.listStatements(object.asResource(), rdfsLabelPred, (RDFNode) null);
            while (iterNomColumna.hasNext()) {
                Statement elemLabelCol = iterNomColumna.nextStatement();
                object = elemLabelCol.getObject();
                nombreColumna = object.toString();
            }
        }
        return nombreColumna;
    }

    public void AgregarNivelesPadre(Model model, manejadorCubos MC ,Resource level) {
        HashMap<String, Nivel> nivelesPadre = new HashMap<String, Nivel>();
        Property resParentLevel = ResourceFactory.createProperty(qb4oparentLevel);
        Property resChildLevel = ResourceFactory.createProperty(qb4ochildLevel);
        Property resLevelComp = ResourceFactory.createProperty(qb4olevelComponent);
        StmtIterator iter1 = model.listStatements(null, resLevelComp, (RDFNode) level);
        while (iter1.hasNext()) {
            Statement elemLevel = iter1.nextStatement();
            Resource nivelBlanco = elemLevel.getSubject();
            StmtIterator iter2 = model.listStatements(null, resChildLevel, (RDFNode) nivelBlanco);
            while (iter2.hasNext()) {
                Statement elemStep = iter2.nextStatement();
                Resource stepBlanco = elemStep.getSubject();

                //Obtengo el nodo blanco que corresponde a la definicion del padre en la jerarquia
                StmtIterator iter3 = model.listStatements(stepBlanco, resParentLevel, (RDFNode) null);
                RDFNode padreBlanco = iter3.nextStatement().getObject();

                //Obtengo el nivel padre con el nodo blanco obtenido anteriormente
                StmtIterator iter4 = model.listStatements(padreBlanco.asResource(), resLevelComp, (RDFNode) null);
                String nivelPadreAgregarNom = iter4.nextStatement().getObject().toString().split("#")[1];
//                Nivel nivelPadreAgregar = new Nivel(nivelPadreAgregarNom);
                Nivel nivelPadreAgregar = MC.getNiveles().get(nivelPadreAgregarNom);
                nivelesPadre.put(nivelPadreAgregarNom, nivelPadreAgregar);
            }
        }
        String nomNivelActual = level.toString().split("#")[1];
        MC.getNiveles().get(nomNivelActual).setNivelesPadre(nivelesPadre);
    }

    public void obtenerNiveles(Model model, manejadorCubos MC) {
        Resource subject;
        Property predicate;
        RDFNode object;
        Resource resLevel = ResourceFactory.createResource(qb4oLevel);
        Property propAtrib = ResourceFactory.createProperty(qb4ohasAttribute);
        Property propDim = ResourceFactory.createProperty(qb4oInDimension);
        Property propHasId = ResourceFactory.createProperty(qb4oHasId);
        Property propIsDsc = ResourceFactory.createProperty(dbmapisDsc);

        StmtIterator iter1 = model.listStatements(null, RDF.type, (RDFNode) resLevel);
        while (iter1.hasNext()) {
            Atributo attr_PK = new Atributo();
            Atributo attr_Dsc = new Atributo();
            Statement elemLevel = iter1.nextStatement();
            subject = elemLevel.getSubject();
            predicate = elemLevel.getPredicate();
            object = elemLevel.getObject();
            printTriple(subject, predicate, object);

            //Obtengo el nombre del nivel
            String nomNivel = subject.toString().split("#")[1];

            //Obtengo los atributos definidos dentro del nivel
            StmtIterator iter3 = model.listStatements(subject, propAtrib, (RDFNode) null);
            List<Atributo> listAttr = new ArrayList<Atributo>();
            while (iter3.hasNext()) {
                Object objectAttr = iter3.nextStatement().getObject();
                String nomAttr = objectAttr.toString().split("#")[1];
                String columnaTabla = obtenerColumnaAtributo(model, objectAttr.toString());
                Atributo attrAux = new Atributo(nomAttr, columnaTabla);
                //Pregunto si el Atributo es el de clave primaria
                if (model.contains(subject, propHasId, (RDFNode) objectAttr)) {
                    attr_PK = attrAux;
                }
                //Pregunto si el Atributo es descriptivo
                if (model.contains(subject, propIsDsc, (RDFNode) objectAttr)) {
                    attr_Dsc = attrAux;
                }
                //Si no es el atributo descriptivo o el PK los agrego
                if (!model.contains(subject, propHasId, (RDFNode) objectAttr))
                    if (!model.contains(subject, propIsDsc, (RDFNode) objectAttr))
                        listAttr.add(attrAux);
            }
            //veficio que tenga atributo descriptivo sino se lo asigno
            if(attr_Dsc.getNombre().equals(""))
                attr_Dsc = attr_PK;
            //Obtengo las jerarquias a las que pertenece el nivel
            Property resInHier = ResourceFactory.createProperty(qb4oInHierarchy);
            StmtIterator iter4 = model.listStatements(subject, resInHier, (RDFNode) null);
            List<String> listHier = new ArrayList<String>();
            RDFNode hierNode = ResourceFactory.createResource();
            while (iter4.hasNext()) {
                hierNode = iter4.nextStatement().getObject();
                String nomHier = hierNode.toString().split("#")[1];
                listHier.add(nomHier);
            }
            System.out.println(listHier.toString());

            //Obtengo la dimension del nivel
            StmtIterator iter2 = model.listStatements(hierNode.asResource(), propDim, (RDFNode) null);
            String nomDim = "";
            if (iter2.hasNext()) {
                Statement dimElem = iter2.nextStatement();
                RDFNode nodoDim = dimElem.getObject();
                String nodoDimString = nodoDim.toString();
                nomDim = nodoDimString.split("#")[1];
            }
            Dimension dimNivel = new Dimension(nomDim);

            Nivel nivelAAgregar = new Nivel(nomNivel, dimNivel, listHier, listAttr, attr_PK, attr_Dsc);
            
            MC.agregarNivel(nivelAAgregar);
        }
        //Obtengo los padres del nivel
        StmtIterator iterPadres = model.listStatements(null, RDF.type, (RDFNode) resLevel);
        while (iterPadres.hasNext()) {
            Statement elemLevel = iterPadres.nextStatement();
            subject = elemLevel.getSubject();
            AgregarNivelesPadre(model,MC ,subject);   
        }
            
    }

    //No se utiliza esta funcion se cargan los niveles en una funcion aparte y no por Jerarquia
    public List<Nivel> obtenerNivelesJerarquia(Model model, manejadorCubos MC, Resource subjectDim, String nomDimension, RDFNode objectJerarquia) {
        Resource subject;
        Property predicate;
        RDFNode object;
        List<Nivel> listaNiveles = new ArrayList<Nivel>();
        Property resInDim = ResourceFactory.createProperty(qb4oInDimension);
        StmtIterator iter3 = model.listStatements(null, resInDim, (RDFNode) subjectDim);
        while (iter3.hasNext()) {

            Statement elemDim = iter3.nextStatement();
            subject = elemDim.getSubject();
            predicate = elemDim.getPredicate();
            object = elemDim.getObject();
            printTriple(subject, predicate, object);

            //Si el nivel tiene la jerarquia que estoy parseando lo agrego
            Property resHasHier = ResourceFactory.createProperty(qb4ohasHierarchy);
            Statement stmtAux = ResourceFactory.createStatement(subject, resHasHier, objectJerarquia);
            if (model.contains(stmtAux)) {
                //Obtengo el nombre del nivel como el String despues del # en la URI
                String nombreNivel = subject.toString().split("#")[1];
                String nombreJerarquia = objectJerarquia.toString().split("#")[1];
                Dimension dimAux = new Dimension(nomDimension);
                Nivel nivelAAgregar = new Nivel(nombreNivel, dimAux);
                listaNiveles.add(nivelAAgregar);
            }

        }
        return listaNiveles;
    }

    public Dimension obtenerJerarquiasDimension(Model model, manejadorCubos MC, Resource subjectDim, String nomDimension) {

        Resource subject;
        Property predicate;
        RDFNode object;
        Property resHasHier = ResourceFactory.createProperty(qb4ohasHierarchy);
        StmtIterator iter2 = model.listStatements(subjectDim, resHasHier, (RDFNode) null);
        List<Jerarquia> listaJerarquias = new ArrayList<Jerarquia>();
        while (iter2.hasNext()) {
            Statement elemDim = iter2.nextStatement();
            subject = elemDim.getSubject();
            predicate = elemDim.getPredicate();
            object = elemDim.getObject();
            printTriple(subject, predicate, object);
            //Obtengo el nombre de la Jerarquia como el String despues del # en la URI
            String nombreJerarquia = subject.toString().split("#")[1];
            //Obtengo los niveles de la jerarquia
            List<Nivel> listaNiveles_Jerarquia = obtenerNivelesJerarquia(model, MC, subjectDim, nomDimension, object);
            //Creo la Jerarquia y la agrego al manejador de cubos
            Jerarquia jerarquiaAgregar = new Jerarquia(nombreJerarquia, nomDimension);
            MC.agregarJerarquia(jerarquiaAgregar);
            listaJerarquias.add(jerarquiaAgregar);
        }
        Dimension dimActual = new Dimension(nomDimension);
        //dimActual.setNivelesMiembro(listaNiveles);
        dimActual.setJerarquiasMiembro(listaJerarquias);
        return dimActual;
    }

    public void ObtenerDimensiones(Model model, manejadorCubos MC) {

        Resource resAux = ResourceFactory.createResource(qbDimension);
        StmtIterator iter = model.listStatements(null, RDF.type, (RDFNode) resAux);
        while (iter.hasNext()) {
            Statement dim = iter.nextStatement();
            Resource subject = dim.getSubject();
            Property predicate = dim.getPredicate();
            RDFNode object = dim.getObject();
            printTriple(subject, predicate, object);
            String nomDimension = subject.toString().split("#")[1];

            //Obtengo las jerarquias definidas en la dimension
            Dimension dimAgregar = obtenerJerarquiasDimension(model, MC, subject, nomDimension);
            MC.agregarDimension(dimAgregar);

        }
        //Obtengo los nievles
        obtenerNiveles(model,MC);
        //Asocio a cada una de las dimensiones
        for (Dimension dim : MC.getDimensiones().values()) {
            List<Nivel> listaNiveles = new ArrayList<Nivel>();
            for (Nivel nivel : MC.getNiveles().values()) 
                if(nivel.getDimension().getNombre().equals(dim.getNombre()))
                    listaNiveles.add(nivel);
            dim.setNivelesMiembro(listaNiveles);
        }
        
    }

    public void ObtenerAtributos(Model model, manejadorCubos MC) {
        Resource resAttribute = ResourceFactory.createResource(qbAttribute);
        StmtIterator iter = model.listStatements(null, RDF.type, (RDFNode) resAttribute);
        while (iter.hasNext()) {
            Statement attr = iter.nextStatement();
            Resource subject = attr.getSubject();
            Property predicate = attr.getPredicate();
            RDFNode object = attr.getObject();
            printTriple(subject, predicate, object);
            String nomAtributo = subject.toString().split("#")[1];

            //Obtengo las jerarquias definidas en la dimension
            Atributo atributoAgregar = new Atributo(nomAtributo);
            MC.agregarAtributo(atributoAgregar);
        }
    }

    public void ObtenerMedidas(Model model, manejadorCubos MC) {
        Resource resMeasure = ResourceFactory.createResource(qbMeasure);
        Property dbmapmeasurecolumn= ResourceFactory.createProperty(dbmapMeasureColumn);
        Property rdfsLabelPred = ResourceFactory.createProperty(rdfsLabel);
        medida medidaAgregar = null;
        StmtIterator iter = model.listStatements(null, RDF.type, (RDFNode) resMeasure);
        while (iter.hasNext()) {
            Statement med = iter.nextStatement();
            Resource subject = med.getSubject();
            Property predicate = med.getPredicate();
            RDFNode object = med.getObject();
            printTriple(subject, predicate, object);
            String nomMedida = subject.toString().split("#")[1];
            medidaAgregar = new medida(nomMedida);
            //obtengo columna de la medida
            StmtIterator iter2 = model.listStatements(subject, dbmapmeasurecolumn, (RDFNode) null);
            while (iter2.hasNext()) {
                Statement st = iter2.nextStatement();
                StmtIterator iter3 = model.listStatements(st.getObject().asResource(), rdfsLabelPred, (RDFNode) null);
                while (iter3.hasNext()) {
                    Statement st2 = iter3.nextStatement();
                    medidaAgregar.setColumna_Fact_Table(st2.getObject().toString());
                }
            }
            
            MC.agregarMedida(medidaAgregar);
        }
    }

    public void ObtenerCubos(Model model, manejadorCubos MC) {
        Statement cubo;
        Resource subject;
        Property predicate;
        RDFNode object;
        Resource resCube = ResourceFactory.createResource(qbDataStructureDefinition);
        Property propLevelColumn = ResourceFactory.createProperty(dbmapLevelColumn);
        Property resLevel = ResourceFactory.createProperty(qb4oLevelDef);
        Property resHasSource = ResourceFactory.createProperty(dbmaphasSource);
        Property resCubeTable = ResourceFactory.createProperty(dbmapCubeTable);
        Property resLabel = ResourceFactory.createProperty(rdfsLabel);
        StmtIterator iter = model.listStatements(null, RDF.type, (RDFNode) resCube);
        while (iter.hasNext()) {
            cubo = iter.nextStatement();
            subject = cubo.getSubject();
            predicate = cubo.getPredicate();
            object = cubo.getObject();
            printTriple(subject, predicate, object);
            String nomTabla = "";
            String nomCubo = subject.toString().split("#")[1];

            //Inicializo el conjunto de columnas y dimensiones
            HashMap<String, String> dimension_Columna = new HashMap<String, String>();

            //Obtener la tabla que esta asociada al cubo
            StmtIterator iterTable = model.listStatements(subject, resCubeTable, (RDFNode) null);
            while (iterTable.hasNext()) {
                RDFNode tablaStmt = iterTable.nextStatement().getObject();
                StmtIterator iterlabelTabla = model.listStatements(tablaStmt.asResource(), resLabel, (RDFNode) null);
                while (iterlabelTabla.hasNext()) {
                    nomTabla = iterlabelTabla.nextStatement().getObject().toString();
                }
            }

            //Obtener elementos del cubo
            Property resComponent = ResourceFactory.createProperty(qbComponent);
            StmtIterator iter1 = model.listStatements(subject, resComponent, (RDFNode) null);
            HashMap<String, String> nivelesCubo = new HashMap<String, String>();
            List<String> medidasCubo = new ArrayList<String>();
            //Para cada uno de los componentes del cubo busco si es definicion de medida o de Nivel
            while (iter1.hasNext()) {
                cubo = iter1.nextStatement();
                subject = cubo.getSubject();
                predicate = cubo.getPredicate();
                object = cubo.getObject();
                printTriple(subject, predicate, object);

                //Obtengo las definiciones de niveles del Cubo
                StmtIterator iter2 = model.listStatements(object.asResource(), resLevel, (RDFNode) null);
                if (iter2.hasNext()) {
                    Statement levelDef = iter2.nextStatement();
                    RDFNode objectLev = levelDef.getObject();
                    String nomNivel = objectLev.toString().split("#")[1];
                    nivelesCubo.put(nomNivel, nomNivel);
                    String nomDim = MC.getNiveles().get(nomNivel).getDimension().getNombre();
                    //Para cada uno de los niveles obtengo la columna de la FactTable atraves del atributo FK
                    StmtIterator iterFK = model.listStatements(objectLev.asResource(), propLevelColumn, (RDFNode) null);
                    if (iterFK.hasNext()) {
                        Statement fkDef = iterFK.nextStatement();
                        RDFNode objectFK = fkDef.getObject();
                        String columnaFK = "";
                        StmtIterator iterFK2 = model.listStatements(objectFK.asResource(), resLabel, (RDFNode) null);
                        while (iterFK2.hasNext()) {
                            Statement statement = iterFK2.next();
                            columnaFK = statement.getObject().toString();
                        }
                        
                        dimension_Columna.put(nomDim, columnaFK);
                    }

                }

                //Obtengo las definiciones de medidas del Cubo
                Property resMeasure = ResourceFactory.createProperty(qbMeasureDef);
                StmtIterator iter3 = model.listStatements(object.asResource(), resMeasure, (RDFNode) null);
                if (iter3.hasNext()) {
                    Statement MedidaDef = iter3.nextStatement();
                    Object objectMes = MedidaDef.getObject();
                    String nomMedida = objectMes.toString().split("#")[1];
                    Property resAggrFun = ResourceFactory.createProperty(qb4oaggregateFunction);
                    StmtIterator iter4 = model.listStatements(object.asResource(), resAggrFun, (RDFNode) null);
                    Statement aggrSt = iter4.nextStatement();
                    Object objectAgg = aggrSt.getObject();
                    String aggrMedida = objectAgg.toString().split("#")[1];
                    String medida = nomMedida + "-" + aggrMedida;
                    medidasCubo.add(medida);
                }

                
            }
            //Creo el cubo y lo agrego
            cubo cuboAgregar = new cubo(medidasCubo, nivelesCubo, nomCubo,nomTabla);
            cuboAgregar.setDimension_Columna(dimension_Columna);
            MC.agregarCubo(cuboAgregar);
        }
    }

    
}
