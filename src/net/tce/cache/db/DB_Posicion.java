package net.tce.cache.db;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.mock.AppEndPoints;
import net.tce.util.AppUtily;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class DB_Posicion {

	static Logger log4j = Logger.getLogger("DB_Posicion");
	
	/* ******************* VACANCY  ********************** */
	private static JSONArray jsArrPositions = null;
	
	public static JSONArray getLsPosition() throws Exception{
		if(jsArrPositions==null){
			log4j.debug("<getLsPosition> Se inicializa el listado de Posiciones desde archivo.json ");
			String stGet = AppUtily.getJsonFile(AppEndPoints.SERV_VACANCY_G);
			jsArrPositions = new JSONArray(stGet);
		}
		return jsArrPositions;
	}
	
	public static void setLsPosition(JSONArray jsArr){
		jsArrPositions=jsArr;
	}
	
	/* *****************  POSITION  ********************** */
	static final ConcurrentHashMap<String, JSONObject> positionMap 
			= new ConcurrentHashMap<String, JSONObject>();
	/**
	 * Adiciona/Reemplaza un Objeto JSONObject que contiene la información de la posicion 
	 * @param 
	 * @param object
	 * @return
	 */
	public static void setPosition(String identificador, JSONObject jsonObj){
		positionMap.put(identificador, jsonObj);
	}	
	/**
	 *  Obtiene un JSONObject dependiendo de su identificador (key) 
	 *  <br>
	 *  i.e.: getPosition("P-"+idPosicion);
	 * @param nombreDataInfoDto
	 * @return
	 */
	public static JSONObject getPosition(String identificador){
		JSONObject objectResp=null;
		if(positionMap.containsKey(identificador)){
			objectResp=positionMap.get(identificador);
		}
		return objectResp;
	}
	/**
	 * evalua si el mapa de posiciones esta vacio
	 * @return
	 */
	public static boolean isPositionEmpty(){
		return positionMap.isEmpty();
	}
	/**
	 * devuelve el set de key's (identificadores) del mapa
	 * @return
	 */
	public static Set<String> getPositionKeySet(){
		return positionMap.keySet();
	}
	
	/* ********************  MONITOR ************************** */
	static final ConcurrentHashMap<String, JSONArray> monitorMap 
	= new ConcurrentHashMap<String, JSONArray>(); 
	
	/**
	 * Adiciona/Reemplaza un Objeto JSONArray con el arreglo de Monitores en Posicion 
	 * @param 
	 * @param object
	 * @return
	 */
	public static void setMonitors(String identificador, JSONArray jsArray){
		monitorMap.put(identificador, jsArray);
	}	
	/**
	 *  Obtiene un JSONArray dependiendo de su identificador (key)
	 * @param nombreDataInfoDto
	 * @return
	 */
	public static JSONArray getMonitors(String identificador){
		JSONArray objectResp=null;
		if(monitorMap.containsKey(identificador)){
			objectResp=monitorMap.get(identificador);
		}
		return objectResp;
	}
	/**
	 * Valida si monitorMap esta vacio
	 * @return
	 */
	public static boolean isMonitorsEmpty(){
		return monitorMap.isEmpty();
	}
	/**
	 * Devuelve el Set de key's de MonitorMap
	 * @return
	 */
	public static Set<String> getMonitorsKeySet(){
		return monitorMap.keySet();
	}
	
	
	/**
	 * Despliega el contenido del mapa dependiendo su nombre 
	 * <b>monitors</b> o <b>position</b>
	 * @param nombreMapa
	 */
	public static void viewData(String nombreMapa){
		if(nombreMapa.toLowerCase().indexOf("monitors")!=-1){
			log4j.debug(" ## monitors="+monitorMap);
		}else if(nombreMapa.toLowerCase().indexOf("position")!=-1){
			log4j.debug(" ## positions="+positionMap);
		}
	}
	
	/**
	 * Vacia el cache para la proxima sesión, se invoca en AdministrativeServImpl.getResult
	 */
	public static void cleanUp(){
		log4j.debug("<cleanUp> Se reinician los valores");
		monitorMap.clear();
		positionMap.clear();
		jsArrPositions=null;
	}
}
