package net.tce.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import net.tce.util.Constante;

public class TrackingDto extends ComunDto {
	
	private String idTracking;

	//Comun
	private String nombre;
	private String descripcion;
	private String orden;
	private String activo;
	private String monitor;
	private String bRealDelete;
	
	//Plantilla_rol
	private String idPlantillaRol;
	private String idRol;
	private String nameRol;
	private String lbRol;
	
	
	//Tracking_plantilla
	private String idTrackingPlantilla;
//	private String idTrackingPlantillaRel;
	
	//Tracking_esquema (Posicion)
	private String idTrackingEsquema;
	private String idEsquemaPerfilPosicion;
	private String idPerfilPosicion;
	private String idTrackingEsquemaRel;
	private String nombrePosicion;
	private String subirArchivo;
	private String bajarArchivo;
	private String editarComentario;
	private String fechaInicio;
	private String fechaFin;

	
	private List<TrackingDto> tracking; //Estados
		
	private String idPersona;
	private String idPosicion;
	private String idPerfil;
	private String idCandidato;
	private String idPosibleCandidato;
	private String idContratante;
	private String idMonitor;
	
	private List<TrackingMonitorDto> trackingMonitor;
	private List<TrackingEsquemaPersonaDto> trackingCandidato;
	
	
	public TrackingDto() {
	}
	/**
	 * Constructor para get en Posicion.read
	 * @param idEmpConf
	 * @param idPosicion
	 */
	public TrackingDto(String idEmpConf, String idPosicion) {
		this.setIdEmpresaConf(idEmpConf);
		this.idPosicion=idPosicion;
	}
	/**
	 * Constructor para HQL de ROl por PlantillaROl
	 * @param idPlantillaRol
	 * @param idRol
	 * @param rolDesc
	 */
	public TrackingDto(Long idRol, String rolDesc) {
//		this.idPlantillaRol=idPlantillaRol!=null?String.valueOf(idPlantillaRol):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.descripcion=rolDesc;
	}
	
