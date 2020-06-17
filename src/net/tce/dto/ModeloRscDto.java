package net.tce.dto;

import java.util.Date;
import java.util.List;

import net.tce.util.Constante;

public class ModeloRscDto extends ComunDto {

	private String idTracking;

	//Comun
	private String nombre;
	private String descripcion;
	private String orden;
	private String actividad;
	private String dias;
	private String activo;
	private String monitor;
	private String principal;
	private String bRealDelete;
	private Boolean cambioDeDias;
	
	//ModeloRsc
	private String idModeloRsc;
	private String idRol;
	
	//ModeloRscFase
	private String idModeloRscFase;
	
	//modeloRscPosFase (Posicion)
	private String idModeloRscPosFase;
	private String idModeloRscPos;
	private String idPerfilPosicion;
	private String idModeloRscPosFaseRel;
	private String nombrePosicion;
	private String subirArchivo;
	private String bajarArchivo;
	private String editarComentario;
	private String fechaInicio;
	private String fechaFin;
	private List<ModeloRscDto> fases; 
		
	private String idPersona;
	private String idPosicion;
	private String idPerfil;
	private String idCandidato;
	private String idPosibleCandidato;
	private String idContratante;
//	private String idMonitor;
	
	private List<TrackingMonitorDto> trackingMonitor;
	private List<TrackingPostulanteDto> trackingCandidato;
	
	
	public ModeloRscDto() {
	}
	
	
	public ModeloRscDto( String fechaInicio, String fechaFin) {
		this.fechaInicio=fechaInicio!=null ? fechaInicio:null;
		this.fechaFin=fechaFin!=null ? fechaFin:null;
	}
	
	/**
	 * Constructor para get en Posicion.read
	 * @param idEmpConf
	 * @param idPosicion
	 */
	public ModeloRscDto(String idEmpConf, String idPosicion, String idPersona, String modo) {
		this.setIdEmpresaConf(idEmpConf);
		this.idPosicion=idPosicion;
		this.idPersona = idPersona;
		setModo(modo!=null?modo:"0");
//		System.out.println("\n *************   1 **************************** \n");
	}
//	/**
//	 * Constructor para HQL de ROl por ModeloRsc
//	 * @param idRol
//	 * @param rolDesc
//	 */
//	public ModeloRscDto(Long idRol, String rolDesc) {
//		this.idRol=idRol!=null?String.valueOf(idRol):null;
//		this.descripcion=rolDesc;
//	}
	
	/**
	 * Constructor para ModeloRsc
	 * @param idModeloRsc
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public ModeloRscDto(Long idModeloRsc, String nombre, String descripcion, Boolean activo) {		
		this.idModeloRsc=idModeloRsc!=null?String.valueOf(idModeloRsc):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
//		this.idRol=idRol!=null?String.valueOf(idRol):null;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para ModeloRsc <<<<<<<<<<<<<<<<<<<<<<\n\n");
//		System.out.println("\n *************   2 **************************** \n");
	}
	
	/**
	 * Constructor para ModeloRsc
	 * @param idModeloRsc
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public ModeloRscDto(Long idModeloRsc, String nombre, String descripcion, 
						boolean activo,  Boolean monitor) {		
		this.idModeloRsc=idModeloRsc!=null?String.valueOf(idModeloRsc):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo ?"1":"0";
		this.monitor=monitor != null ? ( monitor.booleanValue()?"1":"0"):null;
//		System.out.println("\n *************   3 **************************** \n");
	}
	
	/**
	 * Para ModeloRscPosDaoImpl.getByPosicion()
	 * @param idModeloRscPos
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param monitor
	 */
	public ModeloRscDto(Long idModeloRscPos,Long idRol, String nombre, String descripcion, 
						Boolean monitor, boolean activo) {		
		this.idModeloRscPos=idModeloRscPos.toString();
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.idRol=idRol!=null?idRol.toString():null;
		this.monitor=monitor != null ? ( monitor.booleanValue()?"1":"0"):null;
		this.activo=activo ?"1":"0";
//		System.out.println("\n *************   4 **************************** \n");
	}
	
