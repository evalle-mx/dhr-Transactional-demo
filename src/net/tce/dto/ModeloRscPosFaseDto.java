package net.tce.dto;

public class ModeloRscPosFaseDto {

	private long idModeloRscPosFase;
	private long idTrackingPostulante;
	private long idRelacionEmpresaPersona;
	
	public long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	public long getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosFase(long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	public long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

}