	/**
	 * Constructor para PLANTILLA_ROL
	 * @param idPlantillaRol
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public TrackingDto(Long idPlantillaRol, String nombre, String descripcion, Boolean activo, Long idRol) {		
		this.idPlantillaRol=idPlantillaRol!=null?String.valueOf(idPlantillaRol):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para PLANTILLA_ROL <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/**
	 * Constructor para PLANTILLA_ROL
	 * @param idPlantillaRol
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public TrackingDto(Long idPlantillaRol, String nombre, String descripcion, 
						Boolean activo, Long idRol,String nameRol, boolean monitor) {		
		this.idPlantillaRol=idPlantillaRol!=null?String.valueOf(idPlantillaRol):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?"1":"0":"1";
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.nameRol=nameRol;
		this.monitor=monitor?"1":"0";
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para PLANTILLA_ROL <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/**
	 * Constructor para TRACKING_PLANTILLA (Estado-plantilla)
	 * @param idTrackingPlantilla
	 * @param idTrackingPlantillaRel
	 * @param idPlantillaRol
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 */
	public TrackingDto(Long idTrackingPlantilla, Long idPlantillaRol, 
			Short orden, String nombre, String descripcion) {
		this.idTrackingPlantilla=idTrackingPlantilla!=null?String.valueOf(idTrackingPlantilla):null;
//		this.idTrackingPlantillaRel=idTrackingPlantillaRel!=null?String.valueOf(idTrackingPlantillaRel):null;
		this.idPlantillaRol=idPlantillaRol!=null?String.valueOf(idPlantillaRol):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para TRACKING_PLANTILLA <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	
	/**
	 * Constructor para ESQUEMA_PERFIL
	 * @param idPlantillaRol
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public TrackingDto(Long idEsquemaPerfilPosicion, Long idPerfilPosicion, Long idPosicion, Long idRol, String nombre, String descripcion, Boolean activo) {		
		this.idEsquemaPerfilPosicion=idEsquemaPerfilPosicion!=null?String.valueOf(idEsquemaPerfilPosicion):null;
		this.idPerfilPosicion=idPerfilPosicion!=null?String.valueOf(idPerfilPosicion):null;
		this.idPosicion=idPosicion!=null?String.valueOf(idPosicion):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para ESQUEMA_PERFIL <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	/**
	 * Constructor para TRACKING_ESQUEMA (Estados-TrackPosicion)
	 * @param idTrackingEsquema
	 * @param idTrackingPlantilla
	 * @param idEsquemaPerfil
	 * @param idTrackingEsquemaRel
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public TrackingDto(Long idTrackingEsquema, Long idTrackingEsquemaRel, Long idEsquemaPerfilPosicion, Short orden, String nombre, String descripcion, 
			Boolean subirArchivo, Boolean bajarArchivo, Boolean editarComentario, Date fechaInicio, Date fechaFin){
		
		this.idTrackingEsquema=idTrackingEsquema!=null?String.valueOf(idTrackingEsquema):null;
		this.idTrackingEsquemaRel=idTrackingEsquemaRel!=null?String.valueOf(idTrackingEsquemaRel):null;
		this.idEsquemaPerfilPosicion=idEsquemaPerfilPosicion!=null?String.valueOf(idEsquemaPerfilPosicion):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		
		this.fechaInicio=fechaInicio!=null?String.valueOf(fechaInicio):null;
		this.fechaFin=fechaFin!=null?String.valueOf(fechaFin):null;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para TRACKING_ESQUEMA <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/**
	 * Constructor para TRACKING_ESQUEMA2 (Estados-TrackPosicion) con fecha en String
	 * @param idTrackingEsquema
	 * @param idTrackingPlantilla
	 * @param idEsquemaPerfil
	 * @param idTrackingEsquemaRel
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public TrackingDto(Long idTrackingEsquema, Long idTrackingEsquemaRel, Long idEsquemaPerfilPosicion, Short orden, String nombre, String descripcion, 
			Boolean subirArchivo, Boolean bajarArchivo, Boolean editarComentario, String fechaInicio, String fechaFin){
		
		this.idTrackingEsquema=idTrackingEsquema!=null?String.valueOf(idTrackingEsquema):null;
		this.idTrackingEsquemaRel=idTrackingEsquemaRel!=null?String.valueOf(idTrackingEsquemaRel):null;
		this.idEsquemaPerfilPosicion=idEsquemaPerfilPosicion!=null?String.valueOf(idEsquemaPerfilPosicion):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL:Constante.BOL_TRUE_VAL;
		
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para TRACKING_ESQUEMA <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/*public TrackingDto(Long idTracking, Long orden, Long idTrackRef, Long idPerPos, Long idRol, 
			Boolean pUpload, Boolean pDownload, String fechaInicio, String fechaFin, Boolean pComentario,
			String descripcion, Boolean activo) {
		this.idTracking=idTracking!=null?String.valueOf(idTracking):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.idTrackReferencia=idTrackRef!=null?String.valueOf(idTrackRef):null;
		this.idPerfilPosicion=idPerPos!=null?String.valueOf(idPerPos):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.subirArchivo=pUpload!=null?pUpload.booleanValue()?"1":"0":"0";
		this.bajarArchivo=pDownload!=null?pDownload.booleanValue()?"1":"0":"0";
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.editarComentario=pComentario!=null?pComentario.booleanValue()?"1":"0":"0";
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?"1":"0":"1";
	}*/
	
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
	public String getIdPlantillaRol() {
		return idPlantillaRol;
	}
	public void setIdPlantillaRol(String idPlantillaRol) {
		this.idPlantillaRol = idPlantillaRol;
	}
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	public String getIdTrackingPlantilla() {
		return idTrackingPlantilla;
	}
	public void setIdTrackingPlantilla(String idTrackingPlantilla) {
		this.idTrackingPlantilla = idTrackingPlantilla;
	}
//	public String getIdTrackingPlantillaRel() {
//		return idTrackingPlantillaRel;
//	}
//	public void setIdTrackingPlantillaRel(String idTrackingPlantillaRel) {
//		this.idTrackingPlantillaRel = idTrackingPlantillaRel;
//	}
	public String getIdTrackingEsquema() {
		return idTrackingEsquema;
	}
	public void setIdTrackingEsquema(String idTrackingEsquema) {
		this.idTrackingEsquema = idTrackingEsquema;
	}
	public String getIdEsquemaPerfilPosicion() {
		return idEsquemaPerfilPosicion;
	}
	public void setIdEsquemaPerfilPosicion(String idEsquemaPerfilPosicion) {
		this.idEsquemaPerfilPosicion = idEsquemaPerfilPosicion;
	}
	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public String getIdTrackingEsquemaRel() {
		return idTrackingEsquemaRel;
	}
	public void setIdTrackingEsquemaRel(String idTrackingEsquemaRel) {
		this.idTrackingEsquemaRel = idTrackingEsquemaRel;
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
	public List<TrackingDto> getTracking() {
		return tracking;
	}
	public void setTracking(List<TrackingDto> tracking) {
		this.tracking = tracking;
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
	public String getIdMonitor() {
		return idMonitor;
	}
	public void setIdMonitor(String idMonitor) {
		this.idMonitor = idMonitor;
	}
	public List<TrackingMonitorDto> getTrackingMonitor() {
		return trackingMonitor;
	}
	public void setTrackingMonitor(List<TrackingMonitorDto> trackingMonitor) {
		this.trackingMonitor = trackingMonitor;
	}
	public List<TrackingEsquemaPersonaDto> getTrackingCandidato() {
		return trackingCandidato;
	}
	public void setTrackingCandidato(
			List<TrackingEsquemaPersonaDto> trackingCandidato) {
		this.trackingCandidato = trackingCandidato;
	}
	public String getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(String idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	public String getNameRol() {
		return nameRol;
	}
	public void setNameRol(String nameRol) {
		this.nameRol = nameRol;
	}
	public String getbRealDelete() {
		return bRealDelete;
	}
	public void setbRealDelete(String bRealDelete) {
		this.bRealDelete = bRealDelete;
	}
	public String getLbRol() {
		return lbRol;
	}
	public void setLbRol(String lbRol) {
		this.lbRol = lbRol;
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
