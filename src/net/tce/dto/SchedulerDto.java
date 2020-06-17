package net.tce.dto;


public class SchedulerDto extends ComunDto {
	
	private String idEntidad;
	private String core;
	private String tipoEntidad;
	private Long numDocs;	
	private Long idTipoProceso;	
	private Long idControlProceso;	
	private Long idEstatusProceso;	
	private String currentModel;
	private String newModel;
	private String resultado;
	private String queryForModel;
	//private long currentNumDocsByQueryForModel;
	//private long numDocsForModel;
	private Short reclassificationAuto;
	private Long idPosicion;
	private String idPersona;
	private Byte verificado;
	private String tipoSync;

	//private HashMap<Integer,Long> statusList = new HashMap<Integer,Long>();
	
	public  SchedulerDto(){
		
	}
	
	public  SchedulerDto(Long idTipoProceso){
		this.idTipoProceso=idTipoProceso;
		
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getTipoEntidad() {
		return tipoEntidad;
	}
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public Long getNumDocs() {
		return numDocs;
	}
	public void setNumDocs(Long numDocs) {
		this.numDocs = numDocs;
	}		
	public Long getIdTipoProceso() {
		return idTipoProceso;
	}
	public void setIdTipoProceso(Long idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}
	public Long getIdControlProceso() {
		return idControlProceso;
	}
	public void setIdControlProceso(Long idControlProceso) {
		this.idControlProceso = idControlProceso;
	}
	public String getCurrentModel() {
		return currentModel;
	}
	public void setCurrentModel(String currentModel) {
		this.currentModel = currentModel;
	}
	public String getNewModel() {
		return newModel;
	}
	public void setNewModel(String newModel) {
		this.newModel = newModel;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getQueryForModel() {
		return queryForModel;
	}
	public void setQueryForModel(String queryForModel) {
		this.queryForModel = queryForModel;
	}
	/*public long getNumDocsForModel() {
		return numDocsForModel;
	}
	public void setNumDocsForModel(long numDocsForModel) {
		this.numDocsForModel = numDocsForModel;
	}*/
	public Short getReclassificationAuto() {
		return reclassificationAuto;
	}
	public void setReclassificationAuto(Short reclassificationAuto) {
		this.reclassificationAuto = reclassificationAuto;
	}
	public Long getIdEstatusProceso() {
		return idEstatusProceso;
	}
	public void setIdEstatusProceso(Long idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}
	/*public HashMap<Integer, Long> getStatusList() {
		return statusList;
	}
	public void setStatusList(HashMap<Integer, Long> statusList) {
		this.statusList = statusList;
	}*/
	/*public long getCurrentNumDocsByQueryForModel() {
		return currentNumDocsByQueryForModel;
	}
	public void setCurrentNumDocsByQueryForModel(long currentNumDocsByQueryForModel) {
		this.currentNumDocsByQueryForModel = currentNumDocsByQueryForModel;
	}*/
	public Long getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}
	public Byte getVerificado() {
		return verificado;
	}
	public void setVerificado(Byte verificado) {
		this.verificado = verificado;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoSync() {
		return tipoSync;
	}

	public void setTipoSync(String tipoSync) {
		this.tipoSync = tipoSync;
	}
	
	
}
