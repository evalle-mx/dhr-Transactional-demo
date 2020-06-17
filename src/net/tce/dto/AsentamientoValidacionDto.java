package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


import net.tce.dto.DomicilioValidacionDto;

public class AsentamientoValidacionDto {
	private String idAsentamiento;
	private String municipio;
	private String ciudad;
	private String tipoAsentamiento;
	private String claveAsentamiento;
	private String descripcion;
	private String codigoPostal;
	private String latitude;
	private String longitude;
	private boolean estatusRegistro;
	private Set<DomicilioValidacionDto> domicilios = new HashSet<DomicilioValidacionDto>(0);
	

	public AsentamientoValidacionDto() {
	}

	

	public String getIdAsentamiento() {
		return idAsentamiento;
	}



	public void setIdAsentamiento(String idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getCiudad() {
		return ciudad;
	}



	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}



	public String getTipoAsentamiento() {
		return tipoAsentamiento;
	}



	public void setTipoAsentamiento(String tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}



	public String getClaveAsentamiento() {
		return claveAsentamiento;
	}



	public void setClaveAsentamiento(String claveAsentamiento) {
		this.claveAsentamiento = claveAsentamiento;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getCodigoPostal() {
		return codigoPostal;
	}



	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public boolean isEstatusRegistro() {
		return estatusRegistro;
	}



	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}



	public Set<DomicilioValidacionDto> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(Set<DomicilioValidacionDto> domicilios) {
		this.domicilios = domicilios;
	}
	
}
