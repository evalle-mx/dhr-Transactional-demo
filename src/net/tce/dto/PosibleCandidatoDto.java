package net.tce.dto;

public class PosibleCandidatoDto extends ComunDto{

	private String idPersona;
	private String tipoPostulante;
	private int caso;
	
	public PosibleCandidatoDto(){}
	
	public PosibleCandidatoDto(String idEmpresaConf,String idPersona,String tipoPostulante,int caso){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.tipoPostulante=tipoPostulante;
		this.caso=caso;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoPostulante() {
		return tipoPostulante;
	}

	public void setTipoPostulante(String tipoPostulante) {
		this.tipoPostulante = tipoPostulante;
	}

	public int getCaso() {
		return caso;
	}

	public void setCaso(int caso) {
		this.caso = caso;
	}
}
