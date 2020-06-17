package net.tce.dto;

import java.util.List;

public class TrackingMonitorDto extends ModeloRscDto {

private List<CandidatoTrackingDto> candidatos;
	
	private String idTrackingMonitor;
	private String idTrackingPostulante;
	private String idEstadoTracking;
	private List<TrackingMonitorDto> lsTracking;
	private List<ModeloRscPosicionFaseDto> tracks;
//	private String idMonitor;
	private String comentario;
	
	public TrackingMonitorDto() {
	}
	
	/**
	 * Constructor para HQL
	 * @param idTrackingMonitor
	 * @param idTrackingPostulante
	 * @param idModeloRscPosFase
	 * @param idEstadoTracking
	 * @param idMonitor
	 * @param idPostulante
	 * @param comentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public TrackingMonitorDto(Long idTrackingMonitor, Long idTrackingPostulante, Long idModeloRscPosFase, 
			Long idEstadoTracking, Long idMonitor, Long idPostulante, String comentario, String fechaInicio, String fechaFin ) {
		this.idTrackingMonitor=idTrackingMonitor!=null?String.valueOf(idTrackingMonitor):null;
		this.idTrackingPostulante=idTrackingPostulante!=null?String.valueOf(idTrackingPostulante):null;
		this.setIdModeloRscPosFase(idModeloRscPosFase!=null?String.valueOf(idModeloRscPosFase):null);
		this.idEstadoTracking=idEstadoTracking!=null?String.valueOf(idEstadoTracking):null;
//		this.setIdMonitor(idMonitor!=null?String.valueOf(idMonitor):null);
		this.setIdPersona(idPostulante!=null?String.valueOf(idPostulante):null);
		this.comentario=comentario;
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
	}
	
	public List<CandidatoTrackingDto> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<CandidatoTrackingDto> candidatos) {
		this.candidatos = candidatos;
	}
	public String getIdTrackingMonitor() {
		return idTrackingMonitor;
	}
	public void setIdTrackingMonitor(String idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}
	public String getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(String idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
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

	public List<TrackingMonitorDto> getLsTracking() {
		return lsTracking;
	}

	public void setLsTracking(List<TrackingMonitorDto> lsTracking) {
		this.lsTracking = lsTracking;
	}


	public List<ModeloRscPosicionFaseDto> getTracks() {
		return tracks;
	}

	public void setTracks(List<ModeloRscPosicionFaseDto> tracks) {
		this.tracks = tracks;
	}
}
