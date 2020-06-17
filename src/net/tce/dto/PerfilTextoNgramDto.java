package net.tce.dto;

public class PerfilTextoNgramDto extends ComunDto{


	private String idPerfilTextoNgram;
	private String idPerfil;
	private String idTextoNgram;
	private String texto;
	private String ponderacion;
	private String idPosicion;
	
	public PerfilTextoNgramDto() {
	}
	public PerfilTextoNgramDto(String idPerfil, String idEmpresaConf) {
		this.idPerfil=idPerfil;
		this.setIdEmpresaConf(idEmpresaConf);
	}
	
	public PerfilTextoNgramDto(Long idPerfilTextoNgram,String texto,Short ponderacion) {
		this.idPerfilTextoNgram=idPerfilTextoNgram == null ? null:idPerfilTextoNgram.toString();
		this.texto=texto;
		this.ponderacion=ponderacion == null ? null:ponderacion.toString();
	}
	
	
	
	public String getIdPerfilTextoNgram() {
		return idPerfilTextoNgram;
	}
	public void setIdPerfilTextoNgram(String idPerfilTextoNgram) {
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getIdTextoNgram() {
		return idTextoNgram;
	}
	public void setIdTextoNgram(String idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
}
