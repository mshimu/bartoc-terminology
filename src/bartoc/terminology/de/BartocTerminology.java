package bartoc.terminology.de;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import com.mongodb.BasicDBList;


// TODO: Auto-generated Javadoc
/**
 * The Class BartocTerminology.
 */
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
		
/**
 * Gets the ddc.
 *
 * @return the ddc
 */
public BasicDBList[] getDdc() {
		return ddc;
	}
	
	/**
	 * Sets the ddc.
	 *
	 * @param ddc the new ddc
	 */
	public void setDdc(BasicDBList[] ddc) {
		this.ddc = ddc;
	}
	
	/**
	 * Gets the pref label.
	 *
	 * @return the pref label
	 */
	//	public String getClassification() { return classification; }
	public String getPrefLabel() { return prefLabel; }
	
	/**
	 * Gets the alt label.
	 *
	 * @return the alt label
	 */
	public String getAltLabel() { return altLabel; }
	
	/**
	 * Gets the scope note.
	 *
	 * @return the scope note
	 */
	public String getScopeNote() { return scopeNote; }
	
	/**
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	public String getCreator() { return creator; }
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() { return type; }
	
	/**
	 * Gets the wikipedia.
	 *
	 * @return the wikipedia
	 */
	public String getWikipedia() { return wikipedia; }
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() { return url; }
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() { return subject; }
	
	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public String getClasses() { return classes; }
	
	/**
	 * Gets the ddc_notation.
	 *
	 * @return the ddc_notation
	 */
	public String getDdc_notation() { return ddc_notation; }
	
//	public void setClassification(String classification){
//		this.classification = classification;
/**
 * Sets the pref label.
 *
 * @param prefLabel the new pref label
 */
//	}
	public void setPrefLabel(String prefLabel){
		this.prefLabel = prefLabel;
	}
	
	/**
	 * Sets the alt label.
	 *
	 * @param altLabel the new alt label
	 */
	public void setAltLabel(String altLabel){
		this.altLabel = altLabel;
	}
	
	/**
	 * Sets the scope note.
	 *
	 * @param scopeNote the new scope note
	 */
	public void setScopeNote(String scopeNote){
		this.scopeNote = scopeNote;
	}
	
	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(String creator){
		this.creator = creator;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Sets the wikipedia.
	 *
	 * @param wikipedia the new wikipedia
	 */
	public void setWikipedia(String wikipedia){
		this.wikipedia = wikipedia;
	}
	
	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	/**
	 * Sets the classes.
	 *
	 * @param classes the new classes
	 */
	public void setClasses(String classes){
		this.classes = classes;
	}
	
	/**
	 * Sets the ddc_notation.
	 *
	 * @param ddc_notation the new ddc_notation
	 */
	public void setDdc_notation(String ddc_notation){
		this.ddc_notation = ddc_notation;
	}
	
	/**
	 * Adds the.
	 *
	 * @param ddcnotation the ddcnotation
	 * @return the object
	 */
	public Object add(String ddcnotation) {
		// TODO Auto-generated method stub
		return ddcnotation;
	}

}
