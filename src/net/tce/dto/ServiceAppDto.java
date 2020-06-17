package net.tce.dto;

import java.util.List;

import net.tce.util.AppUtily;

import org.json.JSONObject;
/**
 * DTO particular de Mock para proceso de validación de parametros en servicios <br>
 * [La información esta relacionada con la tabla Permiso ]
 * @author dothrDeveloper
 *
 */
public class ServiceAppDto extends ComunDto {
	
	private String uriCode;
	private String uriService;
	private String descripcion;
	private String parametros;
	private String stJsonRequest;
	private JSONObject jsRequest;
	private List<String> paramsList;
	
	public ServiceAppDto() {
	}
	
	public ServiceAppDto(String uriService, JSONObject json) {
		this.uriService = uriService;
		this.jsRequest = json;
	}
	
	public String getUriCode() {
		return uriCode;
	}
	public void setUriCode(String uriCode) {
		this.uriCode = uriCode;
	}
	public String getUriService() {
		return uriService;
	}
	public void setUriService(String uriService) {
		this.uriService = uriService;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public String getStJsonRequest() {
		return stJsonRequest;
	}
	public void setStJsonRequest(String stJsonRequest) {
		this.stJsonRequest = stJsonRequest;
	}
	public JSONObject getJsRequest() {
		return jsRequest;
	}
	public void setJsRequest(JSONObject jsRequest) {
		this.jsRequest = jsRequest;
	}
	public List<String> getParamsList() {
		if(this.paramsList==null && this.parametros!=null){
			this.paramsList = AppUtily.stTokens2List(this.parametros, ",");
					//Arrays.asList(this.parametros.split("\\s*,\\s*")); //Marca: java.lang.UnsupportedOperationException al agregar datos nuevos
		}
		return paramsList;
	}
	//Se comenta pues no se hace set explicitamente, sino se obtienen cuando parametros tiene valor
//	public void setParamsList(List<String> paramsList) {
//		this.paramsList = paramsList;
//	}
	
	public void resetValid(){
		setCode(null);
		setType(null);
		setMessage(null);
		setMessages(null);
	}
}
