package net.tce.dto;

public class ControlProcesoDto extends ComunDto{

	private String idTipoProceso;
	private String fecha;
	private String idPersona;
	private String tipoSync;

	
	public ControlProcesoDto(){}
	
	public ControlProcesoDto(String fecha){
		this.fecha=fecha;
	}

	
	
	public String getIdTipoProceso() {
		return idTipoProceso;
	}

	public void setIdTipoProceso(String idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoSync() {
		return tipoSync;
	}

	public void setTipoSync(String tipoSync) {
		this.tipoSync = tipoSync;
	}	
}
