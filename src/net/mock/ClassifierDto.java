package net.mock;

import net.tce.dto.ComunDto;

public class ClassifierDto extends ComunDto {

//	private String modo;
	private String seedDir;
	private String crawlId;
	private String query;
	private String docType;
	private String idModelo;
	
	private String jsonTokens;
	private Integer numeroDocs;
	private Integer desviacion;
	private String idArea;

	public String getSeedDir() {
		return seedDir;
	}
	public void setSeedDir(String seedDir) {
		this.seedDir = seedDir;
	}
	public String getCrawlId() {
		return crawlId;
	}
	public void setCrawlId(String crawlId) {
		this.crawlId = crawlId;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}	
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}
	public String getJsonTokens() {
		return jsonTokens;
	}
	public void setJsonTokens(String jsonTokens) {
		this.jsonTokens = jsonTokens;
	}
	public Integer getNumeroDocs() {
		return numeroDocs;
	}
	public void setNumeroDocs(Integer numeroDocs) {
		this.numeroDocs = numeroDocs;
	}
	public Integer getDesviacion() {
		return desviacion;
	}
	public void setDesviacion(Integer desviacion) {
		this.desviacion = desviacion;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
}
