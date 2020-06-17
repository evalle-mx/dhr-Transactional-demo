package net.tce.dto;

public class CompetenciaDto extends ComunDto {

	private String idCompetenciaPerfil;
	private String idCompetencia;
	private String idPosicion;
	private String descripcion;
	private String significado;
	private String idDominio;
	private String idPerfil;
	private String seleccionado;
	
	
	public CompetenciaDto() {}
	
	
	public CompetenciaDto(String idEmpresaConf,  String idPerfil) {
		setIdEmpresaConf(idEmpresaConf);
		this.idPerfil=idPerfil;
	}
	
	public CompetenciaDto(long idCompetenciaPerfil, boolean seleccionado,String descripcion) {
		this.setIdCompetenciaPerfil(String.valueOf(idCompetenciaPerfil));
		this.seleccionado=String.valueOf(seleccionado ? "1":"0");
		this.descripcion=descripcion;
	}
	
	
	
	
	public String getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(String idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSignificado() {
		return significado;
	}
	public void setSignificado(String significado) {
		this.significado = significado;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}


	public String getIdCompetenciaPerfil() {
		return idCompetenciaPerfil;
	}


	public void setIdCompetenciaPerfil(String idCompetenciaPerfil) {
		this.idCompetenciaPerfil = idCompetenciaPerfil;
	}
}
