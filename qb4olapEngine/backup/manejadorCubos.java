package Clases;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class manejadorCubos {

    private static manejadorCubos instance = null;

    private HashMap<String, Nivel> niveles;
    private HashMap<String, Dimension> dimensiones;
    private HashMap<String, medida> medidas;
    private HashMap<String, cubo> cubos;
    private HashMap<String, inline_Tables> inline_Tables;
    private HashMap<String, Jerarquia> jerarquias;
    private HashMap<String, Atributo> atributos;
    private String URI_Usuario = "http://www.fing.edu.uy/inco/cubes/hogaresINE";
    //Se guarda la url ingresada por el usuario en la cual se van a cargar las
    //triplas

    public manejadorCubos() {
        medidas = new HashMap<String, medida>();
        dimensiones = new HashMap<String, Dimension>();
        niveles = new HashMap<String, Nivel>();
        cubos = new HashMap<String, cubo>();
        inline_Tables = new HashMap<String, inline_Tables>();
        jerarquias = new HashMap<String, Jerarquia>();
        atributos = new HashMap<String, Atributo>();
    }

    public static manejadorCubos getInstance() {
        if (instance == null) {
            instance = new manejadorCubos();
        }
        return instance;
    }

    public boolean existeMedida(String nombre) {
        //medida med = new medida(nombre);
        return medidas.containsKey(nombre);
    }

    public void agregarMedida(medida m) {
        medidas.put(m.getNombre(), m);
    }

    public void agregarDimension(Dimension d) {
        dimensiones.put(d.getNombre(), d);
    }

    public void agregarNivel(Nivel n) {
        niveles.put(n.getNombre(), n);
    }

    public void agregarCubo(cubo c) {
        cubos.put(c.getNombre(), c);
    }

    public void agregarInlineTable(inline_Tables t) {
        inline_Tables.put(t.getAlias(), t);
    }

    public void agregarJerarquia(Jerarquia j) {
        jerarquias.put(j.getNombre(), j);
    }

    public void agregarAtributo(Atributo a) {
        atributos.put(a.getNombre(), a);
    }

    public String getUrl_usuario() {
        return URI_Usuario;
    }

    public void setUrl_usuario(String url_usuario) {
        this.URI_Usuario = url_usuario;
    }

    public Nivel obtnerNivel(String nombre) {
        return niveles.get(nombre);
    }

    public Dimension obtenerDimension(String nombre) {
        return dimensiones.get(nombre);
    }

    public boolean existeNivel(String nombre) {
        return niveles.containsKey(nombre);
    }

    public Nivel modificarNivel(String nombre, Nivel otroPadre, String nomJerarquia) {

        //Agrego nuevo padre a nivel existente
        Nivel result = new Nivel();
        Iterator<Nivel> iterador = this.niveles.values().iterator();
        while (iterador.hasNext()) {
            Nivel nivelActual = iterador.next();
            if (nivelActual.getNombre().equals(nombre)) {
                result = nivelActual;
            }
        }
        niveles.remove(nombre);
        result.agregarPadre(otroPadre);

        //Agrego una nueva jerarquia al nivel existente
        result.agregarJerarquia(nomJerarquia);

        niveles.put(nombre, result);
        return result;
    }
    
    public void agregarNivelADimension(Nivel niv){
        
    }

    /////////////////////////////////////////////////////
    //Funciones para la impresion de los componentes
    /////////////////////////////////////////////////////
    private void imprimirPrefijos(PrintWriter pw) {
        pw.println("@prefix qb4o: <http://prefix.cc/qb4o#>.\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n"
                + "@prefix qb: <http://purl.org/linked-data/cube#>.\n"
                + "@prefix dims: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/dimensions#> .\n"
                + "@prefix measures: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/measures#> .\n"
                + "@prefix hier: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/hierarchies#> .\n"
                + "@prefix cube: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/cubes#> .\n"
                + "@prefix att: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/attributes#> .");
    }

    private void imprimirNivel(PrintWriter pw, Nivel niv) {
        pw.println(ManejadorURI.obtenerURINivel(niv.getNombre()) + " a " + "qb4o:LevelProperty;");
        pw.println("rdfs:label \"" + niv.getNombre() + "\"@es;");
        //pw.println("qb4o:inDimension " + ManejadorURI.obtenerURIDimension(URI_Usuario, niv.getDimension().getNombre()) + ";");
        for (Atributo attr : niv.getAtributos()) {
            pw.println("qb4o:hasAtribute " + ManejadorURI.obtenerURIAtributo(attr.getNombre()) + ";");
        }
        //Defino las jerarquias a la que pertenece el nivel
        Iterator<String> itJerarquias = niv.getJerarquias().iterator();
        while (itJerarquias.hasNext()) {
            String jerarquiaActual = itJerarquias.next();
            if (itJerarquias.hasNext()) {
                pw.println("qb4o:inHierarchy " + ManejadorURI.obtenerURIJerarquia(jerarquiaActual) + ";");
            } else {
                pw.println("qb4o:inHierarchy " + ManejadorURI.obtenerURIJerarquia(jerarquiaActual) + ".");
            }
        }

    }

    private void imprimirDefJerarquia(PrintWriter pw, Jerarquia jerarquiaDefActual) {

        pw.println(ManejadorURI.obtenerURIJerarquia(jerarquiaDefActual.getNombre()) + " a " + "qb4o:HierarchyProperty;");
        pw.println("rdfs:label \"" + jerarquiaDefActual.getNombre() + "\"@es;");
        pw.println("qb4o:inDimension " + ManejadorURI.obtenerURIDimension(jerarquiaDefActual.getDimension()) + ";");
        Iterator<Nivel> it = jerarquiaDefActual.getNivelesMiembro().iterator();
        String nivelesAux = "";
        while (it.hasNext()) {
            Nivel nivelActual = it.next();
            if (it.hasNext()) {
                nivelesAux = nivelesAux + ManejadorURI.obtenerURINivel(nivelActual.getNombre()) + ",";
            } else {
                nivelesAux = nivelesAux + ManejadorURI.obtenerURINivel(nivelActual.getNombre()) + ".";
            }
        }
        pw.println("qb4o:hasLevel" + nivelesAux);
        pw.println();
    }

    private void imprimirDetalleJerarquia(PrintWriter pw, Jerarquia jerarquiaDetalleActual) {
        String aux1, aux3, aux5;
        Iterator<Nivel> iterador = jerarquiaDetalleActual.getNivelesMiembro().iterator();
        String aux2 = " a qb4o:LevelInHierarchy ; qb4o:levelComponent ";
        String aux4 = " ; qb4o:hierarchyComponent ";
        while (iterador.hasNext()) {
            Nivel nivelActual = iterador.next();
            aux1 = ManejadorURI.obtenerURINodoBlancolevels(jerarquiaDetalleActual.getNombre(), nivelActual.getNombre());
            aux3 = ManejadorURI.obtenerURINivel(nivelActual.getNombre());
            aux5 = ManejadorURI.obtenerURIJerarquia(jerarquiaDetalleActual.getNombre());
            pw.println(aux1 + aux2 + aux3 + aux4 + aux5 + ".");
        }

        aux2 = " a qb4o:HierarchyStep; qb4o:childLevel ";
        aux4 = " ; qb4o:parentLevel ";
        String aux6 = " ; qb4o:cardinality qb4o:OneToMany.";
        for (Nivel niv : jerarquiaDetalleActual.getNivelesMiembro()) {
            for (Nivel nivPadre : niv.getNivelesPadre().values()) {
                aux1 = ManejadorURI.obtenerURINodoBlancoStep(niv.getNombre(), nivPadre.getNombre());
                aux3 = ManejadorURI.obtenerURINodoBlancolevels(jerarquiaDetalleActual.getNombre(), niv.getNombre());
                aux5 = ManejadorURI.obtenerURINodoBlancolevels(jerarquiaDetalleActual.getNombre(), nivPadre.getNombre());
                pw.println(aux1 + aux2 + aux3 + aux4 + aux5 + aux6);
            }
        }

    }

    private void imprimirDimensiones(PrintWriter pw) {

        pw.println();
        pw.println("#DIMENSIONES");
        pw.println();
        Iterator<Dimension> iterador = this.dimensiones.values().iterator();
        while (iterador.hasNext()) {
            Dimension dimensionActual = iterador.next();
            pw.println(ManejadorURI.obtenerURIDimension(dimensionActual.getNombre()) + " a " + "qb:DimensionProperty;");
            pw.println("rdfs:label \"" + dimensionActual.getNombre() + "\"@es;");

            //Imprimo las referencias a las Jerarquias
            Iterator<Jerarquia> iteradorJerarquias = dimensionActual.getJerarquiasMiembro().iterator();
            while (iteradorJerarquias.hasNext()) {
                Jerarquia jerarquiaActual = iteradorJerarquias.next();
                if (iteradorJerarquias.hasNext()) {
                    pw.println("qb4o:hasHierarchy " + ManejadorURI.obtenerURIJerarquia(jerarquiaActual.getNombre()) + ";");
                } else {
                    pw.println("qb4o:hasHierarchy " + ManejadorURI.obtenerURIJerarquia(jerarquiaActual.getNombre()) + ".");
                    pw.println();
                }
            }

            //Imprimo la definicion de las jerarquias
            Iterator<Jerarquia> iteradorDefJerarquias = dimensionActual.getJerarquiasMiembro().iterator();
            while (iteradorDefJerarquias.hasNext()) {
                Jerarquia jerarquiaDefActual = iteradorDefJerarquias.next();
                imprimirDefJerarquia(pw, jerarquiaDefActual);
            }

            //Imprimo la definicion de los niveles
            Iterator<Nivel> iteradorNiveles = this.niveles.values().iterator();
            while (iteradorNiveles.hasNext()) {
                Nivel nivelActual = iteradorNiveles.next();
                if (nivelActual.getDimension().getNombre().equals(dimensionActual.getNombre())) {
                    imprimirNivel(pw, nivelActual);
                    pw.println();
                }
            }

            //Imprimo el detalle de la Jerarquia: Components y Steps
            Iterator<Jerarquia> iteradorDetalleJerarquias = dimensionActual.getJerarquiasMiembro().iterator();
            while (iteradorDetalleJerarquias.hasNext()) {
                Jerarquia jerarquiaDetalleActual = iteradorDetalleJerarquias.next();
                imprimirDetalleJerarquia(pw, jerarquiaDetalleActual);
            }

            pw.println("#-------------------------------------------------------------------");
            pw.println();
        }
    }

    private void imprimirMedidas(PrintWriter pw) {

        pw.println();
        pw.println("#MEDIDAS");
        pw.println();
        Iterator<medida> iterador = this.medidas.values().iterator();
        while (iterador.hasNext()) {
            medida medidaActual = iterador.next();
            pw.println(ManejadorURI.obtenerURIMedida(medidaActual.getNombre()) + " a qb:MeasureProperty;");
            pw.println("rdfs:label \"" + medidaActual.getNombre() + "\"@es.");

        }
        pw.println();
    }

    private void imprimirAtributos(PrintWriter pw) {
        Iterator<Atributo> iterAttr = atributos.values().iterator();
        while (iterAttr.hasNext()) {
            Atributo attr = iterAttr.next();
            pw.println(ManejadorURI.obtenerURIAtributo(attr.getNombre()) + " a qb:AttrubuteProperty;");
            pw.println("rdfs:label \"" + attr.getNombre() + "\"@es");
        }
    }

    private void imprimirCubos(PrintWriter pw) {

        pw.println();
        pw.println("#CUBOS");
        pw.println();

        Iterator<cubo> iterador = this.cubos.values().iterator();
        while (iterador.hasNext()) {
            cubo cuboActual = iterador.next();
            pw.println(ManejadorURI.obtenerURICubo(cuboActual.getNombre()) + " a qb:DataStructureDeﬁnition;");
            pw.println("rdfs:label \"" + cuboActual.getNombre() + "\"@es;");
            Iterator<String> iteradorNiveles = cuboActual.getNiveles().values().iterator();
            while (iteradorNiveles.hasNext()) {
                String nivelActual = iteradorNiveles.next();
                pw.println("qb:component [qb4o:level " + ManejadorURI.obtenerURINivel(nivelActual) + " ; qb4o:cardinality qb4o:ManyToOne];");
            }

            Iterator<String> iteradorMedidas = cuboActual.getMedidasAgregacion().iterator();
            while (iteradorMedidas.hasNext()) {
                String medidaActual = iteradorMedidas.next();
                String[] medAgreg = medidaActual.split("-");
                if (iteradorMedidas.hasNext()) {
                    pw.println("qb:component [qb:measure" + ManejadorURI.obtenerURIMedida(medAgreg[0]) + "; qb4o:hasAggregateFunction qb4o:" + medAgreg[1] + "];");
                } else {
                    pw.println("qb:component [qb:measure" + ManejadorURI.obtenerURIMedida(medAgreg[0]) + "; qb4o:hasAggregateFunction qb4o:" + medAgreg[1] + "].");
                }
            }

            pw.println();
            pw.println("#DATASET");
            pw.println();
            //Para la impresion del dataSet tomo el nombre del cubo
            pw.println(ManejadorURI.obtenerURIDataSet("dataset_" + cuboActual.getNombre()) + " a qb:DataSet;");
            pw.println("rdfs:label \"DataSet " + cuboActual.getNombre() + "\"@es;");
            pw.println("qb:structure eg:SituacionHogares.");

            pw.println();
            pw.println("#-------------------------------------------------------------------");
            pw.println();
        }
    }

 private void imprimirR2RMLPrefijos(PrintWriter pw){
        pw.println("@prefix rr: <http://www.w3.org/ns/r2rml#>.");
        pw.println("@prefix qb4o: <http://purl.org/qb4olap/cubes#>.");
        pw.println("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.");
        pw.println("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.");
        pw.println("@prefix qb: <http://purl.org/linked-data/cube#>.");
        pw.println("@prefix skos: <http://www.w3.org/2004/02/skos/core#>.");
        pw.println("@prefix dim: <http://www.fing.edu.uy/inco/cubes/hogaresINE/dsd/dimensions#>.");
    }
   private void imprimirR2RMLInstancias(PrintWriter pw){
        
        pw.println("#MAPEOS DE INSTANCIAS R2RML");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <Dimension> iterador = this.dimensiones.values().iterator();
        while (iterador.hasNext()) {
            Dimension dimensionActual = iterador.next();
            Iterator <Nivel> iteradorNiveles = this.niveles.values().iterator();
            while (iteradorNiveles.hasNext()){
                Nivel nivelActual = iteradorNiveles.next();
                if (nivelActual.getDimension().getNombre().equals(dimensionActual.getNombre())){
                    //si la tabla que tiene es inline
                    if(inline_Tables.containsKey(nivelActual.getTabla())){
                        imprimirR2RMLInstanciasInlineTable(pw,nivelActual);
                        pw.println("<#"+ nivelActual.getNombre()+"Map>");
                        pw.println("rr:logicalTable <#"+ nivelActual.getTabla()+"InlineTableView>;");
                    }else{
//                        pw.println(" <#"+ nivelActual.getNombre()+"TableView> rr:sqlQuery \"\"\"");
//                        pw.println(getQueryNivelColumn(nivelActual)+ "FROM "+ nivelActual.getTabla());
//                        pw.println("\"\"\".");
                        pw.println("<#"+ nivelActual.getNombre()+"Map>");
                        pw.println("rr:logicalTable  [ rr:tableName \""+nivelActual.getTabla()+"\" ];");
                    }
                    pw.println("rr:subjectMap [rr:template \""+ManejadorURI.obtenerURIInstanciaDimension(URI_Usuario, dimensionActual, nivelActual) +"\"];");        
                    pw.println("rr:predicateObjectMap [rr:predicate qb4o:inLevel;");
                    pw.println("rr:object <"+ManejadorURI.obtenerURINivel(URI_Usuario,nivelActual.getNombre())+">];");  
                     //cargo padres
                    for (Nivel nivPadre : nivelActual.getNivelesPadre().values()) {
                        pw.println("rr:predicateObjectMap [ rr:predicate skos:broader; rr:objectMap [ rr:termType rr:IRI ;");
                        pw.println("rr:template \""+ ManejadorURI.obtenerURIInstanciaDimension(URI_Usuario, dimensionActual, nivPadre) +"\" ] ;] ;");
                    }
                    //labels
                    pw.println("rr:predicateObjectMap [rr:predicate rdfs:label;rr:objectMap[ rr:column \""+nivelActual.getNameColumn()+"\" ];");
                   //cargo atributos
                    Iterator<Atributo> iterAttr = nivelActual.getAtributos().iterator();
                    //caso que no tenga atributos
                    if (!iterAttr.hasNext()) pw.println("]."); else pw.println("];");
                    //sino
                    while (iterAttr.hasNext()) {
                        Atributo attr = iterAttr.next();
                        pw.println("rr:predicateObjectMap [rr:predicate <"+ManejadorURI.obtenerURIAtributo(URI_Usuario, attr.getNombre())+">;");
                        pw.println("rr:objectMap [rr:template \"{"+attr.getColumna()+"}\"];");
                        if (!iterAttr.hasNext()) pw.println("].");
                        else pw.println("];");
                    }
                    
                  
                }
            }
            
        }
    pw.println("#FIN");
    pw.println();

    }
   
 

   
     private void imprimirR2RMLInstanciasInlineTable(PrintWriter pw,Nivel niv){
         inline_Tables table = inline_Tables.get(niv.getTabla());
         int cantCol = table.getNombreColumna_tipo().size();
         HashMap<Integer,String> columnas = table.getNombreColumna_tipo();
         String query = "SELECT * FROM (";
         for (String row : table.getValores_tabla()) {
             String[] valores = row.split(",");
             query += "( SELECT ";
             for(int i=1;i<=cantCol;i++){
                query += "'" + valores[i-1]+"' AS "+columnas.get(i);
                if (i<cantCol)
                    query += ",";
             }
             query += ") UNION ";
         }
         query = query.substring(0, query.length() - 6);
         query += ") AS ValueTable; ";      

          pw.println(" <#"+ table.getAlias()+"InlineTableView> rr:sqlQuery \"\"\"");
          pw.println(query);
          pw.println("\"\"\".");
        
        
     }
     
     private void imprimirR2RMLHechos(PrintWriter pw){
        pw.println("#MAPEOS DE HECHOS R2RML");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <cubo> cubos = this.cubos.values().iterator();
        while (cubos.hasNext()) {
            cubo cuboActual = cubos.next();
            pw.println("<#Fact"+cuboActual.getNombre()+"Map>");
            pw.println("rr:logicalTable [ rr:tableName \""+cuboActual.getTabla()+"\" ];");
            pw.println(" rr:subjectMap [");
            //el get tabla ese esta para el ojete hay que solucionar el tema del autonumber
            pw.print("rr:template \""+ ManejadorURI.obtenerURIHecho(URI_Usuario, cuboActual.getNombre(), "NUMERO") +"\""); 
            pw.println("];");
             
            pw.println("rr:predicateObjectMap [");
            pw.println("rr:predicate rdf:type;");
            //pw.println("rr:objectMap [rr:constant \"http://purl.org/linked-data/cube#Observation\"]];");
            pw.println("rr:object <http://purl.org/linked-data/cube#Observation>];");
            //pw.println(" ];");
            
            HashMap<String, String> columnas = cuboActual.getDimension_Columna();
            Set<Entry<String,String>> map =columnas.entrySet();
            for (Entry<String, String> entry : map) {
                pw.println("rr:predicateObjectMap [");
                Dimension dim = dimensiones.get(entry.getKey());
                pw.println("rr:predicate "+ManejadorURI.obtenerURINivelPrefijo(URI_Usuario, dim.getMenorNivel())+";");
                //pw.println("rr:objectMap [rr:template \""+ManejadorURI.obtenerURIHecho(URI_Usuario,cuboActual.getNombre(), entry.getValue())+"\"];"); 
                pw.println("rr:objectMap [rr:template \""+ManejadorURI.obtenerURIInstanciaDimension(URI_Usuario, dim, dim.getMenorNivel2())+"\"];"); 
                pw.println(" ];");
            }
            
            //medidas
            Iterator<String> strIterator = cuboActual.getMedidasAgregacion().iterator();
            while (strIterator.hasNext()) {
                String strmedida= strIterator.next();
                strmedida=strmedida.split("-")[0];
                medida medida = medidas.get(strmedida);
                pw.println("rr:predicateObjectMap [");
                pw.println("rr:predicate <"+ManejadorURI.obtenerURIMedida(URI_Usuario, strmedida)+">;");
                // ESTO ACA ESTA MAL PERO HAY QUE HACER UN MODULO PARA CENTRALIZACION DE LAS URI
                pw.println("rr:objectMap [rr:column \""+medida.getColumna_Fact_Table()+"\"];"); 
                if(strIterator.hasNext())
                    pw.println(" ];");
                else
                    pw.println(" ].");
            }
        }
    pw.println("#FIN");
    pw.println();

    }
     //esto anda si justo el menor nivel coincide con la foreignkey
    private String obtenerNombreNivelPorColumna(String columna){
        Iterator <Nivel> iterador = this.niveles.values().iterator();
        boolean encontre = false;
        String aretornar = "";
        while (iterador.hasNext() && !(encontre)) {
            Nivel nivel = iterador.next();
            if (nivel.getColumna().equals(columna))
                    aretornar = nivel.getNombre();
        }
        return aretornar;
    }

     private void imprimirInstanciasDirecto(PrintWriter pw) throws SQLException{
        
        pw.println("#INSTANCIAS DE DIMENSION");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <Dimension> iterador = this.dimensiones.values().iterator();
        while (iterador.hasNext()) {
            Dimension dimensionActual = iterador.next();
            Iterator <Nivel> iteradorNiveles = this.niveles.values().iterator();
            while (iteradorNiveles.hasNext()){
                Nivel nivelActual = iteradorNiveles.next();
                if (nivelActual.getDimension().getNombre().equals(dimensionActual.getNombre())){
                    JDBC jdbc = new JDBC();
                    Connection con = jdbc.getConnection();
                    Statement st = con.createStatement();
                    ResultSet rs = null;
                    //si la tabla que tiene es inline
                    if(inline_Tables.containsKey(nivelActual.getTabla())){
                        
                    }else{
                        String query = generarConsultaRelacionalDeNivel(nivelActual);
                        rs = st.executeQuery(query);
                        while (!rs.isLast()){
                            rs.next();
                            //Predicado
                            pw.print("<"+ManejadorURI.obtenerURIInstanciaDimensionDirecta(URI_Usuario, dimensionActual, nivelActual,rs.getString(nivelActual.getColumna()))+">");
                            //In Level
                            pw.print(" qb4o:inLevel ");
                            pw.print("<"+ManejadorURI.obtenerURINivel(URI_Usuario,nivelActual.getNombre())+">");
                            pw.println(";");
                            //Broader
                            for (Nivel nivPadre : nivelActual.getNivelesPadre().values()) {
                                pw.print("     skos:broader ");
                                pw.print("<"+ManejadorURI.obtenerURIInstanciaDimensionDirecta(URI_Usuario, dimensionActual, nivPadre,rs.getString(nivPadre.getColumna()))+">");
                                pw.println(";");
                            }
                            //Atributo
                            Iterator<Atributo> iterAttr = nivelActual.getAtributos().iterator();
                            while (iterAttr.hasNext()) {
                                Atributo attr = iterAttr.next();
                                pw.print("     <"+ManejadorURI.obtenerURIAtributo(URI_Usuario, attr.getNombre())+"> ");
                                pw.println("\""+rs.getString(attr.getColumna())+"\";");
                            }
                            //Label
                                pw.print("     rdfs:label ");
                                pw.print("\""+rs.getString(nivelActual.getNameColumn())+"\"");
                                pw.println(".");
                            
                            
                             pw.println();
                        }
                    }
     
                }
            }
            
        }
    pw.println("#FIN");
    pw.println();

    }
    
    private String generarConsultaRelacionalDeNivel(Nivel nivelActual) {
        String query = "";
       //si tienen los mismos valores significa que no tiene name column no hay que repetir en la consulta
       if(nivelActual.getColumna().equals(nivelActual.getNameColumn()))
           query += "SELECT DISTINCT "+ "tabla."+nivelActual.getColumna()+ " ";
       else
           query +=  "SELECT DISTINCT "+"tabla."+ nivelActual.getColumna()+ ", "+ "tabla."+nivelActual.getNameColumn()+ " ";
       //agrego atributos
        Iterator<Atributo> iterAttr = nivelActual.getAtributos().iterator();
        while (iterAttr.hasNext()) {
            Atributo attr = iterAttr.next();
            query+= ", " + "tabla."+attr.getColumna() + " ";
        }
        //Columnas padres
       Collection<Nivel> padres = nivelActual.getNivelesPadre().values();
       for (Nivel nivel : padres) {
           query+= "," + getPadresConComa(nivel) + " ";
       } 
       //Consulto si la tabla es comun o compuesta
       if(nivelActual.getTablaCompuesta()==null)
           query += "FROM " +nivelActual.getTabla()+ " as tabla";
       else{
           //genero la vista
           String view = CreateJoinQuery(nivelActual.getTablaCompuesta());
           if(nivelActual.getColumna().equals(nivelActual.getNameColumn()))
               view += "SELECT "+ getColumnaAliasControl(nivelActual.getTablaCompuesta(), nivelActual.getColumna())+ " ";
           else
               view +=  "SELECT "+getColumnaAliasControl(nivelActual.getTablaCompuesta(), nivelActual.getColumna())+ ", "+ getColumnaAliasControl(nivelActual.getTablaCompuesta(), nivelActual.getNameColumn()) + " ";           //agrego atributos
           iterAttr = nivelActual.getAtributos().iterator();
           while (iterAttr.hasNext()) {
                Atributo attr = iterAttr.next();
                view+= ", " +attr.getColumna() + " ";
           }
           //Columnas padres
           padres = nivelActual.getNivelesPadre().values();
           for (Nivel nivel : padres) {
               view+= "," + getPadresConComaMasAlias(nivel) + " ";
           }
           view += " FROM ";
           Tabla t = nivelActual.getTablaCompuesta();
           view += t.getNombre();
           while(t.getTablaJoinDer()!=null){
               t = t.getTablaJoinDer();
               view += "," + t.getNombre() + " ";
           }
           view += "WHERE ";
           t = nivelActual.getTablaCompuesta();
           Tabla aux= null;
           while(t.getTablaJoinDer()!=null){
               aux=t;
               t = t.getTablaJoinDer();
               view += " " + aux.getNombre() + "." + aux.getColumnaJoinDer() + " = "+ t.getNombre() + "." + t.getColumnaJoinIzq() + " AND";
           }
           view = view.substring(0, view.length()-4);
           //genero la query
           query +=  "FROM ("+ view + ") as tabla";
       }
        System.out.println(query);    
       return query;
    }
   
    private String CreateJoinQuery(Source source){
        String view = "";
        if(source.getClass().isInstance(Join.class)){
            Join j= (Join) source;
            
        }
        
        return view;
    }
    private String getColumnaAliasControl(Tabla t, String Columna){
        while(t.getTablaJoinDer()!= null){
            //Si son iguales las columnas tengo que usar el alias
            if (t.getTablaJoinDer().getColumnaJoinIzq().equals(t.getColumnaJoinDer()) && t.getColumnaJoinDer().equals(Columna)){
                return t.getAlias()+"."+Columna;
            }
            t= t.getTablaJoinDer();
        }
        return Columna;
    }
   private String getPadresConComa(Nivel nivelActual){
       Collection<Nivel> padres = nivelActual.getNivelesPadre().values();
        if(padres.isEmpty())
            return "tabla."+nivelActual.getColumna();
        else{
            Iterator<Nivel> iterPadre = padres.iterator();
            String aux = "";
            while(iterPadre.hasNext()){
                Nivel padre = iterPadre.next();
                aux += getPadresConComa(padre) + ",";
            }
            aux += "tabla."+nivelActual.getColumna() + " ";
            return aux;
        }
   }
    
   private String getPadresConComaMasAlias(Nivel nivelActual){
       Collection<Nivel> padres = nivelActual.getNivelesPadre().values();
        if(padres.isEmpty())
            return getColumnaAliasControl(nivelActual.getTablaCompuesta(), nivelActual.getColumna());
        else{
            Iterator<Nivel> iterPadre = padres.iterator();
            String aux = "";
            while(iterPadre.hasNext()){
                Nivel padre = iterPadre.next();
                aux += getPadresConComaMasAlias(padre) + ",";
            }
            aux += getColumnaAliasControl(nivelActual.getTablaCompuesta(), nivelActual.getColumna()) + " ";
            return aux;
        }
   }
   
   private void imprimirHechosDirecto(PrintWriter pw) throws SQLException{
        pw.println("#MAPEOS DE HECHOS");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <cubo> cubos = this.cubos.values().iterator();
        int cont = 1;
        while (cubos.hasNext()) {
            cubo cuboActual = cubos.next();
            JDBC jdbc = new JDBC();
            Connection con = jdbc.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + cuboActual.getTabla());
            while(!rs.isLast()){
                rs.next();
                //sujeto
                pw.print( ManejadorURI.obtenerURIHechoDirecto(URI_Usuario, cuboActual.getNombre(), cont));
                cont++;
                //observacion
                pw.print(" rdf:type ");
                pw.println(" <http://purl.org/linked-data/cube#Observation>; ");
                //dimensiones
                HashMap<String, String> columnas = cuboActual.getDimension_Columna();
                Set<Entry<String,String>> map =columnas.entrySet();
                for (Entry<String, String> entry : map) {
                    Dimension dim = dimensiones.get(entry.getKey());
                    pw.print("     " + ManejadorURI.obtenerURINivelPrefijo(URI_Usuario, dim.getMenorNivel())+" ");
                    Nivel menorNivel = dim.getMenorNivel2();
                    pw.println("<"+ManejadorURI.obtenerURIInstanciaDimensionDirecta(URI_Usuario, dim, menorNivel,rs.getString(entry.getValue()))+">;"); 
                }
                //medidas
                Iterator<String> strIterator = cuboActual.getMedidasAgregacion().iterator();
                while (strIterator.hasNext()) {
                    String strmedida= strIterator.next();
                    medida medida = medidas.get(strmedida.split("-")[0]);
                    pw.print("     <"+ManejadorURI.obtenerURIMedida(URI_Usuario, strmedida.split("-")[0])+"> ");
                    pw.print("\""+rs.getString(medida.getColumna_Fact_Table())+"\""); 
                    if(strIterator.hasNext())
                        pw.println(";");
                    else
                        pw.println(".");
                }    
            }
            
        }
    pw.println("#FIN");
    pw.println();

    }

       public void imprimirRDF()
    {
        FileWriter fichero = null;
        PrintWriter pw = null;
        FileWriter r2rmlFichero = null;
        FileWriter mapeoDirectoFichero = null;
        try
        {
            fichero = new FileWriter("./prueba.txt");
            pw = new PrintWriter(fichero);
            
            //imprimo las dimensiones
            imprimirDimensiones(pw);
            //imprimo las medidas
            imprimirMedidas(pw);
            //imprimo los cubos
            imprimirCubos(pw);
            r2rmlFichero = new FileWriter("./r2rml.txt");
            pw = new PrintWriter(r2rmlFichero);
            imprimirR2RMLPrefijos(pw);
            //imprimo las instancias de dim
            imprimirR2RMLInstancias(pw);
            //imprimo los hechos
            imprimirR2RMLHechos(pw);
           
            mapeoDirectoFichero = new FileWriter("./mapeoDirecto.txt");
            pw = new PrintWriter(mapeoDirectoFichero);
            imprimirR2RMLPrefijos(pw);
            imprimirInstanciasDirecto(pw);
            //imprimirHechosDirecto(pw);
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           if (null != r2rmlFichero)
              r2rmlFichero.close();
           if (null != mapeoDirectoFichero)
              mapeoDirectoFichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }


}
