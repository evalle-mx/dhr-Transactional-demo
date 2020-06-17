package net.tce.dto;

public class ModeloRscPosicionFaseDto {

	private long idModeloRscPosFase;
	private long idTrackingPostulante;
	private long idRelacionEmpresaPersona;
	
	public long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	public long getIdModeloRscPosicionFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosicionFase(long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	public long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
}
