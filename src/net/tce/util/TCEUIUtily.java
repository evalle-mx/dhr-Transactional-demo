package net.tce.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import net.tce.exception.SystemTCEException;

/**
 * Clase de Utilerias propias para procesos simples comunes en la aplicacion
 * @author evalle
 *
 */
public class TCEUIUtily {

	static Logger log4j = Logger.getLogger( TCEUIUtily.class);
	private static final String[] invTokens = Constante.INVTOKENS;
	static String resp=null;
	static JSONArray jsonArray = new JSONArray();
	static JSONObject jsonObj = new JSONObject();
	static JSONArray jsonArrayActive = new JSONArray();
	static JSONObject jsonObjActive = new JSONObject();
	
	/**
	 * Transforma un archivo en arreglo de Bytes
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
   	 
		java.io.InputStream is = new java.io.FileInputStream(file);
	    byte[] bytes = new byte[(int)file.length()];
		 
	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while ((offset < bytes.length) && ((numRead=is.read(bytes, offset, bytes.length-offset)) >= 0)) {
	        offset += numRead;
	    }
	    is.close();
	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file " + file.getName());
	    }
	    return bytes;
	}
	
	/**
     * Obtiene la extension del archivo, blanco si no tiene
     * @param fullName
     * @return
     */
    public static String getExtension(String fullName){
   	 String extension = "";
   	 int extDot = fullName.lastIndexOf('.');
   	 if(extDot > 0){
			 extension = fullName.substring(extDot +1);
		}
   	 return extension;
    }
    
    /**
     * Reemplaza los caracteres invalidos por un guion bajo (_)
     * @param fullString
     * @return
     */
    public static String replaceInvalidChars(String fullString){
    	
    	for(String caract : invTokens){
    		fullString = fullString.replace(caract, "");
    	}
    	return fullString;
    }
    
