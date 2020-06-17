package net.tce.admin.service.impl;

import java.util.Date;

import net.mock.AppEndPoints;
import net.tce.admin.service.CompanyService;
import net.tce.dto.CurriculumEmpresaDto;
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
 * Clase donde se emulan las politicas de negocio del servicio CurriculumEmpresa
 * @author Netto
 *
 */
@Transactional
@Service("companyService")
public class CompanyServiceImpl extends BaseMockServiceImpl implements CompanyService {

	boolean validated = false;
	static Logger log4j = Logger.getLogger( CompanyServiceImpl.class );
	final String uriRoot = "/module/curriculumCompany/";
	private JSONArray empresas = null;
	
	@Autowired
	Gson gson;
	
	
	protected void testEmpresasPorPersona(String idPersona){
		CompanyServiceImpl impl = new CompanyServiceImpl();
		StringBuilder sb = new StringBuilder();
		try {
			JSONArray empresasTest = impl.getEmpresasForPersona(idPersona, impl.getEmpresasArray(), impl.getEmpresaRelacionArray());
			log4j.debug("************\n" + empresasTest);
			
			
			for(int y=0; y<empresasTest.length(); y++){
				//log4j.debug(empresasTest.get(y));
				sb.append(empresasTest.get(y)).append("\n");
			}
			log4j.debug("  EMPRESAS:  \n" + sb.toString() );
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testProcessGet(){
		try{
			JSONObject json = new JSONObject();
			json.put("idEmpresaConf", "1");
			json.put("idPersona", "3");
			
			
			String res = getResult("/module/curriculumCompany/get", json);
			System.out.println(res);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompanyServiceImpl impl = new CompanyServiceImpl();
		//impl.testEmpresasPorPersona("3");
		impl.testProcessGet();
	}

	
	/**
	 * Procesa el Json para modulo de COMPANY
	 * @param uriService
	 * @param jsString
	 * @return
	 */
	public String getResult(String uriService, JSONObject jsInput) throws SystemTCEException  {
		String resultado = "[]";
		if(jsInput==null){
			return AppUtily.getJsonErrorMessage();
		}
		try{
			if(uriService.equals(AppEndPoints.SERV_COMPANY_G)){
				log4j.debug("Servicio COMPANY.G");
				resultado = processGet(jsInput);
			}
			else if(uriService.equals(AppEndPoints.SERV_COMPANY_C)){
				log4j.debug("Servicio COMPANY.C");
				resultado = processCreate(jsInput);
			}
			else if(uriService.equals(AppEndPoints.SERV_COMPANY_R)){
				log4j.debug("Servicio COMPANY.R");
				resultado = processRead(jsInput);
			}
			else if(uriService.equals(AppEndPoints.SERV_COMPANY_FS)){
				log4j.debug("Servicio COMPANY.FS");
				resultado = processFindSimilar(jsInput);
			}
			else if(uriService.equals(AppEndPoints.SERV_ASSOCIATE_G)){
				log4j.debug("Servicio ASSOCIATE.G");
				resultado = processGetAssociates(jsInput);
			}
			
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
		}
		
		return resultado;
	}
	
	
	/**
	 * Obtiene las empresas relacionadas a una persona
	 * @param jsonInput
	 * @return
	 * @throws Exception
	 */
	protected String processGet(JSONObject jsonInput) throws Exception {
		empresas = getEmpresasArray();
		JSONArray empresasResponse = new JSONArray();
		//if(empresas!=null && jsonInput.toString().indexOf("\"idPersona\"")!=-1){ /* valida que existe una relacion entre persona y empresa */
		if(empresas!=null && jsonInput.has("idPersona")){ 
			empresasResponse = getEmpresasForPersona(jsonInput.getString("idPersona"), empresas, getEmpresaRelacionArray());
		}else{
			log4j.debug("<ERROR>Se espera idPersona para validar si tiene acceso");
			return getJsonWarning("Sin Acceso a datos ...");
		}
		if(empresasResponse.length()<1){
			log4j.debug("<processGet> Warning, No hay empresas para persona, se envia mensaje acordado ...");
			return getJsonWarning(null);
		}
		return empresasResponse.toString();
	}
	
	/**
	 * Procesa la lectura de una empresa (existente en el archivo get.json) para obtener
	 * información completa
	 * @param jsonInput
	 * @return
	 * @throws Exception
	 */
	protected String processRead(JSONObject jsonInput) throws Exception {
		empresas = getEmpresasArray();
		JSONArray empresasResponse = new JSONArray();
		
		if(jsonInput.has("idEmpresa")){
			JSONObject empresa;
			for(int x=0;x<empresas.length();x++){
				empresa = empresas.getJSONObject(x);
				if(empresa.getString("idEmpresa").equals(jsonInput.getString("idEmpresa"))){
					empresasResponse.put(empresa);
				}
			}
		}else{
			log4j.debug("<ERROR>Se requiere idEmpresa en la busqueda");
			return getJsonErrorMessage();
		}
		if(empresasResponse.length()<1){
			return getJsonWarning(null);
		}
		return empresasResponse.toString();
	}
	
	/**
	 * Procesa la solicitud de creacion de empresa, y en su caso genera nuevo archivo read-empresa.json
	 * @param json
	 * @return
	 * @throws Exception
	 */
	protected String processCreate(JSONObject json) throws Exception {
		String res = null;
		//[{"name":"idEmpresa","value":"22","code":"004","type":"I"}]
		try{
			if(json!=null ){
				if(json.toString().indexOf("\"rfc\"")!=-1){
					JSONArray empresasTemp = findSimilar(json);
					if(empresasTemp!=null && empresasTemp.length()==0){
						Long idTmp = new Date().getTime();
						String idEmpresaNueva = String.valueOf(idTmp).substring(10);	//.substring(0, 1);
						json.put("idEmpresa", idEmpresaNueva);
						json.put("idEstatusInscripcion", "1");
						json.put("estaVerificado", "false");
						log4j.debug("Creando Nueva Empresa");
						AppUtily.writeStringInFile(Constante.JSONFILES_ROOT_DIR+"/module/curriculumCompany/read-" + idEmpresaNueva +".json", 
								json.toString(), false);
						JSONObject jsOutput = new JSONObject();

			    		jsOutput.put(Constante.PARAM_JSON_CODE, "004");
			    		jsOutput.put(Constante.PARAM_JSON_NAME, "idEmpresa");
			    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_INFORMATION);
			    		jsOutput.put(Constante.PARAM_JSON_VALUE, idEmpresaNueva );
			    		return jsOutput.toString();
						
					}else {
						log4j.debug("Ya existen datos registrados "+json.getString("rfc"));
						//[{"name":"rfc","value":"ABC010101E5C","code":"009","type":"E","message":"Ya existen los datos registrados"}]
						JSONObject jsOutput = new JSONObject();

			    		jsOutput.put(Constante.PARAM_JSON_CODE, "009");
			    		jsOutput.put(Constante.PARAM_JSON_TYPE, Mensaje.SERVICE_TYPE_ERROR);
			    		jsOutput.put(Constante.PARAM_JSON_MESSAGE, Mensaje.SERVICE_DATA_EXISTS);

			    		jsOutput.put(Constante.PARAM_JSON_NAME, "rfc");
			    		jsOutput.put(Constante.PARAM_JSON_VALUE, json.getString("rfc") );
			    		return jsOutput.toString();
					}
				}else {
					throw new SystemTCEException(Mensaje.SERVICE_CODE_003, "Json de entrada no contiene RFC requerido ");
				}
				
			}else{
				throw new SystemTCEException(Mensaje.SERVICE_CODE_003, "Json de entrada es null");
			}
		}catch (Exception e){
			log4j.error("Error en Create Empresa ",e);
		}
		return res;
	}
	
	/**
	 * Realiza una busqueda de empresas con RFC similar al ingresado
	 * @param jsonInput
	 * @return
	 * @throws Exception
	 */
	protected String processFindSimilar(JSONObject jsonInput) throws Exception {
		JSONArray empresasResponse = findSimilar(jsonInput);
		return empresasResponse.toString();
	}
	
	/**
	 * Realiza una busqueda y filtrado de Personas asociadas
	 * @param jsonInput
	 * @return
	 * @throws Exception
	 */
	protected String processGetAssociates(JSONObject jsonInput) throws Exception {
		String stPersonas = getJsonFile("/module/curriculumCompany/getAssociates");
		JSONArray jsList = new JSONArray(stPersonas);
		JSONObject jsTmp = null;
		JSONArray jsAux = new JSONArray();
		//if(empresas!=null && jsonInput.toString().indexOf("\"idPersona\"")!=-1){ /* valida que existe una relacion entre persona y empresa */
		if(jsList!=null){
			String idEstatusInscripcion = null, idTipoRelacion = null;
			if(jsonInput.has("idEstatusInscripcion")){
				idEstatusInscripcion = jsonInput.getString("idEstatusInscripcion");
			}
			if(jsonInput.has("idTipoRelacion")){
				idTipoRelacion = jsonInput.getString("idTipoRelacion");
			}
			if(idEstatusInscripcion!=null || idTipoRelacion!=null){
				boolean filtrado = true;
				for(int x=0;x<jsList.length();x++){
					filtrado = true;
					jsTmp = jsList.getJSONObject(x);
					if(idEstatusInscripcion!=null && !jsTmp.getString("idEstatusInscripcion").equals(idEstatusInscripcion)){
						filtrado = false;
					}
					if(idTipoRelacion!=null && !jsTmp.getString("idTipoRelacion").equals(idTipoRelacion)){
						filtrado = false;
					}
					
					if(filtrado){
						jsAux.put(jsTmp);
					}
				}
			}else{
				jsAux = jsList;
			}
		}
		
		return jsAux.toString();
	}
	
	
	
	/* ***************************************************************************** */
	/**
	 * Recorre el arreglo de empresas para verificar duplicados
	 * @param jsonInput
	 * @return
	 * @throws Exception
	 */
	private JSONArray findSimilar(JSONObject jsonInput) throws Exception  {
		empresas = getEmpresasArray();
		JSONArray empresasResponse = new JSONArray();
		try{
			String rfc = null, razonSocial = null;
			if(jsonInput.toString().indexOf("\"rfc\":")!=-1){
				rfc = jsonInput.getString("rfc");
			}else{
				razonSocial = jsonInput.getString("razonSocial");
			}
			//TODO remover idTipoRelacion, tiposRelacion y privilegioAdm
			
			for(int i=0;i<empresas.length();i++){
				JSONObject jsTmEmpresa = empresas.getJSONObject(i);
				String tmRFC = jsTmEmpresa.getString("rfc");
				String tmRazon = jsTmEmpresa.getString("razonSocial");
				
				if(rfc!=null && tmRFC.equals(rfc.toUpperCase())){
					empresasResponse.put(jsTmEmpresa);
				}else if( rfc==null && (razonSocial!=null && tmRazon.toUpperCase().indexOf(razonSocial.toUpperCase())!=-1) ){
					empresasResponse.put(jsTmEmpresa);
				}
			}
		}catch (Exception e){
			log4j.error("Error al procesar Json entrada", e);
			throw e;
		}
		
		return empresasResponse;
	}
	/**
	 * Procesa las empresas con su relacion con la persona solicitante
	 * @param idPersona
	 * @param jsEmpresas
	 * @param jsRelaciones
	 * @return
	 */
	private JSONArray getEmpresasForPersona(String idPersona, JSONArray jsEmpresas, JSONArray jsRelaciones) {
		JSONArray jsEmpresasResponse = new JSONArray();
		try{

			JSONArray admins = null, assoc=null, tiposRelacionAdmin = new JSONArray("[9]"), tiposRelacionAsociado = new JSONArray("[1]");
			String administraEmp = "";
			String idEmpresa = null;
			// 1. Recorremos relaciones para obtener arreglo de administrador 
			for(int x=0; x<jsRelaciones.length(); x++){
				JSONObject jsonRelacionTemp = jsRelaciones.getJSONObject(x);
//				log4j.debug("jsonRelacionTemp: " + jsonRelacionTemp );
				admins =(JSONArray) jsonRelacionTemp.get("admins");
				boolean isAdmin = false;
				for(int y=0; y<admins.length(); y++){
					String jsAdmin = String.valueOf(admins.get(y));
//					log4j.debug(jsAdmin);
//					log4j.debug("iPersona==jsAdmin" + " [ "+idPersona+"=="+jsAdmin+"]" + (idPersona.equals(jsAdmin)));
					if(!isAdmin){
						isAdmin = (idPersona.equals(jsAdmin));
					}
					
				}
				//log4j.debug("fin de for");
				if(isAdmin){
					idEmpresa = jsonRelacionTemp.getString("idEmpresa");
					JSONObject jsEmp = getEmpresaById(idEmpresa, jsEmpresas);
					
					jsEmp.remove("tiposRelacion");
					jsEmp.put("tiposRelacion", tiposRelacionAdmin);
					jsEmp.remove("idTipoRelacion");
					jsEmp.put("idTipoRelacion", "9");
					//log4j.debug("Agregando empresa a la lista de admin");
					jsEmpresasResponse.put(jsEmp);
					administraEmp+="|"+idEmpresa;
				}
			}
			// 2. Recorremos relaciones para obtener arreglo de asociado 
			for(int z=0; z<jsRelaciones.length(); z++){
				JSONObject jsonRelacionTemp = jsRelaciones.getJSONObject(z);
				//log4j.debug("jsonRelacionTemp: " + jsonRelacionTemp );
				idEmpresa = jsonRelacionTemp.getString("idEmpresa");
				
				if(administraEmp.indexOf("|"+idEmpresa)==-1 && (jsonRelacionTemp.toString().indexOf("assoc")!=-1)){
					assoc =(JSONArray) jsonRelacionTemp.get("assoc");
					boolean isAssociate = false;
					for(int y=0; y<assoc.length(); y++){
						String jsAssoc = String.valueOf(assoc.get(y));
						//log4j.debug(jsAdmin);
						//log4j.debug("iPersona==jsAdmin" + " [ "+idPersona+"=="+jsAssoc+"]" + (idPersona.equals(jsAssoc)));
						if(!isAssociate){
							isAssociate = (idPersona.equals(jsAssoc));
						}
					}
					//log4j.debug("fin de for");
					if(isAssociate){
						JSONObject jsEmp = getEmpresaById(idEmpresa, jsEmpresas);
						
						jsEmp.remove("tiposRelacion");
						jsEmp.put("tiposRelacion", tiposRelacionAsociado);
						jsEmp.remove("idTipoRelacion");
						jsEmp.put("idTipoRelacion", "1");
						
						jsEmpresasResponse.put(jsEmp);
						administraEmp+="|"+idEmpresa;
					}
				}
			}
		}catch (Exception e){
			log4j.debug("Error al procesar empresas para persona ", e);
		}
		return jsEmpresasResponse;
	}
	
	/**
	 * Devuelve dentro del arreglo el Json de empresa con Id solicitado o null si no existe
	 * @param idEmpresa
	 * @param jsEmpresas
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getEmpresaById(String idEmpresa, JSONArray jsEmpresas) throws JSONException{
		for(int x=0; x<jsEmpresas.length(); x++){
			JSONObject jsonEmpresaTemp = jsEmpresas.getJSONObject(x);
			if(jsonEmpresaTemp.getString("idEmpresa").equals(idEmpresa)){
				return jsonEmpresaTemp;
			}
		}
		return null;
	}
	/**
	 * Obtiene Arreglo de Jsons de Empresas (Completa)
	 * (lee el get, transforma en JSONArray)
	 * @return
	 */
	private JSONArray getEmpresasArray(){
		JSONArray jsList = null;
		try{
			String stEmpresas = getJsonFile("/module/curriculumCompany/get");
			jsList = new JSONArray(stEmpresas);
		}catch (Exception e){
			log4j.error("Error al obtener listado de empresas", e);
			jsList = new JSONArray();
		}
		return jsList;
	}
	
	
	/**
	 * Obtiene arreglo de Empresas relacion con personas (admin y associate)
	 * @return
	 */
	private JSONArray getEmpresaRelacionArray(){
		JSONArray jsList = null;
		try{
			String stEmpresas = getJsonFile("/module/curriculumCompany/relEmpresas");
			jsList = new JSONArray(stEmpresas);
		}catch (Exception e){
			log4j.error("Error al obtener listado de relacion empresas", e);
			jsList = new JSONArray();
		}
		return jsList;
	}

	/**
	 * Metodo que convierte el String del dto en un JSONObject ocupado en el procedimiento Dummy
	 * @param stJson
	 * @param uriService
	 * @return
	 */
	@Override
	public String getCompanyResponse(CurriculumEmpresaDto dto, String uriService) {
		try{
			log4j.debug("<getCompanyResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getCompanyResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + "["+e.getMessage()+"]"));
		}
	}
}
