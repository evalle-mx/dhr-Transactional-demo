package net.tce.dto;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author
 */
public class VacancyDto extends ComunDto{

	private String idPosicion;
	private String idEstatusPosicion;
	private String fechaCreacion;
	private String fechaModificacion;
	private String idEmpresa;
	private String idDomicilio;
	private String idPersona;
	private String idAmbitoGeografico;
	private String idNivelJerarquico;
	private String idTipoJornada;
	private String idTipoContrato;
	private String idTipoPrestacion;
	private String idPeriodicidadPago;
	private String nombre;
	private String otros;
	private String sueldoNegociable;
	private String salarioMin;
	private String salarioMax;
	private String valorActivo;
	private String numAcceptedCandidatos;
	private String numRejectedCandidatos;
	private String numTotalCandidatos;
	private String anioProgramacion;
	private String mesProgramacion;
	private String diaProgramacion;
	private String fechaProgramacion;
	private String texto;
	private String esConfidencial;
	private String asignada;	
	private String nombreEmpresa;
	private String concurrente;

	/* Identificador del perfil interno asociado a la posición */
	private String idPerfil;
	/* Identificador de la politica de ponderación de escolaridad creada al mismo tiempo */
	private String idPoliticaValorAcademia;

	/* Atributos relacionados con las políticas */
	private String experienciaLaboralMinima;
	private String idGradoAcademicoMin;
	private String idEstatusEscolarMin;
	private String idGradoAcademicoMax;
	private String idEstatusEscolarMax;
	private String idTipoGeneroRequerido;
	private String idEstadoCivilRequerido;
	private String idTipoDispViajar;
	private String cambioDomicilio;
	private String edadMinima;
	private String edadMaxima;
	
	private String atributoValor;
	private String atributoDescripcion;
	
	private Boolean personal;
	private String claveInterna;
	private String claveEmpresa;
	
	private List<LocationInfoDto> domicilios;
	private List<PerfilDto> perfiles;
	private Set<VacancyPoliticaEscolaridadDto> politicaEscolaridads;
	
	private List<SkillDto> habilidades;
	private List<IdiomaDto> idioma;
	private List<CertificacionDto> certificacion;
	private List<CompetenciaDto> competencias;
	
	/*  Candidato-Preview */
	private Long idCandidato;
	private Long idEstatusOperativo;
	private String motivoRechazo;
	
	private AreaPerfilDto area;
	
	public VacancyDto(){}
	
	public VacancyDto(boolean nullAll){
		if(nullAll){
			this.domicilios = null;
			this.politicaEscolaridads = null;
		}
	}
	
	public VacancyDto(String idEmpresaConf,String idPosicion){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPosicion=idPosicion;
	}

	public VacancyDto(String idPosicion, String idEmpresa, String idPersona, String idEstatusPosicion, String nombre, String fechaCreacion, 
			String fechaModificacion, String numTotalCandidatos, String numAcceptedCandidatos, String numRejectedCandidatos, String valorActivo 
			){
		this.idPosicion = idPosicion;
		this.idEmpresa = idEmpresa;
		this.idPersona = idPersona;
		this.idEstatusPosicion = idEstatusPosicion;
		this.nombre = nombre;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.numTotalCandidatos = numTotalCandidatos;
		this.numAcceptedCandidatos = numAcceptedCandidatos;
		this.numRejectedCandidatos = numRejectedCandidatos;
		this.valorActivo = valorActivo;
		this.domicilios = null;
		this.politicaEscolaridads = null;

	}

	public VacancyDto(String idPosicion, String nombre, String fechaCreacion, String fechaModificacion, String numTotalCandidatos){
		this.idPosicion = idPosicion;
		this.nombre = nombre;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.numTotalCandidatos = numTotalCandidatos;
	}
	
	public VacancyDto(Long dummy, String atributoValor, String atributoDescripcion) {
		this.atributoValor = atributoValor;
		this.atributoDescripcion = atributoDescripcion;
		this.domicilios = null;
		this.politicaEscolaridads = null;		
	}
	
