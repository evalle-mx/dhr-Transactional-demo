package net.tce.dto;

public class TipoContenidoDto {
	private long idTipoContenido;
	private String types;
	private Long minSize;
	private Long maxSize;
	
	public void setIdTipoContenido(long idTipoContenido) {
		this.idTipoContenido = idTipoContenido;
	}
	public long getIdTipoContenido() {
		return idTipoContenido;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getTypes() {
		return types;
	}
	public void setMinSize(Long minSize) {
		this.minSize = minSize;
	}
	public Long getMinSize() {
		return minSize;
	}
	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}
	public Long getMaxSize() {
		return maxSize;
	}
	
}