    /**
     * Obtiene la extension del archivo, si no tiene, devuelve el default asignado
     * @param fullName
     * @param stDefault
     * @return
     */
    public static String getExtension(String fullName, String stDefault){
    	String extension = getExtension(fullName);
    	if(extension.trim().equals("")){
    		extension = stDefault;
    	}
    	return extension;
    }
    
    
    /**
     * Metodo desarrollado para obtener solo nombre sin ruta, en caso de IE
     * @param fullName
     * @return
     * @author evalle
     */
    public static String getSimpleFileName(String fullName){
    	String name = "";
    	try{
    		name = fullName.indexOf("\\")!=-1?
        			fullName.substring(fullName.lastIndexOf("\\")+1):
        				fullName;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return name;
    }
    
    
    /**
	 * Este metodo obtiene un Json con error (inicialmente no Recuperable), en base a clave de error, 
	 * sino encuentra, retorna default
	 * @param cveError
	 * @return
	 * @author evalle
	 */
	public static String getErrorJson(String cveError){
//		try{
//			resp = java.util.ResourceBundle.getBundle(Constante.DEFAULT_BUNDLE).
//					getString("app.error.".
//					concat(cveError==null?Constante.ERROR_CODE_SYSTEM:cveError).
//					concat(".label"));
//		}catch (Exception e) {
//			resp = Constante.ERROR_LABEL_SYSTEM;
//		}
		resp = Mensaje.MSG_ERROR_SISTEMA;
		return getErrorJson(cveError, resp);
	}
	/**
	 * Este metodo Genera una cadena Json con error 
	 * @param cveError
	 * @param eMessage
	 * @return
	 * @author evalle
	 */
	public static String getErrorJson(String cveError, String eMessage){
		if(null==eMessage || eMessage.trim().length()<1){
			return getErrorJson(cveError);
		}
//		return getJsonMessageGson(cveError, Constante.SERVICE_TYPE_ERROR, eMessage);
		return getJsonMessage(cveError, Mensaje.SERVICE_TYPE_ERROR, eMessage);
	}
	/**
	 * Obtiene una cadena con formato Json en base a los parametros ingresados
	 * @param code
	 * @param type
	 * @param eMessage
	 * @return
	 * @throws JSONException
	 * @author evalle
	 */
	public static String getJsonMessage(String code, String type, String eMessage) {
		JSONArray jsArr = new JSONArray();
		JSONObject jsonMsg = new JSONObject();
		try{
			jsonMsg.put(Constante.PARAM_JSON_CODE, code!=null?code:Mensaje.MSG_ERROR_SISTEMA);
			jsonMsg.put(Constante.PARAM_JSON_TYPE, type!=null?type:Mensaje.SERVICE_TYPE_FATAL);
			jsonMsg.put(Constante.PARAM_JSON_MESSAGE, eMessage!=null?eMessage:"");
			jsArr.put(jsonMsg);
		}catch (Exception e) {
			log4j.debug(e);
			return "[{\"".concat(Constante.PARAM_JSON_CODE).concat("\":\"").concat(code!=null?code:Mensaje.MSG_ERROR_SISTEMA)
			.concat("\",\"").concat(Constante.PARAM_JSON_TYPE).concat("\":\"").concat(type!=null?type:Mensaje.SERVICE_TYPE_FATAL).concat("\",\"")
			.concat(Constante.PARAM_JSON_MESSAGE).concat("\":\"").concat(eMessage!=null?eMessage:"").concat("\"}]");
		}
		return jsArr.toString();
	}
	
	 /**
	 * Obtiene una cadena con formato Json en base a los parametros ingresados
	 * (Replica de APP)
	 * @param code
	 * @param type
	 * @param eMessage
	 * @return
	 */
	public static String getJsonMessageGson(String errores,Object object ) {
		StringBuilder sb=new StringBuilder("[");
		return (errores == null || errores.equals("")) ? 
						 		sb.append(new Gson().toJson(object)).append("]").toString():
						 		sb.append(new Gson().toJson(object)).append(",").
						 		append(errores.substring(1, errores.length())).toString();
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
				log4j.error( "Se obtuvo Objeto, pero este no se puede convertir en cadena", e );
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
			} catch (JSONException e) {
			log4j.error( Mensaje.MSG_ERROR, e );
			}
		}
		return objeto;
	}
	
	/**
	 * Obtiene un mapa a partir de un Json (Cadena) <br/> con esta opcion, 
	 * se pueden solicitar parametros inexistentes sin generar excepcion
	 * (Mejorado para recibir json y JsonArray)
	 * @param jsonString (String)
	 * @return java.util.Map
	 * @author evalle
	 */
	public static Map<String, Object> mapFromJson(String jsonString) {
		Map<String, Object> mapa = new TreeMap<String, Object>();
		if(null!=jsonString){
			try {
				if(jsonString.startsWith("{")){
					JSONObject json = new JSONObject(jsonString);
					mapa = mapFromJson(json);
				}else{
					JSONArray jsArr = new JSONArray(jsonString);
					long sizeArr = jsArr.length();
					log4j.debug("El arreglo contiene "+sizeArr+" json's");
					if(sizeArr>0){
						for(int i = 0; i<sizeArr;i++){
							JSONObject json = jsArr.getJSONObject(i);
							log4j.debug(">"+json);
							Map<String, Object> subMapa = mapFromJson(json);
							mapa.putAll(subMapa);
						}
					}
				}
			} catch (JSONException e) {
				log4j.error( Mensaje.MSG_ERROR, e );
			}
		}
		return mapa;
	}
	
	/**
	 * Obtiene un mapa a partir de un Json, con esta opcion, 
	 * se pueden solicitar parametros inexistentes sin generar excepcion
	 * @param json (org.json.JSONObject)
	 * @return java.util.Map
	 * @author evalle
	 */
	public static Map<String, Object> mapFromJson(JSONObject json) {
		String[] nombres = JSONObject.getNames(json);
		Map<String, Object> mapa = new TreeMap<String, Object>();
				
		for(String key : nombres){
			try {
				Object value = json.get(key);
				mapa.put(key, value);
			} catch (JSONException e) {
				log4j.error("error al parsear:".concat(key).concat(" al mapa"), e);
			}
		}
		return mapa;
	}
	
	
	/**
	 * Agrega el atributo idEmpresaConf al json en la cadena input por medio de org.json.JSONObject
	 * @param inputJson
	 * @param idEmpresaConf
	 * @return String
	 * @throws JSONException
	 * @author evalle
	 */
	public static String addIdConfIdPersToJson(String inputJson, String idEmpresaConf, String idPersona) throws JSONException{		
		JSONObject jsonObj = new JSONObject(inputJson);		
		jsonObj.put(Constante.PARAM_JSON_IDCONF, idEmpresaConf);
		if(null != idPersona){
			jsonObj.put(Constante.PARAM_JSON_IDPERSONA, idPersona);
		}
		return jsonObj.toString();
	}
	
	/**
	 * Obtiene clase BusinessException a partir de un json de error
	 * {"code":"","type":"","message":""}
	 * en caso de null o error, envia Default
	 * @param errorJson
	 * @return net.tce.ui.view.exception.BusinessException
	 * @author evalle
	 */
	public static SystemTCEException getSysExcFromJson(String errorJson){
		String cveError = Mensaje.SERVICE_CODE_002;
		String msg = Mensaje.MSG_ERROR;
		
		try {
			JSONObject jsonObj = new JSONObject(errorJson);
			cveError = jsonObj.getString(Constante.PARAM_JSON_CODE);
			msg = jsonObj.getString(Constante.PARAM_JSON_MESSAGE);
			
		} catch (Exception e) {
			log4j.debug("Error al generar json de excepcion desde parametros", e);
		}
		return new SystemTCEException(cveError, msg);
	}
	
	/**
     * Obtiene un arreglo de cadenas desde una cadena a separar por token
     * @param strDatos
     * @param delim
     * @return
     */
    public static String[] getTokenizerData(String strDatos, String delim){
    	String[] datos = null;
    	try{
    		StringTokenizer tokens=new StringTokenizer(strDatos, delim);
        	int iDatos = tokens.countTokens();
        	datos = new String[iDatos];    	
        	int i=0;
        	 while(tokens.hasMoreTokens()){
                 String str=tokens.nextToken();
                 datos[i]=str;
                 i++;
             }
    	}catch (Exception e) {
			log4j.error("Error al Obtener tokens de la cadena de entrada", e);
		}	
    	return datos;
    }
    
	 /**
	  * Regresa un mensaje de error_fatal en JSon  
	  * (¿PORQUE NO SE EMPLEA EL METODO getJsonMessage(code, type, error)? **
	  * @return
	  */
	public static String getJSONCreateFatal(){
		//return getJsonMessageGson(Constante.ERROR_CODE_SYSTEM, Constante.SERVICE_TYPE_FATAL, 
		return getJsonMessage(Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_FATAL,
				Mensaje.MSG_ERROR_SISTEMA);
	 }
	
	/**
	 * Hace validaciones del item, si no es valido arroja excepcion
	 * @param fileItem
	 * @throws Exception
	 */
	public static boolean validaArchivo(String fileName, Long fileSize, String tipoContenido) throws SystemTCEException {
		log4j.debug(" validaArchivo() -->  FileName: ".concat(fileName));
		log4j.debug("validaArchivo() -->  fileSize: ".concat(String.valueOf(fileSize)));
		log4j.debug("validaArchivo() -->  tipoContenido: ".concat(tipoContenido));
		boolean resp=true;
		//Se verifica que exista el tipoContenido en el cache
		//if(((String)AppTceCache.get(Constante.IDS_TYPES_FILE)).indexOf(tipoContenido.concat("|")) > -1){
		if(fileName!=null){
			String extension = getExtension(fileName);
			Long minimo = new Long(1000);
					//(Long)AppTceCache.get(new StringBuilder(Constante.MINSIZE_FILE).append(tipoContenido).toString());
			Long maximo = new Long(2000000);
					//(Long)AppTceCache.get(new StringBuilder(Constante.MAXSIZE_FILE).append(tipoContenido).toString());
			String types="jpg|png|bmp|gif|pdf|jpeg|";
					//(String)AppTceCache.get(new StringBuilder(Constante.TYPES_FILE).append(tipoContenido).toString());
			
			log4j.debug("validaArchivo() -->  minimo: "+minimo+", maximo="+maximo+", types="+types+
						" extension_archivo="+extension);
			//Se analiza el minimo tamaño
			if(minimo != null && fileSize.longValue() < minimo.longValue()){ 
				throw new SystemTCEException(fileName.concat(": ").concat(Mensaje.SERVICE_MSG_ERROR_UPLOAD_SIZE_MIN) );
			}
			//Se analiza el maximo tamaño
			if(maximo != null && fileSize.longValue() > maximo.longValue() ){
				throw new SystemTCEException(fileName.concat(": ").concat(Mensaje.SERVICE_MSG_ERROR_UPLOAD_SIZE_MAX) );
			}
			//si no hay extension de archivo
			if(extension== null || extension.equals("")){
				throw new SystemTCEException(fileName.concat(": ").concat(Mensaje.SERVICE_MSG_ERROR_UPLOAD_TYPE) );
			}
			//Se analiza el tipo de archivo
			if(types != null && types.indexOf(extension.toLowerCase().concat("|")) == -1){
					throw new SystemTCEException(Mensaje.SERVICE_MSG_ERROR_UPLOAD_TYPE);
			}
		}else{
			resp=false;
			log4j.error("El json_dataconf para el tipo de contenido: ".concat(tipoContenido).
						concat(", no se encuentra en cache"));
		}
		return resp;
	}

	/**
	 * Regresa una cadena aleatoria 
	 * @param longitud de la cadena
	 * @return
	 */
	public static String getCadenaAlfanumAleatoria (int longMin, int longMax){
		String cadenaAleatoria = "";
		int i = 0;
		int numRandom=0;
		//Encontrar un numero entre longMin y longMax para definir el tamaño de la cadena
		do{
			numRandom=new Random().nextInt(longMax);
		}while( longMin > numRandom );
		//Despues de obtener el tamaño de la cadena, se consigue la correspondiente
		while ( i < numRandom){
			char c = (char)new Random().nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
	
	/**
	 * Regresa un mensaje para logOut
	 * @return  mensaje
	 */
	public static String msgLogOut(){
		try {
			log4j.debug("msgLogOut() --> jsObj="+jsonObj+" jsArray="+jsonArray);
			log4j.debug("msgLogOut() -->  jsArray.length="+jsonArray.length());
			if(jsonArray.length() == 0){
					jsonObj.put(Constante.PARAM_JSON_NAME,"url");
					jsonObj.put(Constante.PARAM_JSON_VALUE,Constante.URL_FRONTPAGE );
					jsonObj.put(Constante.PARAM_JSON_TYPE,"T");
					jsonObj.put(Constante.PARAM_JSON_ACTIVE,false);
					jsonArray.put(jsonObj);
			}
			return jsonArray.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			//return getJsonMessageGson(Constante.ERROR_CODE_SYSTEM,
			return getJsonMessage(Mensaje.SERVICE_CODE_002,
					Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR);
		}
	}

	/**
	 * Regresa un mensaje activo true, sólo se usa cuando hay sesión, ya que si no la hay, el filtro no permite llegar al servicio.
	 * @return  mensaje
	 */
	public static String msgActive(Boolean isActive){
		try {
			if(jsonArrayActive.length() == 0){
				jsonObjActive.put("active",isActive);
				jsonArrayActive.put(jsonObjActive);
			}
			return jsonArrayActive.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return getJsonMessage(Mensaje.SERVICE_CODE_002,
					Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR);
		}
	}
	
	/** 
	 * Convierte un stream en un arreglo de Bytes (Imagen)
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArrayImgs(InputStream stream) throws IOException {
		byte[] b = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while(-1 != (len=stream.read(buf))) {
            out.write(buf, 0, len); 
        }  
        b = out.toByteArray();
        /*log4j.debug("b.length " + b.length); */
		return b;
	}
	
	/*public static void main(String[] args) {
		
		 log4j.debug("cadena:"+ getCadenaAlfanumAleatoria (20, 35));
	}*/
	
}