package Clases;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Modulo central donde se almacena el conocimiento de como se mapean las URI
//del sistema.
public class ManejadorURI {
    
    public static String obtenerURICubo(String cuboNom){
        return "cube:"+cuboNom;
    }
    
    public static String obtenerURICubo(String base, String cuboNom){
        return base+"/dsd/cubes#"+cuboNom;
    }
    
    public static String obtenerURIDataSet(String dataSetNom){
        return "cube:"+dataSetNom;
    }
    
    public static String obtenerURIDataSet(String base, String dataSetNom){
        return base+"/dsd/cubes#"+dataSetNom;
    }
    
    public static String obtenerURIMedida(String medidaNom){
        return "measures:"+medidaNom;
    }
    
    public static String obtenerURIMedida(String base, String medidaNom){
        return base+"/dsd/measures#"+medidaNom;
    }
    
    public static String obtenerURIDimension(String dimensionNom){
        return "dims:"+dimensionNom;
    }
    
    public static String obtenerURIDimension(String base, String dimensionNom){
        return base+"/dsd/dimensions#"+dimensionNom;
    }
    
    public static String obtenerURIJerarquia(String jerarquiaNom){
        return "hier:"+jerarquiaNom;
    }
    
    public static String obtenerURIJerarquia(String base, String jerarquiaNom){
        return base+"/dsd/jerarquias#"+jerarquiaNom;
    }
    
    public static String obtenerURINivel(String NievlNom){
        return "dims:"+NievlNom;
    }
    
    public static String obtenerURINivel(String base, String NievlNom){
        return base+"/dsd/dimensions#"+NievlNom;
    }
    
    public static String obtenerURIInstanciaDimension(String base, Dimension dim, Nivel nivelActual){
        return base+"/dic/"+nivelActual.getNombre() +"#{"+ nivelActual.getColumna() + "}";
    }

    public static String obtenerURIHecho(String base, String cuboNom, String Id){
        return base+"/data/"+cuboNom + "#{" + Id + "}";
    }

    public static String obtenerURINodoBlancolevels(String nomJerarquia, String nomNivel){
        nomNivel = nomNivel.replace("-", "_");
        nomJerarquia = nomJerarquia.replace("-", "_");
        return "_:" + nomNivel + "_" + nomJerarquia;
    }

    public static String obtenerURINodoBlancoStep(String nomNivelHijo, String nomNivelPadre,String nomJerarquia){
        nomNivelHijo = nomNivelHijo.replace("-", "_");
        nomNivelPadre = nomNivelPadre.replace("-", "_");
        nomJerarquia = nomJerarquia.replace("-", "_");
        return "_:" + nomNivelHijo + "_" + nomNivelPadre + "_" + nomJerarquia;
    }
    
    public static String obtenerURIAtributo(String attr){
         return "att:"+attr;
    }
    
    public static String obtenerURIAtributo(String base, String attr){
         return base+"/dsd/atributes#"+attr;
    }
    
    static String obtenerURIHecho(String base, cubo cuboActual) {
        //genero Autonumber
        HashMap<String, String> columnas = cuboActual.getDimension_Columna();
        Set<Map.Entry<String,String>> map =columnas.entrySet();
        String strCol = "";
        for (Map.Entry<String, String> entry : map){
            strCol+= "{"+entry.getValue()+"}_";
        }
        strCol = strCol.substring(0,strCol.length()-1);
        return base+"/data/"+cuboActual.getNombre() + "#" + strCol;  
    }

    //Mapeo con prefijos
    
    public static String obtenerURINivelPrefijo(String base, String nivelNom){
        return "dim:"+nivelNom;
    }

    //Uri para traduccion directa

    static String obtenerURIInstanciaDimensionDirecta(String base, Dimension dimensionActual, Nivel nivelActual, String id) {
        return base+"/dic/"+nivelActual.getNombre() +"#"+ id;
    }
    public static String obtenerURIHechoDirecto(String base, String cuboNom, String id){
        return base+"/data/"+cuboNom + "#" + id;
    }
      
    
}
