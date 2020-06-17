package net.tce.dto.proto;

public class AgendaDto {

	private Integer consecutivo;
	private String id;
	
	private String stFecha;
	private String fechaInicio;
	private String fechaFin;
	
	private String stHora;
	private String estatus;
	
	public AgendaDto() { }
	
	public AgendaDto(Integer consecutivo, String stHora, String estatus) { 
		this.consecutivo=consecutivo;
		this.stHora=stHora;
		this.estatus=estatus;
	}
	
	/* Getters/setters */
	public Integer getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStFecha() {
		return stFecha;
	}
	public void setStFecha(String stFecha) {
		this.stFecha = stFecha;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getStHora() {
		return stHora;
	}
	public void setStHora(String stHora) {
		this.stHora = stHora;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}
