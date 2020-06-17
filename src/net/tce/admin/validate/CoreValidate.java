package net.tce.admin.validate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.tce.cache.TCEStartUp;
import net.tce.dto.ServiceAppDto;
import net.tce.util.Mensaje;
import net.tce.util.TCEUIUtily;

/**
 * Clase propietaria de Mock, realiza validaciones generales y de parametros
 * @author Netto
 *
 */
public class CoreValidate {

	static Logger log4j = Logger.getLogger( CoreValidate.class );
	private static boolean loaded = false;
	private static ConcurrentHashMap<String, ServiceAppDto> mapValidaDtos = new ConcurrentHashMap<String, ServiceAppDto>();
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void setLoaded(boolean loaded) {
		CoreValidate.loaded = loaded;
	}
	
	/**
	 * Obtiene el Dto de validacion por key
	 * @param uriCode
	 * @return
	 */
	public static ServiceAppDto getDtoByUriCode(String uriCode){
		if(mapValidaDtos.containsKey(uriCode)){
			return mapValidaDtos.get(uriCode);
		}else{
			return null;
		}
	}
	
	
	public static boolean setMapValida(ConcurrentHashMap<String, ServiceAppDto> map){
		if(map!=null){
			mapValidaDtos = map;			
			return true;
		}
		return false;
	}
	
	
	/**
	 * Inicia los valores del Cache:
	 * <ul><li>URICODES-URISERVICES-FileCache</li><ul>
	 */
	public static void setup() {		
		if(!isLoaded()){
			log4j.info("<CoreCache> \n >>>>>>>> SETUP >>>>>>>" );
			setLoaded(TCEStartUp.initIt());
			log4j.debug(">>>>>>>> FIN DE SETUP<<<<<<<<< \n");
		}else{
			log4j.info("<CoreCache> Already Loaded!" );
		}
	}
	
	
	/**
	 * Obtiene la llave para el valor determinado
	 * Map<Key,Value>
	 * @param value
	 * @return
	 */
	public static ServiceAppDto getDtoByUriService(String uriService){
		//String keyFounded = null;
		ServiceAppDto dtoFound =null;
		boolean founded = false;
		Iterator<Entry<String, ServiceAppDto>> i = mapValidaDtos.entrySet().iterator(); 
		while(i.hasNext() && !founded){
		    String key = (String)i.next().getKey();
//		    log4j.debug("comparando key: "+ key );
		    ServiceAppDto dto = mapValidaDtos.get(key);
//		    log4j.debug("dto.uriservice: " + dto.getUriService());
		    if(dto.getUriService().equals(uriService)){
		    	dtoFound = dto;
		    	founded = true;
		    }
		}
		return dtoFound;
	}
	
	
	/*  ********************* validate Methods   ******************************************** */
	
	/**
	 * Procedimiento que engloba las validaciones requeridas 
	 * para regresar un Dto emulando el procedimiento en CORE
	 * @param commonDto
	 * @return
	 */
	public static ServiceAppDto validate(ServiceAppDto dto){
		if(!isLoaded()){setup();}
		dto.resetValid();
		boolean error = false;
		String errorValidate = null;
		if(dto.getUriService() == null || dto.getUriService().trim().equals("") ){
			log4j.debug("<validate>Error: uriService es invalido");
			errorValidate = "uriService es invalido";
			error = true;
		}else{
			ServiceAppDto dtoValidate = getDtoByUriService(dto.getUriService());//TODO cambiar key a UriService			
			if(dtoValidate==null){
				/* Si ocurre este error, verificar que el servicio este definido en JSON_ENTERPRISE_EXTENDED */
				log4j.debug("<validate>Error: No esta definido este uriService en el Mapa de UriCOdes/Services");
				errorValidate = " No esta definido este uriService ["+dto.getUriService()+"]" 
//				+ " en el Mapa de UriCOdes/Services [" + Constante.JSON_VALIDATE_ENTERPRISE_EXTENDED+"]"
				;
				error = true;
			}else{
//				log4j.debug("dtoValidate.getParametros(): " + dtoValidate.getParametros());
				List<String> params = dtoValidate.getParamsList();
				if(dto.getParametros()!=null){
					log4j.debug("agregando parametros personalizados en dto: " + dto.getParametros());
					List<String> xtraparams = dto.getParamsList(); 
					log4j.debug("xtraparams: " + xtraparams );
					params.addAll(xtraparams);
				}
				boolean existenParams = true;
				String paramsNoEncont = "";
				JSONObject json = dto.getJsRequest();
				log4j.debug("Parametros a verificar en Json: " + params );
				log4j.debug("json: " + json.toString() );
				for(int x=0;x<params.size();x++){
					if(!json.has(params.get(x))){
						log4j.error("No se encontro " + params.get(x) + " en Json input");
						existenParams = false;/* si falta un parametro cancela la bandera*/
						paramsNoEncont+=params.get(x).concat("|");
					}
				}
				if(!existenParams){
					log4j.debug("Faltan parametros: "+ paramsNoEncont.substring(0, paramsNoEncont.length()-1));
					errorValidate = "Faltan parametros: "+ paramsNoEncont.substring(0, paramsNoEncont.length()-1);
					error = true;
				}
			}
		}
		if(error){
			dto.setCode(Mensaje.SERVICE_CODE_006);
			dto.setType( Mensaje.SERVICE_TYPE_FATAL);
			dto.setMessage(" -".concat(errorValidate).concat("-"));
			dto.setMessages(TCEUIUtily.getJsonMessage( Mensaje.SERVICE_CODE_006, 
					Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR) );
		}
		return dto;
	}			
	
}
