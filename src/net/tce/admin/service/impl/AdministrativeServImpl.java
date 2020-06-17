package net.tce.admin.service.impl;

import java.util.Iterator;

import net.mock.AppEndPoints;
import net.mock.ClassifierDto;
import net.mock.SolrDocTopics;
import net.tce.admin.service.AdministrativeServ;
import net.tce.cache.db.DB_Postulante;
import net.tce.cache.db.DB_Posicion;
import net.tce.cache.db.DB_TrackPosicion;
import net.tce.dto.CatalogueDto;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.HandShakeDto;
import net.tce.dto.MensajeDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

/**
 * Clase donde se Emulan las politicas de negocio de servicios <ul>
 * <li>Catalogue</li>
 * <li>DocumentoClasificacion</li>
 * <li>Catalogue</li>
 * <li>HandShake</li>
 * <li>Logout</li>
 * </ul>
 * @author Netto
 *
 */

@Transactional
@Service("administrativeServ")
public class AdministrativeServImpl extends BaseMockServiceImpl implements AdministrativeServ {
	boolean validated = false; 
	static Logger log4j = Logger.getLogger( AdministrativeServImpl.class );
//	
	@Autowired
	private Gson gson;
	
	/**
	 * Procesa el Json para modulos Administrativos
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject json) throws SystemTCEException  {
		String resultado = "[]";
		if(json==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			/* Empresa parametro Services (Unicamente para indicar que es Mock */
//			if(uriService.equals(AppEndPoints.SERV_ENTERPRISE_G  )){//"/module/enterpriseParameter/get")){
//				log4j.debug(" ******************************************************");
//				log4j.debug(" ********* APP MOCK (applicationContextMock) **********");
//				log4j.debug(" ******************************************************\n\n ");
//				resultado = getJsonFile(uriService);
//			} else 
			/* LOAD-TOKENS Service */
			
