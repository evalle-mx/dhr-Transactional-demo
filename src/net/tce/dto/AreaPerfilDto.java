package net.tce.dto;

public class AreaPerfilDto extends ComunDto {

	private String idAreaPerfil;
	private String idArea;
	private String idPerfil;
	private String lbArea;
	private Boolean confirmada;
	private Boolean automatico;
	
	public AreaPerfilDto() {}
	
	public AreaPerfilDto(Long iAreaPerfil, Long iArea, Long iPerfil, String lbArea,
			Boolean confirmada, Boolean automatico) {
		this.idAreaPerfil=iAreaPerfil!=null?String.valueOf(iAreaPerfil):null;
		this.idArea=iArea!=null?String.valueOf(iArea):null;
		this.idPerfil=iPerfil!=null?String.valueOf(iPerfil):null;
		this.lbArea=lbArea;
		this.confirmada=confirmada;
		this.automatico=automatico;
	}
	
	public String getIdAreaPerfil() {
		return idAreaPerfil;
	}
	public void setIdAreaPerfil(String idAreaPerfil) {
		this.idAreaPerfil = idAreaPerfil;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getLbArea() {
		return lbArea;
	}
	public void setLbArea(String lbArea) {
		this.lbArea = lbArea;
	}
	public Boolean getConfirmada() {
		return confirmada;
	}
	public void setConfirmada(Boolean confirmada) {
		this.confirmada = confirmada;
	}
	public Boolean getAutomatico() {
		return automatico;
	}
	public void setAutomatico(Boolean automatico) {
		this.automatico = automatico;
	}
	
}
