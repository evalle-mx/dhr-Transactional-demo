package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mock.AppEndPoints;
import net.tce.admin.service.PermissionService;
import net.tce.dto.MensajeDto;
import net.tce.dto.PermisoDto;
import net.tce.util.AppUtily;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("permissionService")
public class PermissionServiceImpl extends BaseMockServiceImpl implements PermissionService {
	
	private static Logger log4j = Logger.getLogger( PermissionServiceImpl.class );
	
	private String paramsGET = "idEmpresaConf";
	private String paramsUPD = "idEmpresaConf,idPermiso";
	private String paramsCREATE = "idEmpresaConf,idTipoPermiso";
	private String paramsDELETE = "idEmpresaConf,idPermiso";
	
	@Autowired
	Gson gson;

	
	@Override
	public Object get(PermisoDto permisoDto) {
		try{
			log4j.debug("<get> " );
			String stJson = gson.toJson(permisoDto);
			JSONObject json = new JSONObject(stJson);
			String uriService = AppEndPoints.SERV_PERMISSION_G;
			
			json = filtros(json, paramsGET);		
			
			if(!json.has("code")){
				
				if(json.has("idTipoPermiso")||json.has("idRol")){ //Filtro para rol-Permiso
					if(json.has("idTipoPermiso") && !json.has("idRol")){
						log4j.debug("<get> ERROR: El idRol es requerido");
						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
								Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR + " El idRol es requerido"));
					}else if(json.has("idRol") && !json.has("idTipoPermiso")){
						log4j.debug("<get> ERROR: El idTipoPermiso es requerido");
						return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
								Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
								Mensaje.MSG_ERROR + " El idTipoPermiso es requerido"));
					}else {
						String idTipoPermiso = json.getString("idTipoPermiso");// 1 (uriCode) | 2 (Menú) | 3 (MenuCatalogo)
						
						if(idTipoPermiso.equals("1")){ //UriCode
							return getPermisos(json.getString("idRol"), "uriCodes");
						}else if(idTipoPermiso.equals("2")){ //Menú
							return getPermisos(json.getString("idRol"), "menu");
						}else if(idTipoPermiso.equals("3")){ //Menú-Catalogo
							return getPermisos(json.getString("idRol"), "catalogo");
						}
						else{
							log4j.debug("<get> No existe el tipo permiso solicitado");
							return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
									Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_ERROR,
									Mensaje.MSG_ERROR));
						}
					}
				}
				else {
					return getJsonFile(uriService); //Default
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
	public String update(PermisoDto permisoDto) {
		try{
			log4j.debug("<update> " );
			//String stJson = ;
			JSONObject json = new JSONObject(gson.toJson(permisoDto));
//			String uriService = AppEndPoints.SERV_PERMISSION_U;
			json = filtros(json, paramsUPD);
			
			if(!json.has("code")){
//				if(json.has("activo") 
//						//&& (json.getString("activo").equals("true") || json.getString("activo").equals("false")) 
//						){
					return "[]";
//				}else{
//					log4j.error("<update> Valor de activo Inválido (True/False)");
//					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
//							Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
//							Mensaje.MSG_ERROR ));
//				}
				
				
			}
			else{
				return json.toString();
			}
		}catch (Exception e){
			log4j.error("<update> Exception ", e );
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR + e.getMessage()));
		}
	}
	
	/**
	 * Metodo genérico para obtener el listado de permisos total, con bandera de activo/inactivo 
	 * en el servicio <b>/admin/permission/get</b> 
	 * @param idRol
	 * @param tipoPermiso
	 * @return
	 * @throws Exception
	 */
	private String getPermisos(String idRol, String tipoPermiso) throws Exception{
		log4j.debug("<getUricodes> ");
		
//		String stPermByUser =getJsonFile(Constante.pathUricodesByUser); 
		JSONArray jsIds, jsPermisos;
		JSONObject jsonUsr = AppUtily.getJsonUser(idRol);
//				null, jsonTmp;
//		//1 encuentra el json de Usuario
//		for(int x=0; x<jsArr.length();x++){
//			jsonTmp = jsArr.getJSONObject(x);
//			if(idRol.equals(jsonTmp.getString("idRol"))){
//				jsonUsr = jsonTmp;
//			}
//		}
		//2 si existe tipo de usuario, se obtienen lista uricode completa y luego se asigna true si se encuentra en el arreglo de permisos por usuario
		if(jsonUsr!=null){
			jsIds = jsonUsr.getJSONArray(tipoPermiso); // uriCodes | menu | catalogo
			log4j.debug(jsIds);
			jsPermisos = jsPermisosByRol(AppEndPoints.SERV_PERMISSION_G+"-"+tipoPermiso, idRol, jsIds);
			log4j.debug("<getUricodes> \n"+jsPermisos.length() + " permisos URICODE para " + jsonUsr.getString("nombre") );
			return jsPermisos.toString();
		}else{
			log4j.fatal("<getUricodes> No existe tipo de USUARIO");
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null, null,
					Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR ));
		}
	}
	
	/**
	 * Regresa un arreglo de json Permiso, obtenido del archivo <uriJson>.json de permisos, con activo/inactivo por Rol (tipo_relacion)
	 * @param uriJson
	 * @param idRol
	 * @param jsIds
	 * @return
	 * @throws Exception
	 */
	private JSONArray jsPermisosByRol(String uriJson, String idRol, JSONArray jsIds) throws Exception {
		JSONArray jsPermisos = new JSONArray();
		String stUricodes =getJsonFile(uriJson);
		JSONArray jsArr = new JSONArray(stUricodes);
		if(idRol.equals(String.valueOf( Constante.ROL_ADMINISTRADOR ))){  //Constante.TIPO_RELACION_ADMINISTRADOR))){ //ROL administrador, tiene todos los permisos por default
			jsPermisos = jsArr;
		}else{
			JSONObject jsonTmp;
			for(int y=0; y<jsArr.length();y++){
				jsonTmp = jsArr.getJSONObject(y);
				jsonTmp.put("activo", false);
				for(int z = 0; z<jsIds.length(); z++){
					Integer idPermiso = jsIds.getInt(z);
					if(jsonTmp.getInt("idPermiso") == idPermiso){
						jsonTmp.put("activo", true);
					}
				}
				jsonTmp.put("idTipoRelacion", idRol);
				jsonTmp.put("idTipoRelacionPermiso", idRol+jsonTmp.getInt("idPermiso"));
				jsPermisos.put(jsonTmp);
			}
		}		
		return jsPermisos;
	}
	
