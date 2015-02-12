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
import java.util.Map.Entry;
import java.util.Set;

//Clase principal del sistema, tiene la resposabilidad de almacenar todas las 
//estructuras necesarias para los mapeos, y la URI_Base con la que el usuario
//contruira sus URIs. Cuenta con operaciones para gerenrar la estructura QB4OLAP
//efectuar la Traduccion Directa y tambien los Mapeos R2RML.
public class manejadorCubos {

    private static manejadorCubos instance = null;

    private HashMap<String, Nivel> niveles;
    private HashMap<String, Dimension> dimensiones;
    private HashMap<String, medida> medidas;
    private HashMap<String, cubo> cubos;
    private HashMap<String, inline_Tables> inline_Tables;
    private HashMap<String, Jerarquia> jerarquias;
    private HashMap<String, Atributo> atributos;
    //ESTO HAY QUE CAMBIARLO
    private String URI_Usuario = "http://www.fing.edu.uy/inco/cubes/hogaresINE";
    
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

        result.agregarJerarquia(nomJerarquia);

        niveles.put(nombre, result);
        return result;
    }
    
    public Nivel agregarColumnaNivel(String nombreNivel, String nomColumna) {

        //Agrego nuevo padre a nivel existente
        Nivel result = new Nivel();
        
        return result;
    }
    
    public void agregarNivelADimension(Nivel niv){
        
    }

    public HashMap<String, Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(HashMap<String, Nivel> niveles) {
        this.niveles = niveles;
    }

    public HashMap<String, medida> getMedidas() {
        return medidas;
    }

    public void setMedidas(HashMap<String, medida> medidas) {
        this.medidas = medidas;
    }

    public HashMap<String, Dimension> getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(HashMap<String, Dimension> dimensiones) {
        this.dimensiones = dimensiones;
    }

    public HashMap<String, cubo> getCubos() {
        return cubos;
    }

    public void setCubos(HashMap<String, cubo> cubos) {
        this.cubos = cubos;
    }

    public HashMap<String, inline_Tables> getInline_Tables() {
        return inline_Tables;
    }

    public void setInline_Tables(HashMap<String, inline_Tables> inline_Tables) {
        this.inline_Tables = inline_Tables;
    }

    public HashMap<String, Jerarquia> getJerarquias() {
        return jerarquias;
    }

    public void setJerarquias(HashMap<String, Jerarquia> jerarquias) {
        this.jerarquias = jerarquias;
    }

    public HashMap<String, Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(HashMap<String, Atributo> atributos) {
        this.atributos = atributos;
    }

    public String getURI_Usuario() {
        return URI_Usuario;
    }

    public void setURI_Usuario(String URI_Usuario) {
        this.URI_Usuario = URI_Usuario;
    }
    
    
    
    /////////////////////////////////////////////////////
    //Funciones para la impresion de los componentes
    /////////////////////////////////////////////////////
    private void imprimirPrefijos(PrintWriter pw) {
        pw.println("@prefix qb4o: <http://purl.org/qb4olap/cubes#>.\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n"
                + "@prefix qb: <http://purl.org/linked-data/cube#>.\n"
                + "@prefix dims: <"+ URI_Usuario +"/dsd/dimensions#> .\n"
                + "@prefix measures: <"+ URI_Usuario +"/dsd/measures#> .\n"
                + "@prefix hier: <"+ URI_Usuario +"/dsd/hierarchies#> .\n"
                + "@prefix cube: <"+ URI_Usuario +"/dsd/cubes#> .\n"
                + "@prefix att: <"+ URI_Usuario +"/dsd/attributes#> .");
    }

    private void imprimirNivel(PrintWriter pw, Nivel niv) {
        pw.println(ManejadorURI.obtenerURINivel(niv.getNombre()) + " a " + "qb4o:LevelProperty;");
        pw.println("rdfs:label \"" + niv.getNombre() + "\"@es;");
        pw.println("qb4o:hasAtribute " + ManejadorURI.obtenerURIAtributo(niv.getAtr_ColumnaNombre().getNombre()) + ";");
        pw.println("qb4o:hasAtribute " + ManejadorURI.obtenerURIAtributo(niv.getAtr_ColumnaPrimaria().getNombre()) + ";");
        for (Atributo attr : niv.getAtributos()) {
            pw.println("qb4o:hasAtribute " + ManejadorURI.obtenerURIAtributo(attr.getNombre()) + ";");
        }
        pw.println("qb4o:hasID " + ManejadorURI.obtenerURIAtributo(niv.getAtr_ColumnaPrimaria().getNombre()) + ";");
        
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
        pw.println("qb4o:hasLevel " + nivelesAux);
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
                if (nivPadre.getJerarquias().contains(jerarquiaDetalleActual.getNombre())){
                    aux1 = ManejadorURI.obtenerURINodoBlancoStep(niv.getNombre(), nivPadre.getNombre(),jerarquiaDetalleActual.getNombre());
                    aux3 = ManejadorURI.obtenerURINodoBlancolevels(jerarquiaDetalleActual.getNombre(), niv.getNombre());
                    aux5 = ManejadorURI.obtenerURINodoBlancolevels(jerarquiaDetalleActual.getNombre(), nivPadre.getNombre());
                    pw.println(aux1 + aux2 + aux3 + aux4 + aux5 + aux6);
                }
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
        pw.println();
        pw.println("#ATRIBUTOS");
        pw.println();
        Iterator<Atributo> iterAttr = atributos.values().iterator();
        while (iterAttr.hasNext()) {
            Atributo attr = iterAttr.next();
            pw.println(ManejadorURI.obtenerURIAtributo(attr.getNombre()) + " a qb:AttrubuteProperty;");
            pw.println("rdfs:label \"" + attr.getNombre() + "\"@es.");
        }
        
        Iterator<Nivel> iterniveles = niveles.values().iterator();
        while (iterniveles.hasNext()) {
            Nivel niv = iterniveles.next();
            pw.println(ManejadorURI.obtenerURIAtributo(niv.getAtr_ColumnaPrimaria().getNombre()) + " a qb:AttrubuteProperty;");
            pw.println("rdfs:label \"" + niv.getAtr_ColumnaPrimaria().getNombre() + "\"@es.");
            pw.println(ManejadorURI.obtenerURIAtributo(niv.getAtr_ColumnaNombre().getNombre()) + " a qb:AttrubuteProperty;");
            pw.println("rdfs:label \"" + niv.getAtr_ColumnaNombre().getNombre() + "\"@es.");
        }
        
    }

    private void imprimirCubos(PrintWriter pw) {

        pw.println();
        pw.println("#CUBOS");
        pw.println();

        Iterator<cubo> iterador = this.cubos.values().iterator();
        while (iterador.hasNext()) {
            cubo cuboActual = iterador.next();
            pw.println(ManejadorURI.obtenerURICubo(cuboActual.getNombre()) + " a qb:DataStructureDefinition;");
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
                    pw.println("qb:component [qb:measure " + ManejadorURI.obtenerURIMedida(medAgreg[0]) + "; qb4o:hasAggregateFunction qb4o:" + medAgreg[1] + "];");
                } else {
                    pw.println("qb:component [qb:measure " + ManejadorURI.obtenerURIMedida(medAgreg[0]) + "; qb4o:hasAggregateFunction qb4o:" + medAgreg[1] + "].");
                }
            }

            pw.println();
            pw.println("#DATASET");
            pw.println();
            //Para la impresion del dataSet tomo el nombre del cubo
            pw.println(ManejadorURI.obtenerURIDataSet("dataset_" + cuboActual.getNombre()) + " a qb:DataSet;");
            pw.println("rdfs:label \"DataSet " + cuboActual.getNombre() + "\"@es;");
            pw.println("qb:structure " + ManejadorURI.obtenerURICubo(cuboActual.getNombre()) + ".");

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
        pw.println("@prefix dim: <"+ URI_Usuario +"/dsd/dimensions#>.");
        pw.println("@prefix cube: <"+ URI_Usuario +"/dsd/cubes#> .");
    }
   private void imprimirR2RMLInstancias(PrintWriter pw){
        
        pw.println("#MAPEOS DE INSTANCIAS R2RML");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <Dimension> iterador = this.dimensiones.values().iterator();
        while (iterador.hasNext()) {
            Dimension dimensionActual = iterador.next();
            System.out.println("Generando Dimension " + dimensionActual.getNombre() + "...");
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
                        pw.println(" <#"+ nivelActual.getNombre()+"TableView> rr:sqlQuery \"\"\"");
                        pw.println(generarConsultaRelacionalDeNivel(nivelActual));
                        pw.println("\"\"\".");
                        pw.println("<#"+ nivelActual.getNombre()+"Map>");
//                        pw.println("rr:logicalTable  [ rr:tableName \""+nivelActual.getTabla()+"\" ];");
                        pw.println("rr:logicalTable  <#"+ nivelActual.getNombre()+"TableView>;");
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
                    System.out.println("Se genero correctamente el Nivel " + nivelActual.getNombre() + ".");
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
     
     private String generarQueryR2RMLHechos(cubo cuboActual){
         String query = "";
         query += "SET @varnum=0; SELECT @varnum:=@varnum+1 AS varnum, "+cuboActual.getTabla()+".* FROM "+cuboActual.getTabla();
         return query;
     }
     private void imprimirR2RMLHechos(PrintWriter pw){
        pw.println("#MAPEOS DE HECHOS R2RML");
        pw.println("#--------------------------");
        //Iteracion sobre Dimensiones
        Iterator <cubo> cubos = this.cubos.values().iterator();
        while (cubos.hasNext()) {
            cubo cuboActual = cubos.next();
//            pw.println(" <#"+ cuboActual.getNombre()+"TableView> rr:sqlQuery \"\"\"");
//            pw.println(generarQueryR2RMLHechos(cuboActual));
//            pw.println("\"\"\".");
            pw.println("<#Fact"+cuboActual.getNombre()+"Map>");
            pw.println("rr:logicalTable [ rr:tableName \""+cuboActual.getTabla()+"\" ];");
//            pw.println("rr:logicalTable <#"+ cuboActual.getNombre()+"TableView> ;");
            pw.println(" rr:subjectMap [");
            
            pw.print("rr:template \""+ ManejadorURI.obtenerURIHecho(URI_Usuario, cuboActual) +"\""); 
            pw.println("];");
             
            pw.println("rr:predicateObjectMap [");
            pw.println("rr:predicate rdf:type;");
            pw.println("rr:object <http://purl.org/linked-data/cube#Observation>];");
            //DataSet
            pw.println("rr:predicateObjectMap [ rr:predicate qb:dataSet ; rr:object <" + ManejadorURI.obtenerURIDataSet("dataset_" + cuboActual.getNombre()) + "> ; ] ;");
            //Dimensiones
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
            System.out.println("Se genero correctamente el Cubo " + cuboActual.getNombre() + ".");
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
            System.out.println("Generando Dimension " + dimensionActual.getNombre() + "...");
            Iterator <Nivel> iteradorNiveles = this.niveles.values().iterator();
            while (iteradorNiveles.hasNext()){
                Nivel nivelActual = iteradorNiveles.next();
                if (nivelActual.getDimension().getNombre().equals(dimensionActual.getNombre())){
                    JDBC jdbc = new JDBC();
                    Connection con = jdbc.getConnection();
                    Statement st = con.createStatement();
                    ResultSet rs = null;
                    String query = generarConsultaRelacionalDeNivel(nivelActual);
                    System.out.println(query);
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
                    System.out.println("Se genero correctamente el Nivel " + nivelActual.getNombre() + ".");
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
            if(!attr.getColumna().equals("")) query+= ", " + "tabla."+attr.getColumna() + " ";
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
           query +=  "FROM "+ nivelActual.getTablaCompuesta().PrintFrom() + " as tabla";
       }
//        System.out.println(query);    
       return query;
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
            String template = "";
            while(!rs.isLast()){
                rs.next();
                //creo URI sujeto recorriendo dimensiones
                template = "";
                for ( String col : cuboActual.getDimension_Columna().values()) {
                    template+= rs.getString(col) + "_";
                }
                template = template.substring(0, template.length() - 1);
                //sujeto
                pw.print( "<"+ManejadorURI.obtenerURIHechoDirecto(URI_Usuario, cuboActual.getNombre(), template)+">");
                cont++;
                //observacion
                pw.print(" rdf:type ");
                pw.println(" <http://purl.org/linked-data/cube#Observation>; ");
                //dataSet
                pw.print("     qb:dataSet ");
                pw.println(ManejadorURI.obtenerURIDataSet("dataset_" + cuboActual.getNombre()) + "; ");
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
            System.out.println("Se genero correctamente el Cubo " + cuboActual.getNombre() + ".");
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
            fichero = new FileWriter("./Estructura.ttl");
            pw = new PrintWriter(fichero);
            
            //imprimo los prefijos
            imprimirPrefijos(pw);
            //imprimo las dimensiones
            imprimirDimensiones(pw);
            //Imprimo los atributtos definidios en los niveles
            imprimirAtributos(pw);
            //imprimo las medidas
            imprimirMedidas(pw);
            //imprimo los cubos
            imprimirCubos(pw);
            r2rmlFichero = new FileWriter("./MapeoR2RML.ttl");
            pw = new PrintWriter(r2rmlFichero);
            imprimirR2RMLPrefijos(pw);
            //imprimo las instancias de dim
            imprimirR2RMLInstancias(pw);
            //imprimo los hechos
            imprimirR2RMLHechos(pw);
           
            mapeoDirectoFichero = new FileWriter("./Instancias.ttl");
            pw = new PrintWriter(mapeoDirectoFichero);
            imprimirR2RMLPrefijos(pw);
            imprimirInstanciasDirecto(pw);
            imprimirHechosDirecto(pw);
            

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