	public VacancyDto(String code,String type,String message) {
		this.setCode(code);
		this.setType(type);
		this.setMessage(message);
		this.domicilios = null;
		this.politicaEscolaridads = null;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getIdEstatusPosicion() {
		return idEstatusPosicion;
	}

	public void setIdEstatusPosicion(String idEstatusPosicion) {
		this.idEstatusPosicion = idEstatusPosicion;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(String idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public String getIdNivelJerarquico() {
		return idNivelJerarquico;
	}

	public void setIdNivelJerarquico(String idNivelJerarquico) {
		this.idNivelJerarquico = idNivelJerarquico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(String salarioMin) {
		this.salarioMin = salarioMin;
	}

	public String getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(String salarioMax) {
		this.salarioMax = salarioMax;
	}

	public String getNumAcceptedCandidatos() {
		return numAcceptedCandidatos;
	}

	public void setNumAcceptedCandidatos(String numAcceptedCandidatos) {
		this.numAcceptedCandidatos = numAcceptedCandidatos;
	}

	public String getNumRejectedCandidatos() {
		return numRejectedCandidatos;
	}

	public void setNumRejectedCandidatos(String numRejectedCandidatos) {
		this.numRejectedCandidatos = numRejectedCandidatos;
	}

	public String getNumTotalCandidatos() {
		return numTotalCandidatos;
	}

	public void setNumTotalCandidatos(String numTotalCandidatos) {
		this.numTotalCandidatos = numTotalCandidatos;
	}

	public String getValorActivo() {
		return valorActivo;
	}

	public void setValorActivo(String valorActivo) {
		this.valorActivo = valorActivo;
	}

	public String getIdTipoJornada() {
		return idTipoJornada;
	}

	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}

	public String getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}

	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}

	public void setSueldoNegociable(String sueldoNegociable) {
		this.sueldoNegociable = sueldoNegociable;
	}

	public String getSueldoNegociable() {
		return sueldoNegociable;
	}

	public void setIdPeriodicidadPago(String idPeriodicidadPago) {
		this.idPeriodicidadPago = idPeriodicidadPago;
	}

	public String getIdPeriodicidadPago() {
		return idPeriodicidadPago;
	}

	public String getAnioProgramacion() {
		return anioProgramacion;
	}

	public void setAnioProgramacion(String anioProgramacion) {
		this.anioProgramacion = anioProgramacion;
	}

	public String getMesProgramacion() {
		return mesProgramacion;
	}

	public void setMesProgramacion(String mesProgramacion) {
		this.mesProgramacion = mesProgramacion;
	}

	public String getDiaProgramacion() {
		return diaProgramacion;
	}

	public void setDiaProgramacion(String diaProgramacion) {
		this.diaProgramacion = diaProgramacion;
	}
	
	public String getEsConfidencial() {
		return esConfidencial;
	}

	public void setEsConfidencial(String esConfidencial) {
		this.esConfidencial = esConfidencial;
	}

	public String getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	public List<LocationInfoDto> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<LocationInfoDto> domicilios) {
		this.domicilios = domicilios;
	}

	public String getExperienciaLaboralMinima() {
		return experienciaLaboralMinima;
	}

	public void setExperienciaLaboralMinima(String experienciaLaboralMinima) {
		this.experienciaLaboralMinima = experienciaLaboralMinima;
	}

	public String getIdGradoAcademicoMin() {
		return idGradoAcademicoMin;
	}

	public void setIdGradoAcademicoMin(String idGradoAcademicoMin) {
		this.idGradoAcademicoMin = idGradoAcademicoMin;
	}

	public String getIdEstatusEscolarMin() {
		return idEstatusEscolarMin;
	}

	public void setIdEstatusEscolarMin(String idEstatusEscolarMin) {
		this.idEstatusEscolarMin = idEstatusEscolarMin;
	}

	public String getIdGradoAcademicoMax() {
		return idGradoAcademicoMax;
	}

