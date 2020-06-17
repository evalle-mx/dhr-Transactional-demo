package net.tce.dto;

import java.util.List;

public class MonitorDto extends ModeloRscDto {
	
	private String idMonitor;
	private String lbRol;
	private String nombreMonitor;
	private String idPersonaMonitorSda;
	private String idPersonaMonitorSto;
	
	private List<TrackingMonitorDto> monitores;
	private List<TrackingPostulanteDto> trackingCandidatos;

	
	public MonitorDto() { }
	public MonitorDto(Long idPersona, String nombre, String apellidoPaterno, Boolean principal, Long idPosicion, 
			Long idModeloRscPos, Long idRol) { 
		
		setIdPersona( idPersona!=null?String.valueOf(idPersona):null );
		//this.nombreMonitor= (apellidoPaterno!=null?apellidoPaterno+", ":"") + (nombre!=null?nombre:"");
		setNombre( (apellidoPaterno!=null?apellidoPaterno+", ":"") + (nombre!=null?nombre:"") );
		setPrincipal( principal?"1":"0" );
		setIdPosicion(String.valueOf(idPosicion));
		setIdModeloRscPos(String.valueOf(idModeloRscPos) );
		setIdRol(idRol!=null?String.valueOf(idRol):null);
//		this.lbRol = lbRol;
//		setIdModeloRscPosFase(String.valueOf(idModeloRscPosFase) );
	}
	
	public MonitorDto(Long idPersona, String nombre, String apellidoPaterno, Long idModeloRscPos, Long idRol, String lbRol, Long idPosicion) {
		setIdPersona( idPersona!=null?String.valueOf(idPersona):null );
		this.nombreMonitor= apellidoPaterno!=null?apellidoPaterno+", ":"" + nombre!=null?nombre:"";
//		setPrincipal("1");
		setIdModeloRscPos(String.valueOf(idModeloRscPos) );
		setIdRol(String.valueOf(idRol));
		this.lbRol=lbRol;
		setIdPosicion(String.valueOf(idPosicion));
	}

	public List<TrackingMonitorDto> getMonitores() {
		return monitores;
	}

	public void setMonitores(List<TrackingMonitorDto> monitores) {
		this.monitores = monitores;
	}

	public String getIdMonitor() {
		return idMonitor;
	}

	public void setIdMonitor(String idMonitor) {
		this.idMonitor = idMonitor;
	}

	public String getLbRol() {
		return lbRol;
	}

	public void setLbRol(String lbRol) {
		this.lbRol = lbRol;
	}

	public String getNombreMonitor() {
		return nombreMonitor;
	}

	public void setNombreMonitor(String nombreMonitor) {
		this.nombreMonitor = nombreMonitor;
	}
	public String getIdPersonaMonitorSda() {
		return idPersonaMonitorSda;
	}
	public void setIdPersonaMonitorSda(String idPersonaMonitorSda) {
		this.idPersonaMonitorSda = idPersonaMonitorSda;
	}
	public String getIdPersonaMonitorSto() {
		return idPersonaMonitorSto;
	}
	public void setIdPersonaMonitorSto(String idPersonaMonitorSto) {
		this.idPersonaMonitorSto = idPersonaMonitorSto;
	}
	public List<TrackingPostulanteDto> getTrackingCandidatos() {
		return trackingCandidatos;
	}
	public void setTrackingCandidatos(List<TrackingPostulanteDto> trackingCandidatos) {
		this.trackingCandidatos = trackingCandidatos;
	}
	
}
