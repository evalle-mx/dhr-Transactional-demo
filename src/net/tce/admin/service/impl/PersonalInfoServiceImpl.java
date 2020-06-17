package net.tce.admin.service.impl;

import net.mock.AppEndPoints;
import net.tce.admin.service.PersonalInfoService;
import net.tce.dto.ContactInfoDto;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.SkillDto;
import net.tce.dto.SettlementDto;
import net.tce.exception.SystemTCEException;
import net.tce.util.AppUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
/**
 * Emula acceso a la información de persona/empresa :
 * <ul>
 * <li>Contacto</li>
 * <li>Localizacion</li>
 * <li>Settlement</li>
 * </ul>
 * @author Netto
 *
 */
@Transactional
@Service("personalInfoService")
public class PersonalInfoServiceImpl extends BaseMockServiceImpl implements PersonalInfoService {

	boolean validated = false;
	static Logger log4j = Logger.getLogger( PersonalInfoServiceImpl.class );
	protected final String uriRootContacto = "/module/contact";
	protected final String uriRootLocation = "/module/location";
	protected final String uriRootSettlement = "/module/settlement";
	
	@Autowired
	Gson gson;
	
	/**
	 * Procesa el Json para modulo de CONTACT
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
			/* contact (contacto) */
			if(uriService.equals(AppEndPoints.SERV_CONTACT_C)){
				log4j.debug("Servicio CONTACT.C");
				if(!json.has("idPersona") && !json.has("idEmpresa") && !json.has("idPosicion")){
					throw new SystemTCEException(" -Faltan parametros: idPersona/idEmpresa/idPosicion-");
				}
				resultado = getJsonCreateResponse(json, "contact");
			}
			else if(uriService.equals(AppEndPoints.SERV_CONTACT_U)){
				log4j.debug("Servicio CONTACT.U");				
				//*//Codigo para pruebas
				log4j.debug(String.valueOf(json.getInt("idContacto")).equals("225"));
				if(String.valueOf(json.getInt("idContacto")).equals("225")){
					resultado = AppUtily.getJsonMessage("006","E",null);
				}else{
					resultado = DEF_UPDATE_RESP;
				} //*/ 
				//resultado = DEF_UPDATE_RESP;
			}
			else if(uriService.equals(AppEndPoints.SERV_CONTACT_D)){
				log4j.debug("Servicio CONTACT.D ");
				resultado = getJsonDeleteResponse(json, "contact");
			}
			/* Localizacion/Settlement */
			else if(uriService.equals(AppEndPoints.SERV_LOCATION_D)){
				log4j.debug("Servicio LOCATION.D ");
				resultado = //getJsonFile(uriService); 
				getJsonDeleteResponse(json, "location");
			}
			else if(uriService.equals(AppEndPoints.SERV_SETTLEMENT_C)){
				log4j.debug("Servicio SETTLEMENT.C ");
				if(!json.has("idPersona") && !json.has("idEmpresa") && !json.has("idPosicion")){
					throw new SystemTCEException(" -Faltan parametros: idPersona/idEmpresa/idPosicion-");
				}
				resultado = getJsonFile(uriService); //getJsonCreateResponse(json, "settlement");
			}
			else {
				resultado = getJsonFile(uriService); /* Default */
			}
		}catch (SystemTCEException ex){
			throw ex;
		}
		catch (Exception ex){
			log4j.fatal("Error al procesar Response", ex);
			resultado = AppUtily.getJsonErrorMessage();
		}
//		log4j.debug("resultado..." + resultado);
		return resultado;
	}

	/**
	 * Metodo que convierte el String del dto en un JSONObject ocupado en el procedimiento Dummy
	 * @param stJson
	 * @param uriService
	 * @return
	 */
	@Override
	public String getContactResponse(ContactInfoDto dto, String uriService) {
		try{
			log4j.debug("<getContactResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getContactResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_006, Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	/**
	 * Metodo que convierte el String del dto en un JSONObject ocupado en el procedimiento Dummy
	 * @param stJson
	 * @param uriService
	 * @return
	 */
	@Override
	public String getLocationResponse(LocationInfoDto dto, String uriService) {
		try{
			log4j.debug("<getLocationResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getLocationResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	/**
	 * Metodo que convierte el String del dto en un JSONObject ocupado en el procedimiento Dummy
	 * @param stJson
	 * @param uriService
	 * @return
	 */
	@Override
	public String getSettlementResponse(SettlementDto dto, String uriService) {
		try{
			log4j.debug("<getSettlementResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			boolean espera = AppUtily.delayTime(3000);
			if(espera){
				return getResult(uriService, json);				
			}
		}catch (Exception e){
			log4j.error("<getSettlementResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
		return null;
	}

	/**
	 * Metodo que convierte el String del dto en un JSONObject ocupado en el procedimiento Dummy
	 * @param stJson
	 * @param uriService
	 * @return
	 */
	@Override
	public String getPersonSkillResponse(SkillDto dto, String uriService) {
		try{
			log4j.debug("<getPersonSkillResponse> uriService " + uriService );
			String stJson = gson.toJson(dto);
			JSONObject json = new JSONObject(stJson);
			validated = validate(uriService, json);
			return getResult(uriService, json);
		}catch (Exception e){
			log4j.error("<getPersonSkillResponse> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
}
