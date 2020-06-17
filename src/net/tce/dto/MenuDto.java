package net.tce.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MenuDto extends ComunDto{

	private String idPersona;
	private String etiqueta;
	private String ruta;
	private String catalogo;
	private String metadatos;
	private List<MenuDto> elementos;
	
	
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	public List<MenuDto> getElementos() {
		return elementos;
	}
	public void setElementos(List<MenuDto> elementos) {
		this.elementos = elementos;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getMetadatos() {
		return metadatos;
	}
	public void setMetadatos(String metadatos) {
		this.metadatos = metadatos;
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

	