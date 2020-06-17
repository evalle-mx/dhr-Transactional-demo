package net.tce.dto;



public class WorkReferenceDto  extends ComunDto {
	private String idReferencia;
	private String idExperienciaLaboral;
	private String idTipoReferencia;
	private String nombre;
	private String apellido;
	
	public WorkReferenceDto(){}
		
	public WorkReferenceDto(String idEmpresaConf,String idExperienciaLaboral){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idExperienciaLaboral=idExperienciaLaboral;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getApellido() {
		return apellido;
	}
	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}
	public String getIdReferencia() {
		return idReferencia;
	}
	public void setIdExperienciaLaboral(String idExperienciaLaboral) {
		this.idExperienciaLaboral = idExperienciaLaboral;
	}
	public String getIdExperienciaLaboral() {
		return idExperienciaLaboral;
	}
	public void setIdTipoReferencia(String idTipoReferencia) {
		this.idTipoReferencia = idTipoReferencia;
	}
	public String getIdTipoReferencia() {
		return idTipoReferencia;
	}
}
