package net.tce.dto;

public class PersonaPaisValidacionDto {
	private Long idPersonaPais;
	private PaisValidacionDto pais;

	public PersonaPaisValidacionDto() {
	}

	public Long getIdPersonaPais() {
		return this.idPersonaPais;
	}

	public void setIdPersonaPais(Long idPersonaPais) {
		this.idPersonaPais = idPersonaPais;
	}

	public PaisValidacionDto getPais() {
		return this.pais;
	}

	public void setPais(PaisValidacionDto pais) {
		this.pais = pais;
	}

}
