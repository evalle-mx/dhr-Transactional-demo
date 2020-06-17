package net.mock;

import java.util.List;

import net.tce.dto.ComunDto;

public class SolrDocTopics extends ComunDto {

public static final String FIELD_ID = "id";
		
    
    private String id;
	
	
	private Integer doctype;

	
	private String category;

	
	private List<String> categoriesanalysis;

	
	private String target;
	
	
	private String classified;
	
	
	private String content;

	
	private String modelversion;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDoctype() {
		return doctype;
	}

	public void setDoctype(Integer doctype) {
		this.doctype = doctype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getCategoriesanalysis() {
		return categoriesanalysis;
	}

	public void setCategoriesanalysis(List<String> categoriesanalysis) {
		this.categoriesanalysis = categoriesanalysis;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getClassified() {
		return classified;
	}

	public void setClassified(String classified) {
		this.classified = classified;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getModelversion() {
		return modelversion;
	}

	public void setModelversion(String modelversion) {
		this.modelversion = modelversion;
	}

	
	@Override
    public String toString() {
        //return ReflectionToStringBuilder.toString(this);
		return "[" +
        		"id= " + id + ", doctype= " + doctype + 
				", category= " + category + ", categoriesanalysis= " + categoriesanalysis + 
				", target= " + target + ", classified= " + ", content= " + content + ", modelversion= "+ modelversion +
				"]";
    }
}
