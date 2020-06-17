package net.tce.dto;

import java.util.List;

public class TrackingEsquemaPersonaDto extends TrackingDto {

	private String idTrackingEsquemaPersona;
	private String idTrackingMonitorPersona;
//	private String idPosibleCandidato;
	private String idEstadoTracking;
	private String comentario;
	private String ordenTrackDefaultCumplido;
	private List<TrackingMonitorDto> monitores;
	private List<CandidatoTrackingDto> candidatos;
	private List<PosibleCandidatoDto> lsPosibleCandidatoDto;
	
	private List<TrackingEsquemaPersonaDto> lsTracking;
	
	
	public String getIdTrackingEsquemaPersona() {
		return idTrackingEsquemaPersona;
	}
	public void setIdTrackingEsquemaPersona(String idTrackingEsquemaPersona) {
		this.idTrackingEsquemaPersona = idTrackingEsquemaPersona;
	}
//	public String getIdPosibleCandidato() {
//		return idPosibleCandidato;
//	}
//	public void setIdPosibleCandidato(String idPosibleCandidato) {
//		this.idPosibleCandidato = idPosibleCandidato;
//	}
	public String getIdEstadoTracking() {
		return idEstadoTracking;
	}
	public void setIdEstadoTracking(String idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public List<TrackingMonitorDto> getMonitores() {
		return monitores;
	}
	public void setMonitores(List<TrackingMonitorDto> monitores) {
		this.monitores = monitores;
	}
	public String getOrdenTrackDefaultCumplido() {
		return ordenTrackDefaultCumplido;
	}
	public void setOrdenTrackDefaultCumplido(String ordenTrackDefaultCumplido) {
		this.ordenTrackDefaultCumplido = ordenTrackDefaultCumplido;
	}
	public List<CandidatoTrackingDto> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<CandidatoTrackingDto> candidatos) {
		this.candidatos = candidatos;
	}
	public List<PosibleCandidatoDto> getLsPosibleCandidatoDto() {
		return lsPosibleCandidatoDto;
	}
	public void setLsPosibleCandidatoDto(List<PosibleCandidatoDto> lsPosibleCandidatoDto) {
		this.lsPosibleCandidatoDto = lsPosibleCandidatoDto;
	}
	public String getIdTrackingMonitorPersona() {
		return idTrackingMonitorPersona;
	}
	public void setIdTrackingMonitorPersona(String idTrackingMonitorPersona) {
		this.idTrackingMonitorPersona = idTrackingMonitorPersona;
	}
	public List<TrackingEsquemaPersonaDto> getLsTracking() {
		return lsTracking;
	}
	public void setLsTracking(List<TrackingEsquemaPersonaDto> lsTracking) {
		this.lsTracking = lsTracking;
	}
	
}
