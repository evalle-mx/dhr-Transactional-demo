package net.tce.dto;

public class ModificarDto {

	boolean almenosSeModificoAlgo;
	boolean seModificoKoEIndices;
	boolean seModificoCategorias;
	boolean seModificoTextoAreaSolr;

	

	public ModificarDto(){}
	
	public ModificarDto(boolean almenosSeModificoAlgo, boolean seModificoTextoAreaSolr, Object dummy){
		this.almenosSeModificoAlgo=almenosSeModificoAlgo;
		this.seModificoTextoAreaSolr=seModificoTextoAreaSolr;
	}
	public ModificarDto(boolean almenosSeModificoAlgo, boolean seModificoKoEIndices){
		this.almenosSeModificoAlgo=almenosSeModificoAlgo;		
		this.seModificoKoEIndices=seModificoKoEIndices;		
	}

	public boolean isSeModificoTextoAreaSolr() {
		return seModificoTextoAreaSolr;
	}

	public void setSeModificoTextoAreaSolr(boolean seModificoTextoAreaSolr) {
		this.seModificoTextoAreaSolr = seModificoTextoAreaSolr;
	}

	
	
	
	public boolean isSeModificoKoEIndices() {
		return seModificoKoEIndices;
	}
	public void setSeModificoKoEIndices(boolean seModificoKoEIndices) {
		this.seModificoKoEIndices = seModificoKoEIndices;
	}
	
	public boolean isAlmenosSeModificoAlgo() {
		return almenosSeModificoAlgo;
	}
	public void setAlmenosSeModificoAlgo(boolean almenosSeModificoAlgo) {
		this.almenosSeModificoAlgo = almenosSeModificoAlgo;
	}
	
	public boolean isSeModificoCategorias() {
		return seModificoCategorias;
	}

	public void setSeModificoCategorias(boolean seModificoCategorias) {
		this.seModificoCategorias = seModificoCategorias;
	}
	

	
}
