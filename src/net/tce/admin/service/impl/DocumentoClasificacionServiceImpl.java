package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mock.AppEndPoints;
import net.tce.admin.service.DocumentoClasificacionService;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import com.google.gson.Gson;

@Transactional
@Service("documentoClasificacionService")
public class DocumentoClasificacionServiceImpl extends BaseMockServiceImpl implements DocumentoClasificacionService {
	
	Logger log4j = Logger.getLogger( DocumentoClasificacionServiceImpl.class );
	
	private String paramsGet = "idEmpresaConf";
	private JSONArray jsResponse;
	
	@Autowired
	Gson gson;

	@Override
	public Object get(DocumentoClasificacionDto docClasificacionDto) {
		log4j.debug("<get>");
		log4j.debug("<get> dto "+ docClasificacionDto );
		jsResponse = null;
		try{
			String stJson = gson.toJson(docClasificacionDto);
			JSONObject json = new JSONObject(stJson);
			log4j.debug("json: " + json);
			String uriService = AppEndPoints.SERV_DOCSCLASS_G;
			log4j.debug("Servicio DOCSCLASS.G");
			json = filtros(json, paramsGet);
			if(!json.has("code")){
				String stDocsClass = getJsonFile(uriService);
				String pSincro=null, pAuto=null, pEstatus=null, pArea=null;
				JSONObject jsonDoc;
				
				if(json.has("sincronizado")){
					pSincro = json.getString("sincronizado");
				}
				if(json.has("automatico")){
					pAuto = json.getString("automatico");
				}
				if(json.has("estatusClasificacion")){
					pEstatus = json.getString("estatusClasificacion");
				}
				if(json.has("idArea")){
					pArea = json.getString("idArea");
				}

//				log4j.debug("stDocsClass "+stDocsClass );
				jsResponse = new JSONArray(stDocsClass);
				log4j.debug("\npSincro=" + pSincro
						+ "\npAuto="+pAuto
						+ "\npEstatus="+pEstatus
						+ "\npArea="+pArea 
						);
				
				if(jsResponse.length()>0){
					boolean agregar = true;
					JSONArray jsOut = new JSONArray();
					for(int x=0; x<jsResponse.length();x++){
						jsonDoc = jsResponse.getJSONObject(x);
						log4j.debug("("+x+") jsonDoc: " + jsonDoc );
						agregar = true; //Por defecto se agrega a menos q no cumpla un pParam
						
						if(pSincro!=null && !jsonDoc.getString("sincronizado").equals(pSincro)){
							log4j.debug("Se omite por pSincro");
							agregar = false;
						}
						if(pAuto!=null && !jsonDoc.getString("automatico").equals(pAuto)){
							log4j.debug("Se omite por pAuto");
							agregar = false;
						}
						if(pEstatus!=null && !jsonDoc.getString("estatusClasificacion").equals(pEstatus)){
							log4j.debug("Se omite por pEstatus");
							agregar = false;
						}
						if(pArea!=null){
							if(!jsonDoc.has("idArea") || !jsonDoc.getString("idArea").equals(pArea)){
								log4j.debug("Se omite por (falta de) pArea");
								agregar = false;
							}
						}
						if(agregar){
							log4j.debug("Se agrega json!!! ");
							jsOut.put(jsonDoc);
						}
					}
					return jsOut.toString();
				}else{
					return jsResponse.toString();
				}
				
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<get> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}

	@Override
	public String update(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadTokens(String dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String classifyByLot(DocumentoClasificacionDto docClasificacionDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
