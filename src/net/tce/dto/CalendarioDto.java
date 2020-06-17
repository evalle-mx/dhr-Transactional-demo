package net.tce.dto;

import java.util.Date;
import java.util.List;

import net.tce.util.Constante;
import net.tce.util.DateUtily;

public class CalendarioDto  extends ComunDto{

	private String idPersona;
	private String idCalendario;
	private String idModeloRscPosFase;
	private String idTrackingMonitor;
	private String fecha;
	private String dia;
	private String hora;
	private String orden;
	private List<CalendarioDto> dias;
	private List<CalendarioDto> horasLibres;
	private List<CalendarioDto> horasPropias;
	private List<CalendarioDto> horasOcupadas;
	
	public CalendarioDto(){}
	
	/**
	 * constructor para CalendarioServiceImpl.getDays()
	 * @param fecha
	 * @param idTrackingMonitor
	 */
	public CalendarioDto(Long idCalendario, Date fecha,Long idTrackingMonitor){
		this.idCalendario=String.valueOf(idCalendario);
		this.dia= DateUtily.date2String(fecha,Constante.DATE_FORMAT);
		this.hora= DateUtily.date2String(fecha,Constante.HOUR_FORMAT_JAVA);
		this.idTrackingMonitor=idTrackingMonitor!=null?String.valueOf(idTrackingMonitor):null;
	}

	public String getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}

	public void setIdModeloRscPosFase(String idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}

	

	public String getIdTrackingMonitor() {
		return idTrackingMonitor;
	}

	public void setIdTrackingMonitor(String idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}

	public List<CalendarioDto> getHorasLibres() {
		return horasLibres;
	}

	public void setHorasLibres(List<CalendarioDto> horasLibres) {
		this.horasLibres = horasLibres;
	}

	public List<CalendarioDto> getHorasPropias() {
		return horasPropias;
	}

	public void setHorasPropias(List<CalendarioDto> horasPropias) {
		this.horasPropias = horasPropias;
	}

	public List<CalendarioDto> getHorasOcupadas() {
		return horasOcupadas;
	}

	public void setHorasOcupadas(List<CalendarioDto> horasOcupadas) {
		this.horasOcupadas = horasOcupadas;
	}

	public List<CalendarioDto> getDias() {
		return dias;
	}

	public void setDias(List<CalendarioDto> dias) {
		this.dias = dias;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	

}
