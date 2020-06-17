package net.tce.dto;

public class CatalogueDto extends ComunDto{
	
	private String idPrimary;
	private Long idForeign1;
	private Long idForeign2;
	private Long idForeign3;
//	private Boolean status; 
//	private String status;
	private String catalogueName;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private String field7;
	private String field8;	
	private String descripcion;
	private String significado;	
	
	public CatalogueDto() {
	}
	
	public CatalogueDto(String idPrimary,String descripcion) {
		this.idPrimary=idPrimary;
		this.descripcion=descripcion;
	}
	
	public String getIdPrimary() {
		return idPrimary;
	}
	public void setIdPrimary(String idPrimary) {
		this.idPrimary = idPrimary;
	}
	public Long getIdForeign1() {
		return idForeign1;
	}
	public void setIdForeign1(Long idForeign1) {
		this.idForeign1 = idForeign1;
	}
	public Long getIdForeign2() {
		return idForeign2;
	}
	public void setIdForeign2(Long idForeign2) {
		this.idForeign2 = idForeign2;
	}
	public Long getIdForeign3() {
		return idForeign3;
	}
	public void setIdForeign3(Long idForeign3) {
		this.idForeign3 = idForeign3;
	}
	public String getCatalogueName() {
		return catalogueName;
	}
	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getField7() {
		return field7;
	}
	public void setField7(String field7) {
		this.field7 = field7;
	}
	public String getField8() {
		return field8;
	}
	public void setField8(String field8) {
		this.field8 = field8;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSignificado() {
		return significado;
	}
	public void setSignificado(String significado) {
		this.significado = significado;
	}
	
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getCode()!=null?"code:".concat(this.getCode()).concat(", "):"")
		.append(this.getType()!=null?"type:".concat(this.getType()).concat(", "):"")
		.append(this.getMessage()!=null?"message:".concat(this.getMessage()).concat(", "):"")
		
		.append("catalogueName=").append(this.catalogueName!=null?this.catalogueName:"null")
		.append(", descripcion=").append(this.descripcion!=null?this.descripcion:"null")
		.append(", significado=").append(this.significado!=null?this.significado:"null")
		.append(", idPrimary=").append(this.idPrimary!=null?this.idPrimary:"null")
		.append(", idForeign1=").append(this.idForeign1!=null?this.idForeign1:"null")
		.append(", idForeign2=").append(this.idForeign2!=null?this.idForeign2:"null")
		.append(", idForeign3=").append(this.idForeign3!=null?this.idForeign3:"null")
		.append(", status=").append(this.getStatus()!=null?this.getStatus():"null")
		.append(", field1=").append(this.field1!=null?this.field1:"null")
		.append(", field2=").append(this.field2!=null?this.field2:"null")
		.append(", field3=").append(this.field3!=null?this.field3:"null")
		.append(", field4=").append(this.field4!=null?this.field4:"null")
		.append(", field5=").append(this.field5!=null?this.field5:"null")
		.append(", field6=").append(this.field6!=null?this.field6:"null")
		.append(", field7=").append(this.field7!=null?this.field7:"null")
		.append(", field8=").append(this.field8!=null?this.field8:"null");
		
		return sb.toString();
	}
}