	public void setIdGradoAcademicoMax(String idGradoAcademicoMax) {
		this.idGradoAcademicoMax = idGradoAcademicoMax;
	}

	public String getIdEstatusEscolarMax() {
		return idEstatusEscolarMax;
	}

	public void setIdEstatusEscolarMax(String idEstatusEscolarMax) {
		this.idEstatusEscolarMax = idEstatusEscolarMax;
	}

	public String getIdTipoGeneroRequerido() {
		return idTipoGeneroRequerido;
	}

	public void setIdTipoGeneroRequerido(String idTipoGeneroRequerido) {
		this.idTipoGeneroRequerido = idTipoGeneroRequerido;
	}

	public String getIdEstadoCivilRequerido() {
		return idEstadoCivilRequerido;
	}

	public void setIdEstadoCivilRequerido(String idEstadoCivilRequerido) {
		this.idEstadoCivilRequerido = idEstadoCivilRequerido;
	}

	public Set<VacancyPoliticaEscolaridadDto> getPoliticaEscolaridads() {
		return politicaEscolaridads;
	}

	public void setPoliticaEscolaridads(
			Set<VacancyPoliticaEscolaridadDto> politicaEscolaridads) {
		this.politicaEscolaridads = politicaEscolaridads;
	}

	public String getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(String edadMinima) {
		this.edadMinima = edadMinima;
	}

	public String getEdadMaxima() {
		return edadMaxima;
	}

	public void setEdadMaxima(String edadMaxima) {
		this.edadMaxima = edadMaxima;
	}
	
	public String getIdPoliticaValorAcademia() {
		return idPoliticaValorAcademia;
	}

	public void setIdPoliticaValorAcademia(String idPoliticaValorAcademia) {
		this.idPoliticaValorAcademia = idPoliticaValorAcademia;
	}
	public String getAtributoValor() {
		return atributoValor;
	}

	public void setAtributoValor(String atributoValor) {
		this.atributoValor = atributoValor;
	}

	public String getAtributoDescripcion() {
		return atributoDescripcion;
	}

	public void setAtributoDescripcion(String atributoDescripcion) {
		this.atributoDescripcion = atributoDescripcion;
	}

	public Boolean getPersonal() {
		return personal;
	}

	public void setPersonal(Boolean personal) {
		this.personal = personal;
	}
	public String getAsignada() {
		return asignada;
	}

	public void setAsignada(String asignada) {
		this.asignada = asignada;
	}

	public Long getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}

	public Long getIdEstatusOperativo() {
		return idEstatusOperativo;
	}

	public void setIdEstatusOperativo(Long idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getClaveInterna() {
		return claveInterna;
	}

	public void setClaveInterna(String claveInterna) {
		this.claveInterna = claveInterna;
	}

	public String getClaveEmpresa() {
		return claveEmpresa;
	}

	public void setClaveEmpresa(String claveEmpresa) {
		this.claveEmpresa = claveEmpresa;
	}

	public List<PerfilDto> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<PerfilDto> perfiles) {
		this.perfiles = perfiles;
	}

	public List<SkillDto> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<SkillDto> habilidades) {
		this.habilidades = habilidades;
	}

	public List<IdiomaDto> getIdioma() {
		return idioma;
	}

	public void setIdioma(List<IdiomaDto> idioma) {
		this.idioma = idioma;
	}

	public String getIdTipoDispViajar() {
		return idTipoDispViajar;
	}

	public void setIdTipoDispViajar(String idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public List<CertificacionDto> getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(List<CertificacionDto> certificacion) {
		this.certificacion = certificacion;
	}

	public List<CompetenciaDto> getCompetencias() {
		return competencias;
	}

	public void setCompetencias(List<CompetenciaDto> competencias) {
		this.competencias = competencias;
	}

	public AreaPerfilDto getArea() {
		return area;
	}

	public void setArea(AreaPerfilDto area) {
		this.area = area;
	}

	public String getConcurrente() {
		return concurrente;
	}

	public void setConcurrente(String concurrente) {
		this.concurrente = concurrente;
	}
}