			if(uriService.equals(AppEndPoints.SERV_DOCSCLASS_T  )){//"/module/classify/loadTokens")){
				log4j.debug("Servicio DOCSCLASS.T");
				resultado = respLoadTokens(json);
			}
			/* HANDSHAKE (candidato) Services */
			else if(uriService.equals(AppEndPoints.SERV_HANDSHAKE_C  )){//"/module/handshake/create")){
				log4j.debug("Servicio HANDSHAKE.C");
				resultado = processHandShake(json);
			}
			/* CATALOGUE Services */
			else if(uriService.equals( AppEndPoints.SERV_CATALOGUE_G)){//"/admin/catalogue/getCatalogueValues")){
				log4j.debug("Servicio CATALOGUE.G");
				resultado = processCatalogue(json);
			}
			/* LOGOUT Services */
			else if(uriService.equals( AppEndPoints.SERV_LOGOUT_D)){//"//logout/delete
				log4j.debug("Servicio LOGOUT.D");
				//Vaciar los cache's de Posición y Trackings
				DB_TrackPosicion.cleanUp();
				DB_Posicion.cleanUp();
				DB_Postulante.cleanUp();
				
				resultado = "[]";	//TODO verificar salida real
			}
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
			
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}
		
		return resultado;
	}
	
	
	/**
	 * Procesa Peticion de Catalogo
	 */
	private String processCatalogue(JSONObject json ) throws Exception {
		String nombreCatalogo = json.getString("catalogueName");
		String jsonResponse = "[]";
		log4j.debug("jsonGet: " + json);
		log4j.debug("Buscando catalogo de >>" + nombreCatalogo +"<<");
		JSONArray jsArr = new JSONArray(getJsonFile(AppEndPoints.SERV_CATALOGUE_G));
		
		//[1] si el nombre de catalogo es valido y es diferente a Todos (ALL) */
		if( !(nombreCatalogo.trim().equals("")) && !(nombreCatalogo.toUpperCase().equalsIgnoreCase("ALL")) ){			
			System.out.println("tamaño jsArray " + jsArr.length());
			JSONObject catalogos = jsArr.getJSONObject(0);
			JSONArray jsaOut = new JSONArray();
			log4j.debug("# de Catalogos en Json: " + catalogos.length());
			
			String catSearch = "\"".concat(nombreCatalogo).concat("\":{");
			JSONObject jsnCatalogo = new JSONObject();
			/*Si existe el catalogo en el Json, se obtiene*/
			if(catalogos.toString().indexOf(catSearch)!=-1){
				JSONObject catalogo = catalogos.getJSONObject(nombreCatalogo);
				log4j.debug(catalogo);
				
				
				jsnCatalogo.put(nombreCatalogo, catalogo);
				
				jsaOut.put( jsnCatalogo );
			}else{
				
				//Si no existe, se envia mensaje como en APP
				jsnCatalogo.put(Constante.PARAM_JSON_CODE, "002");
				jsnCatalogo.put(Constante.PARAM_JSON_TYPE, "E");
				jsnCatalogo.put(Constante.PARAM_JSON_MESSAGE, "No existe el Catálogo: ".concat(nombreCatalogo));
				jsaOut.put( jsnCatalogo );
			}
			jsonResponse = jsaOut.toString();
			
		}
		/* si es Todos, regresa el mismo de entrada */
		else if(nombreCatalogo.toUpperCase().equalsIgnoreCase("ALL")){
			jsonResponse = jsArr.toString();//jsonGet;
		}
			
		return jsonResponse;
		
	}
	
	/**
	 * Procesa una peticion de HandShake create
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private String processHandShake(JSONObject json) throws Exception {
		
		String operacion = json.getString("claveEvento");
		String idEstatusOperativo = "0";
		if(operacion==null || operacion.trim().equals("")){
			operacion = "0";
		}
		log4j.debug("<processHandShake> Operación HS a realizar: " + operacion );
//		String OP_VISTO = "1", OP_INVITACION = "2", OP_RECHAZO = "3", 
//				OP_ACEPTAR = "4", OP_NO_ACEPTAR="5", OP_REINVITAR = "7";
		String OP_VISTO = "HANDCHECK_VISTO", OP_INVITACION = "HANDCHECK_INVITACION", OP_RECHAZO = "HANDCHECK_RECHAZO", 
				OP_ACEPTAR = "HANDCHECK_ACEPTAR_INVITACION", OP_NO_ACEPTAR="HANDCHECK_DENEGAR_INVITACION", OP_REINVITAR = "HANDCHECK_REINVITACION";
		//---- OPERACIONES CONTRATANTE->CANDIDATO
		if(operacion.equals(OP_VISTO)){ /* el contratante ha visto el registro encontrado */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_VISTO);//"2";
		}
		else if(operacion.equals(OP_INVITACION)){  /* el contratante ha enviado una invitación al candidato */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO);//"3";
		}
		else if(operacion.equals(OP_RECHAZO)){ /* el contratante ha rechazado al candidato encontrado */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_RECHAZADO);//"8";
		}
		else if(operacion.equals(OP_REINVITAR)){ /* el contratante ha enviado nuevamente la invitacion */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO);//"3";
		}
		
		//---- OPERACIONES CANDIDATO-->CONTRATANTE
		else if(operacion.equals(OP_ACEPTAR)){ /* el candidato ha visto la oferta y acepto seguir el proceso */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_INTERESADO);//"5";
		}
		else if(operacion.equals(OP_NO_ACEPTAR)){ /* el candidato ha visto la oferta, pero indico que no esta interesado */
			idEstatusOperativo = String.valueOf(Constante.ESTATUS_CANDIDATO_OPERATIVO_NO_INTERESADO);//"6";
		}
		JSONObject jsOutput = new JSONObject();
		jsOutput.put(Constante.PARAM_JSON_CODE, "004");
		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
		jsOutput.put("name", "idEstatusOperativo");
		jsOutput.put(Constante.PARAM_JSON_VALUE, idEstatusOperativo );
		
		return "["+jsOutput.toString()+"]";
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param classifierDto
	 * @return
	 */
	private String respLoadTokens(JSONObject json){
		String response = "[]";
		try{
			String docType = json.getString("docType");
			String modo = json.getString("modo");
			Integer numeroDocs = json.getInt("numeroDocs");
			String jsonTokens = json.getString("jsonTokens");
			String idArea = json.getString("idArea");
			if(idArea == null || idArea.equals("undefined")){
				throw new NullPointerException("idArea es 'Undefined'");
			}
			
			Integer desviacion = null;
			if(json.toString().indexOf("\"desviacion\"")!=-1){
				desviacion = Integer.parseInt(json.getString("desviacion") );
			}
					
			ClassifierDto classifierDto = new ClassifierDto();
			classifierDto.setJsonTokens(jsonTokens);
			classifierDto.setNumeroDocs( numeroDocs );
			classifierDto.setIdArea(idArea);
			classifierDto.setDesviacion(desviacion);
			classifierDto.setDocType(docType);
			classifierDto.setModo(modo);
			
			loadFromTokens(classifierDto);			
		}catch (Exception e){
			log4j.fatal("<CoreService> Error al subir los archivos a Solr: \n" + e.getMessage() + "\n", e);
			response = AppUtily.getJsonMessage(Mensaje.SERVICE_CODE_006, 
					Mensaje.SERVICE_TYPE_FATAL, e.getMessage() );
		}
		return response;
	}
	
	/**
     * Realiza la carga por medio del parametro de Tokens proporcionados, y el numero de documentos a crear
     * @param classifierDto
     * @throws Exception
     */
    private void loadFromTokens(ClassifierDto classifierDto) throws Exception {
    	
    	String jsonTokens = classifierDto.getJsonTokens();
    	Integer objMaxDesv = classifierDto.getDesviacion();//puede ser null
    	Integer objNumDocs = classifierDto.getNumeroDocs();
    	String idArea = classifierDto.getIdArea();
    	
    	if(objMaxDesv == null || objMaxDesv.intValue()>100){
    		objMaxDesv = new Integer(100);
    	}else if(objMaxDesv.intValue()<0){
    		objMaxDesv = new Integer(0);
    	}
    	int z=0;
    	for(z=0; z<objNumDocs.intValue();z++){
			String content = getDocContentFromJson(jsonTokens, objMaxDesv.intValue());
			//log4j.debug("Contenido "+(z+1)+":" + content );
			String idField = "token_".concat(String.valueOf(z));
			sendSolr(classifierDto.getDocType(),idField,
	   					new StringBuilder("/").append(idArea).
	   					append("/").append(idField).toString(), content );
			AppUtily.delayTime(Constante.DELAYTIME);/* simula un tiempo de respuesta */
		}
    	log4j.debug("Se enviaron a Solr "+z+" Documentos ");
    }
    /**
     * Realiza la iteración de tokens siguiendo el algoritmo diseñado
     * @param jsPares
     * @param mxDesv
     * @return
     * @throws Exception
     */
    private static String getDocContentFromJson(String jsPares, int mxDesv) throws Exception {
		if(mxDesv>100){
			mxDesv=100;
		}
		StringBuilder finalContent = new StringBuilder();
		try{
			JSONObject jsObj = new JSONObject(jsPares);
		    @SuppressWarnings("unchecked")
			Iterator<String> a = jsObj.keys();
		    while(a.hasNext()) {
		    	String key = a.next();
		        Integer value = (Integer)jsObj.get(key);
		        int weight = AppUtily.getWeight(value,(short)mxDesv);  
		        finalContent.append(AppUtily.repeat(key, " ", weight)).append(" ");
		        /*log4j.debug("weight<"+key+">: " + weight); //*/
		    }
		}catch (JSONException je){
			log4j.error("<getDocContentFromJson>\nError al convertir cadena en JSON, string:"+jsPares, je);
			throw je;
		}
	    
	    return finalContent.toString();
	}
    
    /**
     * Se crean documentos en Solr
     * @param docType, es el tipo de documento
     * @param idField, es el id del documento
     * @param target, es la preclasificación
     * @param content, es el contenido o cuerpo del documento
     * @throws Exception
     */
    private void sendSolr(String docType,String idField, String target, 
    					  String content) throws Exception{
    	SolrDocTopics solrDocTopics = new SolrDocTopics();
		solrDocTopics.setId(idField);
		solrDocTopics.setDoctype(Integer.valueOf(docType));
		solrDocTopics.setTarget(target);
		solrDocTopics.setModelversion(Constante.MODEL_VERSION_INICIAL);
		solrDocTopics.setClassified(String.valueOf(Constante.CLASSIFICATION_STATUS_PRECLASSIFIED));
		solrDocTopics.setContent(content);
		log4j.debug("<loadCvsIntoSolr> solrDoc:");
		log4j.debug(solrDocTopics.toString());
		//solrDocService.addToIndexCoreClass(solrDocTopics);
		
    }
    
    
    /*  ************************************************************************************* */
    /*  ***************************   D U M M Y  ******************************************** */
    /*  ************************************************************************************* */


	@Override
	public String getCatalogueResponse(CatalogueDto dto, String uriService) {
		try{
			log4j.debug("<getCatalogueResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getCatalogueResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}

	public String getResponse(String stJson, String uriService) {
		try{
			log4j.debug("<getResponse> uriService " + uriService );
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}

	@Override
	public String getClassifyResponse(DocumentoClasificacionDto dto,
			String uriService) {
		try{
			log4j.debug("<getClassifyResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getClassifyResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}
	
	public String getClassifyUpdateResponse(String stJson, String uriService) {
		try{
			log4j.debug("<getClassifyUpdateResponse> uriService " + uriService );
			JSONArray jsonArray = new JSONArray(stJson);
			if(jsonArray.length()>0){
				JSONObject jsonUno = jsonArray.getJSONObject(0);
				validated=validate(uriService, jsonUno);
				return "[]";	//TODO verificar funcionalidad real
			}
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + " [No hay documentos a actualizar]"));
		}catch (Exception e){
			log4j.error("<getClassifyUpdateResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}


	@Override
	public String getHandshakeResponse(HandShakeDto dto, String uriService) {
		try{
			log4j.debug("<getHandshakeResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getHandshakeResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}


	@Override
	public String getEnterpriseParameterResponse(EmpresaParametroDto dto,
			String uriService) {
		try{
			log4j.debug("<getEnterpriseParameterResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getEnterpriseParameterResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}

}
