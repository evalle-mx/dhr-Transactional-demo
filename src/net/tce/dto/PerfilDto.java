package net.tce.dto;

import java.math.BigDecimal;
import java.util.List;


import net.tce.dto.PerfilTextoNgramDto;

public class PerfilDto extends ComunDto {

	private String idPosicion;
	private String idPerfil;
	private String idEmpresa;
	private String idPersonaPerfil;
	private String idEmpresaPerfil;
	private String ponderacion;
	private String idTipoPerfil;
	private String nombre;
	private String descripcion;
	private String perfilSinTextosNgram;
	private String perfilesDetalleTextosNgram;
	private String textoClasificacion;
	private String clasificado;
	private String idEstatusPerfil;
	private Boolean incluyeInactivos;
	private Long associate;
	
	private String idPersona;
	
	private List<PerfilTextoNgramDto> textos ;
	
	public PerfilDto(){}
	
	public PerfilDto(Long idPerfil){
		this.idPerfil=idPerfil.toString();
	}
	
	public PerfilDto(String idEmpresaConf,String idPosicion){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPosicion=idPosicion;
	}
	
	public PerfilDto(Long idPerfil, Long idTipoPerfil, BigDecimal ponderacion,String nombre,
			String descripcion, Long idEstatusPerfil){
		this.idPerfil= (idPerfil == null ? null:idPerfil.toString());
		this.idTipoPerfil=(idTipoPerfil == null ? null:idTipoPerfil.toString());
		this.ponderacion=(ponderacion == null ? null:ponderacion.toString());
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.idEstatusPerfil=(idEstatusPerfil==null?null:idEstatusPerfil.toString());
	}
	
	public PerfilDto(Long idPerfil, Long idTipoPerfil, Long idEmpresa, String textoClasificacion,
			String nombre, String descripcion, Boolean clasificado, Long idEstatusPerfil){
		this.idPerfil= (idPerfil == null ? null:idPerfil.toString());
		this.idTipoPerfil=(idTipoPerfil == null ? null:idTipoPerfil.toString());
		this.idEmpresa=(idEmpresa==null?null:idEmpresa.toString());
		this.textoClasificacion=textoClasificacion;
		this.ponderacion=(ponderacion == null ? null:ponderacion.toString());
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.clasificado=(clasificado==null? null:(String.valueOf(clasificado ? "1":"0")) );
		this.idEstatusPerfil=(idEstatusPerfil==null?null:idEstatusPerfil.toString());
	}
	
	
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	
	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
	public String getPonderacion() {
		return ponderacion;
	}
	
	public String getIdTipoPerfil() {
		return idTipoPerfil;
	}
	public void setIdTipoPerfil(String idTipoPerfil) {
		this.idTipoPerfil = idTipoPerfil;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getIdPersonaPerfil() {
		return idPersonaPerfil;
	}

	public void setIdPersonaPerfil(String idPersonaPerfil) {
		this.idPersonaPerfil = idPersonaPerfil;
	}
	
	public String getIdEmpresaPerfil() {
		return idEmpresaPerfil;
	}

	public void setIdEmpresaPerfil(String idEmpresaPerfil) {
		this.idEmpresaPerfil = idEmpresaPerfil;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public List<PerfilTextoNgramDto> getTextos() {
		return textos;
	}

	public void setTextos(List<PerfilTextoNgramDto> textos) {
		this.textos = textos;
	}	

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	public String getPerfilSinTextosNgram() {
		return perfilSinTextosNgram;
	}

	public void setPerfilSinTextosNgram(String perfilSinTextosNgram) {
		this.perfilSinTextosNgram = perfilSinTextosNgram;
	}
	
	public String getPerfilesDetalleTextosNgram() {
		return perfilesDetalleTextosNgram;
	}

	public void setPerfilesDetalleTextosNgram(String perfilesDetalleTextosNgram) {
		this.perfilesDetalleTextosNgram = perfilesDetalleTextosNgram;
	}

	public String getTextoClasificacion() {
		return textoClasificacion;
	}

	public void setTextoClasificacion(String textoClasificacion) {
		this.textoClasificacion = textoClasificacion;
	}

	public String getClasificado() {
		return clasificado;
	}

	public void setClasificado(String clasificado) {
		this.clasificado = clasificado;
	}

	public String getIdEstatusPerfil() {
		return idEstatusPerfil;
	}

	public void setIdEstatusPerfil(String idEstatusPerfil) {
		this.idEstatusPerfil = idEstatusPerfil;
	}

	public Boolean getIncluyeInactivos() {
		return incluyeInactivos;
	}

	public void setIncluyeInactivos(Boolean incluyeInactivos) {
		this.incluyeInactivos = incluyeInactivos;
	}

	public Long getAssociate() {
		return associate;
	}
	
	/*TODO -> Buscar equivalente en lang3
	 * @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }*/

	public void setAssociate(Long associate) {
		this.associate = associate;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
}
