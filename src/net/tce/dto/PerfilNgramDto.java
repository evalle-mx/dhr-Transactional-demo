package net.tce.dto;

public class PerfilNgramDto {
	private String descripcion;
	private short ponderacion;

	
	public PerfilNgramDto(){}
	
	public PerfilNgramDto(String descripcion,short ponderacion){
		this.descripcion=descripcion;
		this.ponderacion=ponderacion;
	}
	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setPonderacion(short ponderacion) {
		this.ponderacion = ponderacion;
	}
	public short getPonderacion() {
		return ponderacion;
	}
}
