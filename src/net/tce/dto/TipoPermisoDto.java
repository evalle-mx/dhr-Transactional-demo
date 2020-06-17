package net.tce.dto;

public class TipoPermisoDto extends ComunDto {

	private String idTipoPermiso;
	private String descripcion;
	private String significado;
	private String estatusRegistro;
	
	public TipoPermisoDto(){}
	
	public TipoPermisoDto(byte idTipoPermiso,String descripcion,String significado, Boolean estatusRegistro){
		this.idTipoPermiso=String.valueOf(idTipoPermiso);
		this.descripcion=descripcion;
		this.significado=significado;
		this.estatusRegistro= (estatusRegistro != null ? estatusRegistro.toString():null);
	}
	
	
	public String getIdTipoPermiso() {
		return idTipoPermiso;
	}
	public void setIdTipoPermiso(String idTipoPermiso) {
		this.idTipoPermiso = idTipoPermiso;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSignificado() {
		return significado;
	}
	public void setSignificado(String significado) {
		this.significado = significado;
	}
	public String getEstatusRegistro() {
		return estatusRegistro;
	}
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}
}
