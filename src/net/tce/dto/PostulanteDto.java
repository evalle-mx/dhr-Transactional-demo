package net.tce.dto;

import java.util.List;

public class PostulanteDto extends ComunDto {
	
	private String idPosicion;
	private String nombrePosicion;
	private String idArea;
	private String lbArea;
	
	private List<CandidatoDto> confirmados;
	private List<CandidatoDto> descartados;
	
	private List<CandidatoDto> internoIndexado;
	private List<CandidatoDto> internoPotencial;
	private List<CandidatoDto> externo;
	
	private String tipoPostulante;
	private String idCandidato;
	private String idPosibleCandito;
	
	private String idEstatusOperativo;
	private String idEstatusPostulante;
	private String confirmado;	//1/0
	private String textoMotivo;
	
	public PostulanteDto() {
	}
	
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getNombrePosicion() {
		return nombrePosicion;
	}
	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}
	public List<CandidatoDto> getConfirmados() {
		return confirmados;
	}
	public void setConfirmados(List<CandidatoDto> confirmados) {
		this.confirmados = confirmados;
	}
	public List<CandidatoDto> getDescartados() {
		return descartados;
	}
	public void setDescartados(List<CandidatoDto> descartados) {
		this.descartados = descartados;
	}
	public List<CandidatoDto> getInternoIndexado() {
		return internoIndexado;
	}
	public void setInternoIndexado(List<CandidatoDto> internoIndexado) {
		this.internoIndexado = internoIndexado;
	}
	public List<CandidatoDto> getInternoPotencial() {
		return internoPotencial;
	}
	public void setInternoPotencial(List<CandidatoDto> internoPotencial) {
		this.internoPotencial = internoPotencial;
	}
	public List<CandidatoDto> getExterno() {
		return externo;
	}
	public void setExterno(List<CandidatoDto> externo) {
		this.externo = externo;
	}

	public String getTipoPostulante() {
		return tipoPostulante;
	}

	public void setTipoPostulante(String tipoPostulante) {
		this.tipoPostulante = tipoPostulante;
	}

	public String getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}

	public String getIdPosibleCandito() {
		return idPosibleCandito;
	}

	public void setIdPosibleCandito(String idPosibleCandito) {
		this.idPosibleCandito = idPosibleCandito;
	}

	public String getIdEstatusOperativo() {
		return idEstatusOperativo;
	}

	public void setIdEstatusOperativo(String idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}

	public String getIdEstatusPostulante() {
		return idEstatusPostulante;
	}

	public void setIdEstatusPostulante(String idEstatusPostulante) {
		this.idEstatusPostulante = idEstatusPostulante;
	}

	public String getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(String confirmado) {
		this.confirmado = confirmado;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getLbArea() {
		return lbArea;
	}

	public void setLbArea(String lbArea) {
		this.lbArea = lbArea;
	}

	public String getTextoMotivo() {
		return textoMotivo;
	}

	public void setTextoMotivo(String textoMotivo) {
		this.textoMotivo = textoMotivo;
	}
}