//	/**
//	 * Regresa un arreglo de json Permiso, obtenido del archivo <uriJson>.json de permisos, filtrado por 
//	 * rol <br> EMULA EL SERVICIO DE URICODES <b>PERSON.UC=/module/curriculumManagement/uricodes</b>
//	 * @param uriJson
//	 * @param idRol
//	 * @param jsIds
//	 * @return
//	 * @throws Exception
//	 */
//	private JSONArray jsPermisosByRol(String uriJson, String idRol, JSONArray jsIds) throws Exception {
//		JSONArray jsPermisos = new JSONArray();
//		String stUricodes =getJsonFile(uriJson);
//		JSONArray jsArr = new JSONArray(stUricodes);
//		if(idRol.equals(String.valueOf(Constante.TIPO_RELACION_ADMINISTRADOR))){ //ROL administrador, tiene todos los permisos por default
//			jsPermisos = jsArr;
//		}else{
//			JSONObject jsonTmp;
//			for(int y=0; y<jsArr.length();y++){
//				jsonTmp = jsArr.getJSONObject(y);
////				log4j.debug("permiso: " + jsonTmp + " esta activado para " + jsonUsr.getString("nombre"));
//				for(int z = 0; z<jsIds.length(); z++){
//					Integer idPermiso = jsIds.getInt(z);
////					log4j.debug("idPermiso: " + idPermiso );
//					if(jsonTmp.getInt("idPermiso") == idPermiso){
////						log4j.debug("idPermiso: " + idPermiso );
////						log4j.debug("Activado");
//						jsonTmp.put("idTipoRelacion", idRol);
//						jsPermisos.put(jsonTmp);
//					}
//				}
//			}
//		}		
//		return jsPermisos;
//	}

	
	public static void main(String[] args) {
		PermissionServiceImpl impl = new PermissionServiceImpl();
		try {
			String uriCodes = impl.getPermisos("12", "uriCodes");	// menu | 
			System.out.println(uriCodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String create(PermisoDto permisoDto) {
		String resultado = "";
		try{
			log4j.debug("<create> " );
			JSONObject json = new JSONObject(gson.toJson(permisoDto));
			json = filtros(json, paramsCREATE);
			
			if(!json.has("code")){
				resultado = getJsonCreateResp(json, "idPermiso");
				
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
	public String delete(PermisoDto permisoDto) {
		String resultado = "";
		try{
			log4j.debug("<delete> " );
			JSONObject json = new JSONObject(gson.toJson(permisoDto));
			json = filtros(json, paramsDELETE);
			
			if(!json.has("code")){
				resultado = getJsonDeleteResp(json, "idPermiso");
				
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
