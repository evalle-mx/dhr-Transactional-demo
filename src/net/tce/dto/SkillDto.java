package net.tce.dto;

public class SkillDto extends ComunDto {
	/*Para Persona */
	private String idPersona;
	private String idHabilidad;
	private String idDominio;
	private String descripcion;

	/* Para Posicion */
	private String idPosicion;
	private String idHabilidadpos;
	private String idPoliticaMHabilidad;
	private String idPolitica;
	
	public SkillDto(){}
	
	public SkillDto(String idEmpresaConf,String idPersona){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
	}
	
	public SkillDto(String idEmpresaConf,String idPersona, String idPosicion){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.idPosicion=idPosicion;
	}
	
	/**
	 * Constructor para Query en Get
	 * @param idPoliticaMHabilidad
	 * @param idHabilidadpos
	 * @param descripcion
	 * @param idDominio
	 * @param idPolitica
	 */
	public SkillDto(Long idPoliticaMHabilidad, Long idHabilidadpos, String descripcion, Long idDominio, Long idPolitica){
		this.idPoliticaMHabilidad=(idPoliticaMHabilidad == null ? null:idPoliticaMHabilidad.toString());
		this.idHabilidadpos= (idHabilidadpos == null ? null:idHabilidadpos.toString());
		this.descripcion= descripcion;
		this.idDominio= (idDominio == null ? null:idDominio.toString());
		this.idPolitica= (idPolitica == null ? null:idPolitica.toString());
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	
	public void setIdHabilidad(String idHabilidad) {
		this.idHabilidad = idHabilidad;
	}
	public String getIdHabilidad() {
		return idHabilidad;
	}
	
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getIdDominio() {
		return idDominio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getIdHabilidadpos() {
		return idHabilidadpos;
	}

	public void setIdHabilidadpos(String idHabilidadpos) {
		this.idHabilidadpos = idHabilidadpos;
	}

	public String getIdPoliticaMHabilidad() {
		return idPoliticaMHabilidad;
	}

	public void setIdPoliticaMHabilidad(String idPoliticaMHabilidad) {
		this.idPoliticaMHabilidad = idPoliticaMHabilidad;
	}

	public String getIdPolitica() {
		return idPolitica;
	}

	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}
	
}
