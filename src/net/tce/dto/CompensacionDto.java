package net.tce.dto;

import java.util.List;

public class CompensacionDto extends ComunDto {

	private String idPersona;
	private String nombreCompleto;
	private String fechaNacimiento;
	private String edad;
	private String idTipoGenero;
	private String lbGenero;
	private String idEstadoCivil;
	private String lbEstadoCivil;
	private String idPais;
	private String stPais;
	private String diasAguinaldo;
	private String cantidadFondoAhorro;
	private String comedor;
	private Boolean celular;
	private Boolean clubGym;
	private Boolean checkUp;
	private String ultimoMontoUtilidades;
	private String otro;
	private String curp;
	private String fechaCaptura;	
	private List<String> sueldoBase;
	private List<String> auto;
	private List<ContactInfoDto> contacto;
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getIdTipoGenero() {
		return idTipoGenero;
	}
	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}
	public String getLbGenero() {
		return lbGenero;
	}
	public void setLbGenero(String lbGenero) {
		this.lbGenero = lbGenero;
	}
	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public String getLbEstadoCivil() {
		return lbEstadoCivil;
	}
	public void setLbEstadoCivil(String lbEstadoCivil) {
		this.lbEstadoCivil = lbEstadoCivil;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getStPais() {
		return stPais;
	}
	public void setStPais(String stPais) {
		this.stPais = stPais;
	}
	public String getDiasAguinaldo() {
		return diasAguinaldo;
	}
	public void setDiasAguinaldo(String diasAguinaldo) {
		this.diasAguinaldo = diasAguinaldo;
	}
	public String getCantidadFondoAhorro() {
		return cantidadFondoAhorro;
	}
	public void setCantidadFondoAhorro(String cantidadFondoAhorro) {
		this.cantidadFondoAhorro = cantidadFondoAhorro;
	}
	public String getComedor() {
		return comedor;
	}
	public void setComedor(String comedor) {
		this.comedor = comedor;
	}
	public Boolean getCelular() {
		return celular;
	}
	public void setCelular(Boolean celular) {
		this.celular = celular;
	}
	public Boolean getClubGym() {
		return clubGym;
	}
	public void setClubGym(Boolean clubGym) {
		this.clubGym = clubGym;
	}
	public Boolean getCheckUp() {
		return checkUp;
	}
	public void setCheckUp(Boolean checkUp) {
		this.checkUp = checkUp;
	}
	public String getUltimoMontoUtilidades() {
		return ultimoMontoUtilidades;
	}
	public void setUltimoMontoUtilidades(String ultimoMontoUtilidades) {
		this.ultimoMontoUtilidades = ultimoMontoUtilidades;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(String fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public List<String> getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(List<String> sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	public List<String> getAuto() {
		return auto;
	}
	public void setAuto(List<String> auto) {
		this.auto = auto;
	}
	public List<ContactInfoDto> getContacto() {
		return contacto;
	}
	public void setContacto(List<ContactInfoDto> contacto) {
		this.contacto = contacto;
	}	
}
