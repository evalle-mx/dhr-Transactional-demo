package net.tce.admin.service.impl;

import net.mock.AppEndPoints;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.MensajeDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase donde se aplica las politicas de negocio del servicio de enterprise
 * @author dothrDev
 *
 */
@Transactional
@Service("empresaParametroService")
public class EmpresaParametroServiceImpl extends BaseMockServiceImpl implements EmpresaParametroService {
	
	static Logger log4j = Logger.getLogger( EmpresaParametroServiceImpl.class );

	private String param_Get = "idEmpresaConf,idTipoParametro";
	private String param_Update = "idEmpresaConf,idEmpresaParametro";
	private String paramsCREATE = "idEmpresaConf,idConf,orden";
	private String paramsDELETE = "idEmpresaConf,idEmpresaParametro";
	private JSONArray jsResp;
	
	@Autowired
	Gson gson;
	
	@Override
	//public Object get(EmpresaParametroDto dto, boolean usarCache) {
	public String get(EmpresaParametroDto dto){
		log4j.debug("<get>\n IdTipoParametro="+dto.getIdTipoParametro()+
				"\n IdEmpresaConf="+dto.getIdEmpresaConf() + 
				"\n Contexto="+dto.getContexto());
		try {
			log4j.debug("<get> uriCode: EPARAMS.G, uriService: " + AppEndPoints.SERV_EPARAMS_G );
			
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, param_Get);
			
			if(!json.has("code")){
				String idTipoParam = dto.getIdTipoParametro();
				
				if(idTipoParam.equals("1") || idTipoParam.equals("5") || idTipoParam.equals("8")){
					String stArray = AppUtily.getJsonFile(AppEndPoints.SERV_EPARAMS_G+"-"+idTipoParam);
					log4j.debug("<get> stArray: " + stArray );
					jsResp = new JSONArray(stArray);
					
					if(dto.getContexto()!=null){
						log4j.debug("<get> \n\n Se busca por Contexto: " + dto.getContexto() );
						JSONArray jsResp2 = new JSONArray();
						//for (JSONObject jsonOb : jsResp) {
						for(int x=0; x<jsResp.length();x++){
							json = jsResp.getJSONObject(x);
							log4j.debug("<get> json: "+json );
							if(json.has("contexto") && json.getString("contexto").equals(dto.getContexto())){
								log4j.debug("<get> Se agrega a jsResp2");
								jsResp2.put(json);
							}
						}
						jsResp = jsResp2;
					}
					log4j.debug("<get> regresando jsResp:\n"+jsResp);
					return jsResp.toString();
				}else{
					log4j.debug("<get> No existe datos en Empresa_parametro para el tipo de Parametro "+idTipoParam);
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
							Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR));
				}
			}
			else{
				log4j.debug("<reloadCache> Inconsistencia detectada en filtros");
				return json.toString();
			}
			
		} catch (Exception e) {
			log4j.error("<get> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	//public Object update(EmpresaParametroDto empresaParametroDto) {
	public String update(EmpresaParametroDto empresaParametroDto){
		log4j.debug("<update> ");
		try {
			log4j.debug("<get> uriCode: EPARAMS.U, uriService: " + AppEndPoints.SERV_EPARAMS_U );
			String stJson = gson.toJson(empresaParametroDto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, param_Update);
//			String idEmpresaParam = empresaParametroDto.getIdEmpresaParametro();			
			if(!json.has("code")){
				return "[]";
			}else{
				log4j.debug("<reloadCache> Inconsistencia detectada en filtros: \n"+json.toString());
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						json.has("code")?json.getString("code"):Mensaje.SERVICE_CODE_000, json.has("type")?json.getString("code"):Mensaje.SERVICE_TYPE_FATAL,
						json.has("message")?json.getString("message"):Mensaje.MSG_ERROR));
			}
		}catch (Exception e) {
			log4j.error("<get> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	//public Object updateMultiple(String jsonArray) {
	public String updateMultiple(String jsonArray) {
		log4j.debug("<updateMultiple> ");
//		EmpresaParametroDto empresaParametroDto;
		StringBuilder sbResponse = new StringBuilder("[");
		log4j.debug("<get> uriCode: EPARAMS.M, uriService: " + AppEndPoints.SERV_EPARAMS_M );
		try{
			JSONArray arrayDto = new JSONArray(jsonArray);
			EmpresaParametroDto empParamDto;
			for(int x=0;x<arrayDto.length();x++){
				log4j.debug("<updateMultiple> "+x+" "+ arrayDto.getJSONObject(x).toString() );
				empParamDto = gson.fromJson(arrayDto.getJSONObject(x).toString(), EmpresaParametroDto.class);
				log4j.debug("<updateMultiple> Conversión exitosa a DTO: " + empParamDto.getIdEmpresaParametro() );
//				empParamDto.setActualizaCache(false);//==>Se actualiza de manera Masiva al final de la iteración
				
				Object objRsp = update(empParamDto);
				log4j.debug("<updateMultiple> se obtuvo respuesta: "+objRsp );
				if(objRsp instanceof String){
					if(String.valueOf(objRsp).equals(Mensaje.SERVICE_MSG_OK_JSON)){
						log4j.debug("<updateMultiple> update exitoso, se omite respuesta");
					}else{
						log4j.debug("<updateMultiple> update no Exitoso, agregando cadena");
						sbResponse.append(objRsp).append(x==arrayDto.length()-1?"":",")
						.append("\n");
						
					}
				}
				else if(objRsp instanceof EmpresaParametroDto){
					log4j.debug("<updateMultiple> update no Exitoso, se obtuvo EmpresaParametroDto");
					//empDtoRsp = (EmpresaParametroDto)objRsp;
					sbResponse.append(new GsonBuilder().disableHtmlEscaping().create().toJson(objRsp))
					.append(x==arrayDto.length()-1?"":",")
					.append("\n");
				}
				else{
					log4j.debug("<updateMultiple> update no Exitoso, se obtuvo respuesta no esperada");
					sbResponse.append(objRsp).append(x==arrayDto.length()-1?"":",").append("\n");
				}
			}
			
			// ACTUALIZAR el cache se llamará de manera explicita
			sbResponse.append("]");
			log4j.debug("<updateMultiple> \n\n sbResponse: \n"+sbResponse);
			return sbResponse.toString();
			
		}catch (Exception e){
			log4j.fatal("<updateMultiple> Excepcion: ", e);
//			empresaParametroDto=new EmpresaParametroDto();
//			empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
//			empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//			empresaParametroDto.setCode(Mensaje.SERVICE_CODE_000);
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_000, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR));
		}
		
//		return empresaParametroDto;
	}

	@Override
	//public Object reloadCache(EmpresaParametroDto empresaParametroDto) {
	public String reloadCache(EmpresaParametroDto empresaParametroDto) {
		log4j.debug("<reloadCache> ");
		log4j.debug("<reloadCache>\n IdTipoParametro="+empresaParametroDto.getIdTipoParametro()+
				"\n IdEmpresaConf="+empresaParametroDto.getIdEmpresaConf());
		try {
			log4j.debug("<get> uriCode: EPARAMS.RL, uriService: " + AppEndPoints.SERV_EPARAMS_RL );
			
			String stJson = gson.toJson(empresaParametroDto);
			JSONObject json = new JSONObject(stJson);
			json = filtros(json, param_Get);
			
			if(!json.has("code")){
				String idConf=empresaParametroDto.getIdConf()!=null?empresaParametroDto.getIdConf():"0";
				String idTipoParam=empresaParametroDto.getIdTipoParametro();
				String keyCache=idConf+idTipoParam;
				

				empresaParametroDto=new EmpresaParametroDto();
//				empresaParametroDto.setMessage("Se ha actualizado correctamente la información para "+keyCache);
//				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
//				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_005);
//				return empresaParametroDto;
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						Mensaje.SERVICE_CODE_005, Mensaje.SERVICE_TYPE_INFORMATION,
						"Se ha actualizado correctamente la información para "+keyCache));
			}
			else{
				log4j.debug("<reloadCache> Inconsistencia detectada en filtros");
				return json.toString();
			}
		
		}catch (Exception e) {
				log4j.error("<get> Exception ", e );
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
						Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String create(EmpresaParametroDto empresaParametroDto)
			throws SystemTCEException {
		String resultado = "";
		try{
			log4j.debug("<create> " );
			JSONObject json = new JSONObject(gson.toJson(empresaParametroDto));
			json = filtros(json, paramsCREATE);
			
			if(!json.has("code")){
				resultado = getJsonCreateResp(json, "idEmpresaParametro");
				return resultado;
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<create> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String delete(EmpresaParametroDto empresaParametroDto)
			throws SystemTCEException {
		String resultado = "";
		try{
			log4j.debug("<delete> " );
			JSONObject json = new JSONObject(gson.toJson(empresaParametroDto));
			json = filtros(json, paramsDELETE);
			
			if(!json.has("code")){
				resultado = getJsonDeleteResp(json, "idEmpresaParametro");
				
				return resultado;
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<delete> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

}