	/**
	 * Para ModeloRscPosFaseDaoImpl.getFasesByOrden()
	 * @param idModeloRscFase
	 * @param orden
	 * @param actividad
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscDto(Long idModeloRscFase, Short orden, 
			Short actividad, String fechaInicio, String fechaFin) {
		this.idModeloRscFase=idModeloRscFase!=null?String.valueOf(idModeloRscFase):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.actividad=actividad!=null ? String.valueOf(actividad):null;
		this.fechaInicio=fechaInicio!=null ? fechaInicio:null;
		this.fechaFin=fechaFin!=null ? fechaFin:null;
	}
	
	
	/**
	 * Constructor para ModeloRscFase
	 * @param idModeloRscFase
	 * @param idModeloRsc
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 */
	public ModeloRscDto(Long idModeloRscFase, Long idModeloRsc, 
			Short orden, String nombre, String descripcion) {
		this.idModeloRscFase=idModeloRscFase!=null?String.valueOf(idModeloRscFase):null;
		this.idModeloRsc=idModeloRsc!=null?String.valueOf(idModeloRsc):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
//		System.out.println("\n *************   5 **************************** \n");
	}
	
	
	/**
	 * Constructor para ModeloRscPos
	 * @param idModeloRsc
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public ModeloRscDto(Long idModeloRscPos, Long idPerfilPosicion, Long idPosicion,  
						Long idRol,String nombre, String descripcion, Boolean activo) {		
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		this.idPerfilPosicion=idPerfilPosicion!=null?String.valueOf(idPerfilPosicion):null;
		this.idPosicion=idPosicion!=null?String.valueOf(idPosicion):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para ESQUEMA_PERFIL <<<<<<<<<<<<<<<<<<<<<<\n\n");
//		System.out.println("\n *************   6 **************************** \n");
	}
	/**
	 *  Para ModeloRscPosFaseDaoImpl.getByPosicion()
	 * @param idModeloRscPosFase
	 * @param idModeloRscPosFaseRel
	 * @param idModeloRscPos
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscDto(Long idModeloRscPosFase, Long idModeloRscPosFaseRel, Long idModeloRscPos,Short orden,
						String nombre, String descripcion, Boolean subirArchivo, Boolean bajarArchivo,
						Boolean editarComentario, Date fechaInicio, Date fechaFin){
		
		this.idModeloRscPosFase=idModeloRscPosFase!=null?String.valueOf(idModeloRscPosFase):null;
		this.idModeloRscPosFaseRel=idModeloRscPosFaseRel!=null?String.valueOf(idModeloRscPosFaseRel):null;
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		
		this.fechaInicio=fechaInicio!=null?String.valueOf(fechaInicio):null;
		this.fechaFin=fechaFin!=null?String.valueOf(fechaFin):null;
//		System.out.println("\n *************   7   **************************** \n");
	}
	
	/**
	 * Constructor para ModeloRscPosFaseDaoImpl.getByModeloRscPos()
	 * @param idModeloRscPosFase
	 * @param idModeloRscPosFaseRel
	 * @param idModeloRscPos
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscDto(Long idModeloRscPosFase, Long idModeloRscPosFaseRel, Long idModeloRscPos, 
						Short orden, String nombre, String descripcion,Boolean subirArchivo,
						Boolean bajarArchivo, Boolean editarComentario, String fechaInicio, 
						String fechaFin, Boolean activo, Short dias){
		
		this.idModeloRscPosFase=idModeloRscPosFase!=null?String.valueOf(idModeloRscPosFase):null;
		this.idModeloRscPosFaseRel=idModeloRscPosFaseRel!=null?String.valueOf(idModeloRscPosFaseRel):null;
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.activo= activo!=null?activo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.dias=dias!=null?String.valueOf(dias):null;
//		System.out.println("\n *************   8 **************************** \n");
	}
	
	
	public String getIdTracking() {
		return idTracking;
	}
	public void setIdTracking(String idTracking) {
		this.idTracking = idTracking;
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
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	public String getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosFase(String idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	
	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public String getIdModeloRscPosFaseRel() {
		return idModeloRscPosFaseRel;
	}
	public void setIdModeloRscPosFaseRel(String idModeloRscPosFaseRel) {
		this.idModeloRscPosFaseRel = idModeloRscPosFaseRel;
	}
	public String getSubirArchivo() {
		return subirArchivo;
	}
	public void setSubirArchivo(String subirArchivo) {
		this.subirArchivo = subirArchivo;
	}
	public String getBajarArchivo() {
		return bajarArchivo;
	}
	public void setBajarArchivo(String bajarArchivo) {
		this.bajarArchivo = bajarArchivo;
	}
	public String getEditarComentario() {
		return editarComentario;
	}
	public void setEditarComentario(String editarComentario) {
		this.editarComentario = editarComentario;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}
	public String getIdContratante() {
		return idContratante;
	}
	public void setIdContratante(String idContratante) {
		this.idContratante = idContratante;
	}
	
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public String getNombrePosicion() {
		return nombrePosicion;
	}
	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}
//	public String getIdMonitor() {
//		return idMonitor;
//	}
//	public void setIdMonitor(String idMonitor) {
//		this.idMonitor = idMonitor;
//	}
	public List<TrackingMonitorDto> getTrackingMonitor() {
		return trackingMonitor;
	}
	public void setTrackingMonitor(List<TrackingMonitorDto> trackingMonitor) {
		this.trackingMonitor = trackingMonitor;
	}
	public List<TrackingPostulanteDto> getTrackingCandidato() {
		return trackingCandidato;
	}
	public void setTrackingCandidato(
			List<TrackingPostulanteDto> trackingCandidato) {
		this.trackingCandidato = trackingCandidato;
	}
	public String getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(String idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
//	public String getNameRol() {
//		return nameRol;
//	}
//	public void setNameRol(String nameRol) {
//		this.nameRol = nameRol;
//	}
	public String getbRealDelete() {
		return bRealDelete;
	}
	public void setbRealDelete(String bRealDelete) {
		this.bRealDelete = bRealDelete;
	}
//	public String getLbRol() {
//		return lbRol;
//	}
//	public void setLbRol(String lbRol) {
//		this.lbRol = lbRol;
//	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getIdModeloRscFase() {
		return idModeloRscFase;
	}
	public void setIdModeloRscFase(String idModeloRscFase) {
		this.idModeloRscFase = idModeloRscFase;
	}
	
	public String getIdModeloRsc() {
		return idModeloRsc;
	}
	public void setIdModeloRsc(String idModeloRsc) {
		this.idModeloRsc = idModeloRsc;
	}
	public String getIdModeloRscPos() {
		return idModeloRscPos;
	}
	public void setIdModeloRscPos(String idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}
	public List<ModeloRscDto> getFases() {
		return fases;
	}
	public void setFases(List<ModeloRscDto> fases) {
		this.fases = fases;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public Boolean getCambioDeDias() {
		return cambioDeDias;
	}
	public void setCambioDeDias(Boolean cambioDeDias) {
		this.cambioDeDias = cambioDeDias;
	}
}
