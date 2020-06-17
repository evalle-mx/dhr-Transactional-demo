package net.tce.util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtily {
	
	static Logger log4j = Logger.getLogger( JsonUtily.class );
	
	/**
	 * Convierte un JSONObject a un java.util.Map, con esto se evita excepcion 
	 * al buscar un objeto que no exista en el Json
	 * @param json
	 * @return
	 */
	public static Map<String, Object> mapFromJson(JSONObject json){
		String[] nombres = JSONObject.getNames(json);
		//log4j.debug("<jsonUtily>total de valores en el Json:" + (nombres!=null?nombres.length:"0") );
		Map<String, Object> mapa = new TreeMap<String, Object>();
				
		if(nombres!=null){
			for(String key : nombres){
				try {
					Object value = json.get(key);
					mapa.put(key, value);
					//System.out.println("<jsonUtily>"+ key + ": "+value);
				} catch (JSONException e) {
					log4j.fatal("<jsonUtily> Error al obtener:".concat(key));
					e.printStackTrace();
				}
			}
		}
			
		return mapa;
	}

	/**
	 * Obtiene Cadena dentro de un Json, debe recibir un json con estructura {"key":valor, "key2":valor2}
	 * <ul>
	 * <li>En caso de no existir la llave, regresa null</li>
	 * <li>En caso de recibir un arreglo o tener algun error,
	 * reporta en log la excepcion interna y regresa null</li>
	 * <li>En caso de que el objeto sea encontrado, pero no sea convertible a cadena, reporta en log y regresa null</li>
	 * </ul>
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static String getStringInJson(String jsonString, String key){
		String cadena = null;
		Object objeto = getObjInJson(jsonString, key);
		if(objeto!=null){
			try{
				cadena = String.valueOf(objeto);
			}catch (Exception e) {
				System.err.println("Se obtuvo Objeto, pero este no se puede convertir en cadena");
				e.printStackTrace();
			}
		}
		return cadena; 
	}
	/**
	 * Obtiene Objeto (Object) dentro de un Json, debe recibir un Json con estructura {"key":valor, "key2":valor2}
	 * <ul><li>En caso de no existir la llave, regresa null</li>
	 * <li>En caso de recibir un arreglo o tener algun error,
	 * reporta en log la excepcion interna y regresa null</li></ul>
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static Object getObjInJson(String jsonString, String key){
		Object objeto = null;
		if(null!=jsonString && null!=key){
			try {
				JSONObject json = new JSONObject(jsonString);
				objeto = json.get(key);
			} catch (JSONException e) {   /* e.printStackTrace();*/ }
		}
		return objeto;
	}
	
	/**
	 * A partir de una cadena, obtiene el Objeto JSONx, dependiendo su estructura
	 * 1: JSONObject si empieza en {, 2: JSONArray si empieza con [
	 * @param json
	 * @return
	 */
	public static Object getJsonObjFromString(String json){
		Object jsonObject = null;
		if(null !=json && !json.trim().equals("")){
			System.out.println("json: ".concat(json));
			try {
				if(json.startsWith("{")){ 
					System.out.println("es JSONObject");
					JSONObject jsObj = new JSONObject(json);
					return jsObj;
				}else if(json.startsWith("[")){
					System.out.println("es JSONArray");
					JSONArray jsArr = new JSONArray(json);
					return jsArr;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
			
	/* *************************************************************************************
	 * *************************************************************************************
	 * *************************************************************************************
	 */
	/**
	 * Imprime el contenido de un mapa iterando las llaves del mapa
	 * @param mapa
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void imprimeMapa(Map mapa){
		Iterator<Map.Entry> i = mapa.entrySet().iterator(); 
		while(i.hasNext()){
		    String key = (String)i.next().getKey();
		    System.out.println(key+", "+mapa.get(key));
		}
	}
	
	/**
	 * Muestra como obtener un Mapa a partir de un Json, el cual se genera y demuestra tambien que
	 *  los valores que sean null no son almacenados en el JSONObject
	 * 
	 */
	@SuppressWarnings("rawtypes")
	protected static void test_mapFromJson(){
		//el siguiente objeto se declara como null o con valor para probar que no se agrega al json siendo null
		String nuleable = "noNUll";
		try{
			JSONObject json = new JSONObject();
			json.put("idPersona", "147");
			json.put("nombre", "paquito");
			json.put("apellidoPaterno", "Perez");
			json.put("apellidoMaterno", "Gomez");
			json.put("correo", "PerezG@mail.com");
			json.put("sexo", new Long(0));
			json.put("fechaNacimiento", "01/02/03");
			json.put("idEstatusInscripcion", "1");
			json.put("urlImgPerfil", "http://localhost/imagen/uno.jpg");
			json.put("idContenidoImgPerfil", "987");
			json.put("nuleable", nuleable);
			json.put("nuleable2", nuleable);
			
			Map mapa = mapFromJson(json);
			
			System.out.println("\n>>Tamaño del mapa: "+mapa.size());	
			System.out.println(mapa.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param mapa
	 */
	@SuppressWarnings("rawtypes")
	public static void printJsonFromMap(Map mapa){
		System.out.println("printJsonFromMap");
		if(mapa!=null){
			System.out.println("Mapa(".concat(String.valueOf(mapa.size())).concat(")").concat(mapa.toString()) );
			//imprimeMapa(mapa);
			System.out.println();
			JSONObject jsono = new JSONObject(mapa);//al crear, se pierde cualquier orden
			System.out.println("jsono:");
			System.out.println(jsono.toString());
		}
	}
	protected static void test_jsonFromMap(){
		//Map<String, Object> mapaObj = new java.util.HashMap<String, Object>();
		Map<String, Object> mapaObj = new java.util.TreeMap<String, Object>();

		mapaObj.put("Cadena", "Aania");
		mapaObj.put("Long", new Long(5));
		mapaObj.put("Cadena2", "Acathan");
		mapaObj.put("Booleano", false);
		mapaObj.put("Fecha", new java.util.Date());
		
		printJsonFromMap(mapaObj);
	}
	
	
	protected static void test_getObjInJson(){
		String jsonStOk = "{\"code\":\"005\",\"type\":\"I\",\"message\":\"Fue modificado satisfactoriamente \"}";
		String jsonStFail = "{\"code\":\"002\",\"type\":\"F\",\"message\":\"La construcci�n del message no es correcta, favor de verificar la especificaci�n correspondiente\"}";
		String jsonStDetail = "[ {\"email\":\"mimail@pati@to.com\",\"code\":\"006\",\"type\":\"E\",\"message\":\" Pattern(email)\"}, {\"anioNacimiento\":\"dhhdfhdfhfdhdf\",\"code\":\"006\",\"type\":\"E\",\"message\":\"Range [1927,1995]\"}, {\"sexo\":\"5\",\"code\":\"006\",\"type\":\"E\",\"message\":\"ValueSet(0,1)\"}, {\"numeroDependientesEconomicos\":\"holatyuu\",\"code\":\"006\",\"type\":\"E\",\"message\":\" Restriction(numbers)\"} ]";
		
		//1.jsonStOk debe mostrar I (type = I)
		String type = getStringInJson(jsonStOk, "type");  //(String) getObjInJson(jsonStOk, "type");
		System.out.println("jsonStOk: " + (type!=null?type:"type es null") );
		
		//2.jsonStFail debe mostrar F (type = F)
		type = (String) getObjInJson(jsonStFail, "type");
		System.out.println("jsonStFail: " + (type!=null?type:"type es null") );
		
		//2.jsonStFail debe mostrar null, porque genera excepcion al intentar convertir arreglo en Json (type = null)
		type = getStringInJson(jsonStDetail, "type");
		System.out.println("jsonStFail: " + (type!=null?type:"type es null") );
		
	}
}
