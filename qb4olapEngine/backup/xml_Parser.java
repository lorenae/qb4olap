package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xml_Parser {

    private static xml_Parser instance = null;

    public xml_Parser() {
    }

    public static xml_Parser getInstance() {
        if (instance == null) {
            instance = new xml_Parser();
        }
        return instance;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getAttributes().toString();

    }

    private String sacarEspacios(String uri) {
        String[] uri_sinEspacios = uri.split(" ");
        String result = "";
        for (int i = 0; i < uri_sinEspacios.length; i++) {
            result = result + uri_sinEspacios[i];
        }
        return result;
    }

    public List<Nivel> obtener_niveles_Jerarquia(Element jerarquiaActual, String nomJerarquia, manejadorCubos MC, Dimension dimActual, String nombreDimension, String nombre_Tabla,Source tablaCompuesta,boolean esTablaCompuesta) {

        List<Nivel> listaNiveles = new ArrayList<Nivel>();
        Nivel nivelAAgregar=null;
        //Busco los niveles en la jerarquia dada
        Nivel nivelPadre = new Nivel();
        NodeList niveles = jerarquiaActual.getElementsByTagName("Level");
        for (int k = 0; k < niveles.getLength(); k++) {
            Node nivel = niveles.item(k);
            if (nivel.getNodeType() == Node.ELEMENT_NODE) {
                Element nivelActual = (Element) nivel;
                String nombreNivel = nivelActual.getAttribute("name");
                nombreNivel = sacarEspacios(nombreNivel);
                String columnaTablaNivel = nivelActual.getAttribute("column");
                nombreNivel = nombreDimension + "-" + nombreNivel;
                String columnaNombreTablaNivel = nivelActual.getAttribute("nameColumn");
                if (columnaNombreTablaNivel.equals("")){
                    columnaNombreTablaNivel = columnaTablaNivel;
                }
                System.out.println("valor Nivel: " + nombreNivel);

                //Obtengo las propiedades definidas sobre el nivel
                List<Atributo> listaAtributos = new ArrayList<Atributo>();
                NodeList propiedadesNivel = nivelActual.getElementsByTagName("Property");
                for (int r = 0; r < propiedadesNivel.getLength(); r++) {
                    Node propAux = propiedadesNivel.item(r);
                    if (propAux.getNodeType() == Node.ELEMENT_NODE) {
                        Element propActual = (Element) propAux;
                        String nombrePropiedad = propActual.getAttribute("name");
                        nombrePropiedad = sacarEspacios(nombrePropiedad);
                        String columnaPropiedad = propActual.getAttribute("column");
                        Atributo propiedadAgregar = new Atributo(nombrePropiedad);
                        MC.agregarAtributo(propiedadAgregar);
                        Atributo propiedadNivel = new Atributo(nombrePropiedad,columnaPropiedad);
                        listaAtributos.add(propiedadNivel);
                    }
                }
                
                if (nivelPadre.getNombre().equals("")) {
                    if(!esTablaCompuesta)
                        nivelAAgregar = new Nivel(nombreNivel, dimActual, nombre_Tabla, columnaTablaNivel,columnaNombreTablaNivel,listaAtributos);
                    else
                        nivelAAgregar = new Nivel(nombreNivel, dimActual, tablaCompuesta, columnaTablaNivel,columnaNombreTablaNivel,listaAtributos);
                    
                    if (MC.existeNivel(nombreNivel)) {
                        MC.modificarNivel(nombreNivel, nivelPadre,nomJerarquia);
                    } else {
                        nivelAAgregar.agregarJerarquia(nomJerarquia);
                        MC.agregarNivel(nivelAAgregar);
                        listaNiveles.add(nivelAAgregar);
                    }
                    nivelPadre = nivelAAgregar;
                } else {
                    if(!esTablaCompuesta)
                        nivelAAgregar = new Nivel(nombreNivel, nivelPadre, dimActual, nombre_Tabla, columnaTablaNivel,columnaNombreTablaNivel,listaAtributos);
                    else
                        nivelAAgregar = new Nivel(nombreNivel, nivelPadre, dimActual, tablaCompuesta, columnaTablaNivel,columnaNombreTablaNivel,listaAtributos);
                    
                    if (MC.existeNivel(nombreNivel)) {
                        MC.modificarNivel(nombreNivel, nivelPadre,nomJerarquia);
                    } else {
                        nivelAAgregar.agregarJerarquia(nomJerarquia);
                        MC.agregarNivel(nivelAAgregar);
                        listaNiveles.add(nivelAAgregar);
                    }
                    nivelPadre = nivelAAgregar;
                }
            }
        }
        return listaNiveles;
    }

    private Source getTablaJoins(Node Nodo) {
        Element Elem = (Element) Nodo;
        Source source = null;
        if(Nodo.getNodeName().equals("Join")){
            Node primerHijo=Nodo.getChildNodes().item(1);
            Node segundoHijo=Nodo.getChildNodes().item(3);
            
            //Caso dos joins
            System.out.println(primerHijo.getNodeName());
            System.out.println(segundoHijo.getNodeName());
            //Caso Tabla y Join
            if(primerHijo.getNodeName().equals("Table") && segundoHijo.getNodeName().equals("Join")){
                Tabla t = new Tabla();
                Join j = new Join();
                t.setNombre(((Element)primerHijo).getAttribute("name"));
                
                j.setColumnaIzq(((Element)Nodo).getAttribute("leftKey"));
                j.setAliasDerecha(((Element)Nodo).getAttribute("rightAlias"));
                j.setAliasIzquierda(((Element)Nodo).getAttribute("leftAlias"));
                j.setColumnaDer(((Element)Nodo).getAttribute("rightKey"));
                Source aux = getTablaJoins(segundoHijo);
                j.setDer(aux);
                j.setIzq(t);
                source = j;
            }
            //Caso dos Tablas
            if(primerHijo.getNodeName().equals("Table") && segundoHijo.getNodeName().equals("Table")){
                Tabla tablaIzq = new Tabla();
                Tabla tablaDer = new Tabla();
                tablaIzq.setNombre(((Element)primerHijo).getAttribute("name"));
                tablaDer.setNombre(((Element)segundoHijo).getAttribute("name"));
                Join j = new Join();
                j.setColumnaIzq(((Element)Nodo).getAttribute("leftKey"));
                j.setAliasDerecha(((Element)Nodo).getAttribute("rightAlias"));
                j.setAliasIzquierda(((Element)Nodo).getAttribute("leftAlias"));
                j.setColumnaDer(((Element)Nodo).getAttribute("rightKey"));
                j.setDer(tablaDer);
                j.setIzq(tablaIzq);
                source = j;
            }
        } else if(Nodo.getNodeName().equals("Table")){
            Tabla t = new Tabla();
            t.setNombre(Elem.getAttribute("name"));
            source = t;
        }
        return source;
    }
    
    public Dimension obtener_jerarquias_Dimension(Element element, manejadorCubos MC, Dimension dimActual, String nombreDimension) {
        List<Nivel> listaNiveles = new ArrayList<Nivel>();
        List<Jerarquia> listaJerarquias = new ArrayList<Jerarquia>();

        //Busco las jerarquias dentro de esta dimension llamada element
        NodeList jerarquias = element.getElementsByTagName("Hierarchy");
        for (int j = 0; j < jerarquias.getLength(); j++) {
            Node jerarquia = jerarquias.item(j);
            //Lista de niveles para asignarle a la dimension

            if (jerarquia.getNodeType() == Node.ELEMENT_NODE) {
                Element jerarquiaActual = (Element) jerarquia;
                //Obtengo el nombre de la jerarquia
                String nombreJerarquia = jerarquiaActual.getAttribute("name");
                nombreJerarquia = sacarEspacios(nombreJerarquia);
                System.out.println("valor Jerarquia: " + nombreJerarquia);
                //Joins y tablas compuestas
                NodeList joins = jerarquiaActual.getElementsByTagName("Join");
                Source tablaCompuesta = null;
                String nombre_Tabla= "";
                boolean esTablaCompuesta = false;
                if(joins.getLength()!=0){
                    Node joinNodo = joins.item(0);
                    tablaCompuesta= getTablaJoins(joinNodo);
                    esTablaCompuesta = true;
                }else{
                    //Obtengo la tabla para la Jerarquia que sera la misma de los niveles
                    NodeList tablas = jerarquiaActual.getElementsByTagName("Table");
                    nombre_Tabla = "";
                    if (tablas.getLength() != 0) {
                        Element tabla = (Element) tablas.item(0);
                        nombre_Tabla = tabla.getAttribute("name");
                    } else {
                        NodeList inline_Tables = jerarquiaActual.getElementsByTagName("InlineTable");
                        Element inline_Table = (Element) inline_Tables.item(0);
                        nombre_Tabla = inline_Table.getAttribute("alias");
                        obtener_Inline_Table_Jerarquia(inline_Table,MC);
                    }
                }
                
                //Busco los niveles en esta jerarquia
                List<Nivel> listaNiveles_Jerarquia = obtener_niveles_Jerarquia(jerarquiaActual,nombreJerarquia, MC, dimActual, nombreDimension, nombre_Tabla, tablaCompuesta, esTablaCompuesta);
                listaNiveles.addAll(listaNiveles_Jerarquia);
                
                //Creo la Jerarquia y la agrego al manejador de cubos
                Jerarquia jerarquiaAgregar = new Jerarquia(nombreJerarquia, nombreDimension, listaNiveles_Jerarquia);
                MC.agregarJerarquia(jerarquiaAgregar);
                listaJerarquias.add(jerarquiaAgregar);
            }
        }
        dimActual.setNivelesMiembro(listaNiveles);
        dimActual.setJerarquiasMiembro(listaJerarquias);
        return dimActual;
    }

    public void obtener_dimensiones(Document doc, manejadorCubos MC) {
        NodeList dimensiones = doc.getElementsByTagName("Dimension");
        for (int i = 0; i < dimensiones.getLength(); i++) {
            Node dimension = dimensiones.item(i);

            if (dimension.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) dimension;
                String nombreDimension = element.getAttribute("name");
                nombreDimension = sacarEspacios(nombreDimension);
                System.out.println("valor Dimension: " + nombreDimension);
                Dimension dimActual = new Dimension(nombreDimension);
                Dimension dimAgregar = obtener_jerarquias_Dimension(element, MC, dimActual, nombreDimension);
                MC.agregarDimension(dimAgregar);
            }
        }
    }

    public List<String> obtener_Medidas(manejadorCubos MC, Element cuboElem) {

        //Obtengo las medidas y sus usos en el cubo
        NodeList medidas = cuboElem.getElementsByTagName("Measure");

        List<String> medidasUsos = new ArrayList<String>();
        for (int n = 0; n < medidas.getLength(); n++) {
            Node medida = medidas.item(n);
            if (medida.getNodeType() == Node.ELEMENT_NODE) {
                Element medidaActual = (Element) medida;
                String nombreMedida = medidaActual.getAttribute("name");
                String dataTypeMedida = medidaActual.getAttribute("datatype");
                String columnaMedida = medidaActual.getAttribute("column");
                String sql_Medida = "";
                if (columnaMedida.equals("")){
                    NodeList Expresiones_SQL = cuboElem.getElementsByTagName("MeasureExpression");
                    Node expr_SQL = Expresiones_SQL.item(0);
                    if (expr_SQL.getNodeType() == Node.ELEMENT_NODE) {
                        Element expr_SQL_Elem = (Element) expr_SQL;
                        sql_Medida = expr_SQL_Elem.getTextContent();
                        sql_Medida = sql_Medida.replaceAll("[\n\r]","");
                    }
                }
                nombreMedida = sacarEspacios(nombreMedida);
                String usoMedida = medidaActual.getAttribute("aggregator");
                String aux = nombreMedida + "-" + usoMedida;
                medida medidaAgregar = new medida(nombreMedida,columnaMedida,dataTypeMedida,sql_Medida);
                MC.agregarMedida(medidaAgregar);
                medidasUsos.add(aux);
            }
        }
        return medidasUsos;
    }

    public HashMap<String, String> obtener_Niveles_Uso(manejadorCubos MC, Element cuboElem) {
        //Obtengo los niveles de uso del cubo
        NodeList usoDimensiones = cuboElem.getElementsByTagName("DimensionUsage");
        HashMap<String, String> nivelesUsos = new HashMap<String, String>();
        for (int p = 0; p < usoDimensiones.getLength(); p++) {
            Node usoDim = usoDimensiones.item(p);
            if (usoDim.getNodeType() == Node.ELEMENT_NODE) {
                Element usoDimActual = (Element) usoDim;
                String nombreUsoDim = usoDimActual.getAttribute("source");
                nombreUsoDim = sacarEspacios(nombreUsoDim);
                String nivelUsoDim = MC.obtenerDimension(nombreUsoDim).getMenorNivel();
                //String nivelUsoDim = usoDimActual.getAttribute("level");
                nivelesUsos.put(nombreUsoDim, nivelUsoDim);
            }
        }
        //Obtengo las dimensiones definidas dentro del cubo y asigno el uso de la misma
        NodeList dimensiones = cuboElem.getElementsByTagName("Dimension");
        for (int r = 0; r < dimensiones.getLength(); r++) {
            Node dim = dimensiones.item(r);
            if (dim.getNodeType() == Node.ELEMENT_NODE) {
                Element dimActual = (Element) dim;
                String nombreDim = dimActual.getAttribute("name");
                nombreDim = sacarEspacios(nombreDim);
                System.out.println("Dimension adentro del cubo: " + nombreDim);
                String nivelUsoDim = MC.obtenerDimension(nombreDim).getMenorNivel();
                nivelesUsos.put(nombreDim, nivelUsoDim);
            }
        }
        return nivelesUsos;
    }

    public HashMap<String, String> obtener_Columnas_Dimension_Uso(manejadorCubos MC, Element cuboElem) {
        //Obtengo las columnas de los usos de dimension definidos en el cubo
        NodeList usoDimensiones = cuboElem.getElementsByTagName("DimensionUsage");
        HashMap<String, String> columnasDimension = new HashMap<String, String>();
        for (int p = 0; p < usoDimensiones.getLength(); p++) {
            Node usoDim = usoDimensiones.item(p);
            if (usoDim.getNodeType() == Node.ELEMENT_NODE) {
                Element usoDimActual = (Element) usoDim;
                String nombreUsoDim = usoDimActual.getAttribute("source");
                nombreUsoDim = sacarEspacios(nombreUsoDim);
                String columnaUsoDim = usoDimActual.getAttribute("foreignKey");
                columnasDimension.put(nombreUsoDim, columnaUsoDim);
            }
        }
        //Obtengo las dimensiones definidas dentro del cubo y obtengo columna de la misma en la Fact Table
        NodeList dimensiones = cuboElem.getElementsByTagName("Dimension");
        for (int r = 0; r < dimensiones.getLength(); r++) {
            Node dim = dimensiones.item(r);
            if (dim.getNodeType() == Node.ELEMENT_NODE) {
                Element dimActual = (Element) dim;
                String nombreDim = dimActual.getAttribute("name");
                nombreDim = sacarEspacios(nombreDim);
                String columnaDim = dimActual.getAttribute("foreignKey");
                columnasDimension.put(nombreDim, columnaDim);
            }
        }
        return columnasDimension;
    }

    public void obtener_Cubos(Document doc, manejadorCubos MC) {
        NodeList cubos = doc.getElementsByTagName("Cube");
        for (int m = 0; m < cubos.getLength(); m++) {
            Node cubo = cubos.item(m);

            if (cubo.getNodeType() == Node.ELEMENT_NODE) {
                Element cuboElem = (Element) cubo;
                String nombreCubo = cuboElem.getAttribute("name");
                nombreCubo = sacarEspacios(nombreCubo);
                
                //Obtengo la tabla para el cubo (FactTable)
                NodeList tablas = cuboElem.getElementsByTagName("Table");
                Element tabla = (Element) tablas.item(0);
                String nombre_Tabla = tabla.getAttribute("name");
                
                cubo cuboActual = new cubo(nombreCubo,nombre_Tabla);

                //Obtengo las medidas y sus usos en el cubo
                List<String> medidasUsos = obtener_Medidas(MC, cuboElem);
                //Asigno las medidas con sus usos al cubo
                cuboActual.setMedidasAgregacion(medidasUsos);

                //Obtengo los niveles de uso del cubo
                HashMap<String, String> nivelesUsos = obtener_Niveles_Uso(MC, cuboElem);
                //Asigno los niveles con sus usos al cubo
                cuboActual.setNiveles(nivelesUsos);

                //Obtengo las columnas en la fact table para las dimensiones usadas
                HashMap<String, String> columnasDimUsos = obtener_Columnas_Dimension_Uso(MC, cuboElem);
                //Asigno los niveles con sus usos al cubo
                cuboActual.setDimension_Columna(columnasDimUsos);

                //Agrego el cubo al Manejador
                MC.agregarCubo(cuboActual);

            }
        }
    }

    public void obtener_Inline_Table_Jerarquia(Element inlineTableElem, manejadorCubos MC) {

            String nombreInlineTable = inlineTableElem.getAttribute("alias");
            nombreInlineTable = sacarEspacios(nombreInlineTable);

            //Obtengo las columnas para la tabla
            NodeList defColumns = inlineTableElem.getElementsByTagName("ColumnDefs");
            Element columnsElem = (Element) defColumns.item(0);
            NodeList listaColumnas = columnsElem.getElementsByTagName("ColumnDef");
            HashMap<Integer, String> nombreTipoColumnas = new HashMap<Integer, String>();
            int contador = 1;
            for (int n = 0; n < listaColumnas.getLength(); n++) {
                Node columna = listaColumnas.item(n);
                if (columna.getNodeType() == Node.ELEMENT_NODE) {
                    Element colElem = (Element) columna;
                    String nombreColumnTable = colElem.getAttribute("name");
                    //String tipoColumnTable = colElem.getAttribute("type");
                    nombreTipoColumnas.put(contador,nombreColumnTable);
                    contador++;
                }
            }

            //Obtengo los valores de las columnas para la tabla
            NodeList filasTabla = inlineTableElem.getElementsByTagName("Row");
            List<String> filasTablaValores = new ArrayList<String>();
            for (int p = 0; p < filasTabla.getLength(); p++) {
                Node fila = filasTabla.item(p);
                if (fila.getNodeType() == Node.ELEMENT_NODE) {
                    Element filaElem = (Element) fila;
                    NodeList valoresfilas = filaElem.getElementsByTagName("Value");
                    String valorFilaCsv = "";
                    for (int r = 0; r < valoresfilas.getLength(); r++) {
                        Node valorFila = valoresfilas.item(r);
                        if (valorFila.getNodeType() == Node.ELEMENT_NODE) {
                            Element valorFilaElem = (Element) valorFila;
                            String aux = valorFilaElem.getTextContent();
                            if (r < valoresfilas.getLength() - 1) {
                                valorFilaCsv = valorFilaCsv + aux + ",";
                            } else {
                                valorFilaCsv = valorFilaCsv + aux;
                            }
                        }
                    }
                    filasTablaValores.add(valorFilaCsv);
                }
            }
            inline_Tables tablaAagregar = new inline_Tables(nombreInlineTable, filasTablaValores, nombreTipoColumnas);

            //Agrego la Inline Table al Manejador
            MC.agregarInlineTable(tablaAagregar);

    }

    public void obtener_Inline_Tables(Document doc, manejadorCubos MC) {

        NodeList inlineTables = doc.getElementsByTagName("InlineTable");
        for (int m = 0; m < inlineTables.getLength(); m++) {
            Node inlineTable = inlineTables.item(m);

            if (inlineTable.getNodeType() == Node.ELEMENT_NODE) {
                Element inlineTableElem = (Element) inlineTable;
                String nombreInlineTable = inlineTableElem.getAttribute("alias");
                nombreInlineTable = sacarEspacios(nombreInlineTable);

                //Obtengo las columnas para la tabla
                NodeList defColumns = inlineTableElem.getElementsByTagName("ColumnDefs");
                Element columnsElem = (Element) defColumns.item(0);
                NodeList listaColumnas = columnsElem.getElementsByTagName("ColumnDef");
                HashMap<Integer, String> nombreTipoColumnas = new HashMap<Integer, String>();
                int contador = 1;
                for (int n = 0; n < listaColumnas.getLength(); n++) {
                    Node columna = listaColumnas.item(n);
                    if (columna.getNodeType() == Node.ELEMENT_NODE) {
                        Element colElem = (Element) columna;
                        String nombreColumnTable = colElem.getAttribute("name");
                        String tipoColumnTable = colElem.getAttribute("type");
                        nombreTipoColumnas.put(contador, nombreColumnTable);
                        contador++;
                    }
                }

                //Obtengo los valores de las columnas para la tabla
                NodeList filasTabla = inlineTableElem.getElementsByTagName("Row");
                List<String> filasTablaValores = new ArrayList<String>();
                for (int p = 0; p < filasTabla.getLength(); p++) {
                    Node fila = filasTabla.item(p);
                    if (fila.getNodeType() == Node.ELEMENT_NODE) {
                        Element filaElem = (Element) fila;
                        NodeList valoresfilas = filaElem.getElementsByTagName("Value");
                        String valorFilaCsv = "";
                        for (int r = 0; r < valoresfilas.getLength(); r++) {
                            Node valorFila = valoresfilas.item(r);
                            if (valorFila.getNodeType() == Node.ELEMENT_NODE) {
                                Element valorFilaElem = (Element) valorFila;
                                String aux = valorFilaElem.getTextContent();
                                if (r < valoresfilas.getLength() - 1) {
                                    valorFilaCsv = valorFilaCsv + aux + ",";
                                } else {
                                    valorFilaCsv = valorFilaCsv + aux;
                                }
                            }
                        }
                        filasTablaValores.add(valorFilaCsv);
                    }
                }
                inline_Tables tablaAagregar = new inline_Tables(nombreInlineTable, filasTablaValores, nombreTipoColumnas);

                //Agrego la Inline Table al Manejador
                MC.agregarInlineTable(tablaAagregar);

            }
        }
    }

    
}
