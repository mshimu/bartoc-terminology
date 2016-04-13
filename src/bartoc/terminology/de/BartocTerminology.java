package bartoc.terminology.de;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import com.mongodb.BasicDBList;


public class BartocTerminology {
	
//	private String classification;
	private String prefLabel;
	private String altLabel;
	private String scopeNote;  
	private String creator;
	private String type;
	private String wikipedia;
	private String url;
	private String subject;
	private String classes;
	private String ddc_notation;
//	private String[] ddc_notation;
	private BasicDBList[] ddc;
		
public BasicDBList[] getDdc() {
		return ddc;
	}
	public void setDdc(BasicDBList[] ddc) {
		this.ddc = ddc;
	}
	//	public String getClassification() { return classification; }
	public String getPrefLabel() { return prefLabel; }
	public String getAltLabel() { return altLabel; }
	public String getScopeNote() { return scopeNote; }
	public String getCreator() { return creator; }
	public String getType() { return type; }
	public String getWikipedia() { return wikipedia; }
	public String getUrl() { return url; }
	public String getSubject() { return subject; }
	public String getClasses() { return classes; }
	public String getDdc_notation() { return ddc_notation; }
	
//	public void setClassification(String classification){
//		this.classification = classification;
//	}
	public void setPrefLabel(String prefLabel){
		this.prefLabel = prefLabel;
	}
	public void setAltLabel(String altLabel){
		this.altLabel = altLabel;
	}
	public void setScopeNote(String scopeNote){
		this.scopeNote = scopeNote;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setWikipedia(String wikipedia){
		this.wikipedia = wikipedia;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
	public void setClasses(String classes){
		this.classes = classes;
	}
	public void setDdc_notation(String ddc_notation){
		this.ddc_notation = ddc_notation;
	}
	public Object add(String ddcnotation) {
		// TODO Auto-generated method stub
		return ddcnotation;
	}

}
