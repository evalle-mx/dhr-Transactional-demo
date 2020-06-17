package net.tce.dto;

public class ReactivoDto extends ComunDto {

	private String idReactivo;
	private String idCompetencia;
	private String idRespuesta;
	private String texto;
	private String nResp;
	private String valor;

	public ReactivoDto() { }
	
	public ReactivoDto(Long idReactivo, String texto, Long idRespuesta ) { 
		this.idReactivo=idReactivo!=null?String.valueOf(idReactivo):null;
		this.texto=texto;
		this.idRespuesta=idRespuesta!=null?String.valueOf(idRespuesta):null;
	}
	
	public ReactivoDto(Long idReactivo, String texto, Long idRespuesta, Short valor ) { 
		this.idReactivo=idReactivo!=null?String.valueOf(idReactivo):null;
		this.texto=texto;
		this.idRespuesta=idRespuesta!=null?String.valueOf(idRespuesta):null;
		this.nResp=valor!=null?String.valueOf(valor):null;
	}
	
	public String getIdReactivo() {
		return idReactivo;
	}
	public void setIdReactivo(String idReactivo) {
		this.idReactivo = idReactivo;
	}
	public String getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(String idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getnResp() {
		return nResp;
	}
	public void setnResp(String nResp) {
		this.nResp = nResp;
	}
	public String getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(String idRespuesta) {
		this.idRespuesta = idRespuesta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
