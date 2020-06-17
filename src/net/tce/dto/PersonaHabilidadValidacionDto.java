package net.tce.dto;

public class PersonaHabilidadValidacionDto {
	private Long idPersonaHabilidad;
	private HabilidadValidacionDto habilidad;
	private PersonaValidacionDto persona;
	private DominioValidacionDto dominio;
	private String otra;

	public PersonaHabilidadValidacionDto() {
	}

	public Long getIdPersonaHabilidad() {
		return this.idPersonaHabilidad;
	}

	public void setIdPersonaHabilidad(Long idPersonaHabilidad) {
		this.idPersonaHabilidad = idPersonaHabilidad;
	}

	public HabilidadValidacionDto getHabilidad() {
		return this.habilidad;
	}

	public void setHabilidad(HabilidadValidacionDto habilidad) {
		this.habilidad = habilidad;
	}

	public PersonaValidacionDto getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaValidacionDto persona) {
		this.persona = persona;
	}

	public DominioValidacionDto getDominio() {
		return this.dominio;
	}

	public void setDominio(DominioValidacionDto dominio) {
		this.dominio = dominio;
	}

	public String getOtra() {
		return this.otra;
	}

	public void setOtra(String otra) {
		this.otra = otra;
	}

}
