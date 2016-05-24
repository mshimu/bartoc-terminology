/**
 * This file is part of a Web Application Software for viewing the 
 * Bartoc Terminologies on the web. 
 * 
 * getTerminology, resetData, fileDownload are the methods under the 
 * class named ShowBartocTerminology.
 */

package bartoc.terminology.de;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import bartoc.terminology.de.BartocTerminology;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;  
import javax.json.stream.*;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;  
import javax.json.JsonWriterFactory;

import org.primefaces.json.JSONException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

// TODO: Auto-generated Javadoc
/**
 * This class is the main ManagedBean which is operates all the methods 
 * and accessing the database. 
 * 
 * <p>The getTerminology method usually connects with the mongodb and also 
 * responsible to generate the query 
 * and returns all the required query data as a list.
 * Calling this method from another class is easy.   
 * 
 * <p>The resetData is a method where all the required queries are written 
 * and it also setting the required field for the specific data table.
 * 
 * <p>The method fileDownload gives the flexibility to download the specific 
 * data table as a JSON/JSKOS format. It calls the getTerminology method and 
 * takes the data from the database and converts it into JSON/JSKOS format.
 *  
 * @author M. Akter 
 * @version $1.0 $ 
 *  
 */


@ManagedBean(name = "showTerminology")  
@ApplicationScoped
public class ShowBartocTerminology {
	
	/** The table. */
	DBCollection table = null;
	
	/** The db. */
	DB db = null;
	
	/** The mongo. */
	Mongo mongo = null;
	
	/** The cursor. */
	DBCursor cursor = null;
	
	/** The query. */
	private DBObject query=null;
	
	/** The fields. */
	private BasicDBObject fields=null;
	
	/** The list. */
	private List<BartocTerminology> list = new ArrayList<BartocTerminology>();
	
	/** The lst. */
	private List<BartocTerminology> lst;
	
	/** The filtered terminologies. */
	private List<BartocTerminology> filteredTerminologies = null;
	
	/** The list name. */
	private String listName;

	/**
	 * Gets the list name.
	 *
	 * @return the list name
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * Sets the list name.
	 *
	 * @param listName the new list name
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}
	
	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public DBObject getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 *
	 * @param query the new query
	 */
	public void setQuery(DBObject query) {
		this.query = query;
	}

	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public BasicDBObject getFields() {
		return fields;
	}

	/**
	 * Sets the fields.
	 *
	 * @param fields the new fields
	 */
	public void setFields(BasicDBObject fields) {
		this.fields = fields;
	}
	
	/**
	 * Reset data.
	 */
	//Queries for the data table
	@PostConstruct
	public void resetData() {
		try{
			if(getListName()=="Subject Classifications")
			{
				setQuery(new BasicDBObject("Classification", getListName()));
				setFields(new BasicDBObject("_id",0).append("Classification", 0));	
			}
			else if(getListName()=="Subject Heading")
			{
				setQuery(new BasicDBObject("Classification", getListName()));
				setFields(new BasicDBObject("_id",0).append("Classification", 0));
			}
			else if(getListName()=="Thesauri")
			{
				setQuery(new BasicDBObject("Classification", getListName()));
				setFields(new BasicDBObject("_id",0).append("Classification", 0));
			}
			else if(getListName()=="Universal Classifications")
			{
				setQuery(new BasicDBObject("Classification", getListName()));
				setFields(new BasicDBObject("_id",0).append("Classification", 0));
			}
			else if(getListName()=="University Specific Classifications")
			{
				setQuery(new BasicDBObject("Classification", getListName()));
				setFields(new BasicDBObject("_id",0).append("Classification", 0));
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

	/**
	 * Gets the terminology.
	 *
	 * @return the terminology
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Returns the data as a list
	public List<BartocTerminology> getTerminology() throws IOException{
			mongo = new Mongo("localhost",27017);
			db = mongo.getDB("MongoDatabase");
//			mongo = new MongoClient(new ServerAddress("esx-128.gbv.de", 27017));
               //     new MongoClientOptions.Builder().connectTimeout(300000).socketTimeout(300000).build());
//			db = mongo.getDB("BartocDatabase");
			table = db.getCollection("data_bartoc");
			list = new ArrayList<BartocTerminology>();
			BartocTerminology bT = null;
			setQuery(new BasicDBObject("Classification", getListName()));
			setFields(new BasicDBObject("_id",0).append("Classification", 0));
	
			DBCursor cursor = table.find(getQuery(), getFields());
				while (cursor.hasNext()) {
					DBObject obj = cursor.next();
					ArrayList<DBObject> description = (ArrayList<DBObject>)obj.get("Description"); 
						if(description!=null){
							for(DBObject embedded : description){
								bT = new BartocTerminology();
								bT.setPrefLabel((String)embedded.get("prefLabel"));
								bT.setAltLabel((String)embedded.get("altLabel"));
								bT.setScopeNote((String)embedded.get("scopeNote"));
								bT.setCreator((String)embedded.get("creator"));
								bT.setType((String)embedded.get("type"));
								bT.setWikipedia((String)embedded.get("wikipedia"));
								bT.setUrl((String)embedded.get("url"));
								bT.setSubject((String)embedded.get("subject"));
							//	bT.setDdc_notation((String) embedded.get("ddc_notation"));
								BasicDBList asList = null;
								//BasicDBObject Ddc = (BasicDBObject) embedded.get("ddc_notation");
								BasicDBList Ddc = (BasicDBList) embedded.get("ddc_notation");
									for(Object dbObj : Ddc) {
										asList = new BasicDBList();
										asList.add(dbObj);
//				    				
										// shows each item from the ddc_notation array
									//	ddcnotation = dbObj.toString();
										
									}	
				    				bT.setDdc_notation(asList.toString());
								bT.setClasses((String)embedded.get("classes"));
								list.add(bT);
							}
						}
					}
		return list;
		
	}
	
	/**
	 * Gets the filtered terminologies.
	 *
	 * @return the filtered terminologies
	 */
	public List<BartocTerminology> getFilteredTerminologies() {
		return filteredTerminologies;
	}

	/**
	 * Sets the filtered terminologies.
	 *
	 * @param filteredTerminologies the new filtered terminologies
	 */
	public void setFilteredTerminologies(
			List<BartocTerminology> filteredTerminologies) {
		this.filteredTerminologies = filteredTerminologies;
	}
	
	/**
	 * Post process xls.
	 *
	 * @param document the document
	 */
//	public void postProcessXLS(Object document) {
//        HSSFWorkbook wb = (HSSFWorkbook) document;
//        HSSFSheet sheet = wb.getSheetAt(0);
//        CellStyle style = wb.createCellStyle();
//        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
// 
//        for (org.apache.poi.ss.usermodel.Row row : sheet) {
//            for (Cell cell : row) {
//                cell.setCellValue(cell.getStringCellValue().toUpperCase());
//                cell.setCellStyle(style);
//            }
//        }
//    }
	
	/**
	 * File download.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JSONException the JSON exception
	 */
	//Download the data from the database
	public void fileDownload() throws IOException, JSONException{
		String fileName = "dataTable";
		FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    ec.responseReset(); 
	    ec.setResponseContentType("application/json"); 
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); 
	    OutputStream wr = ec.getResponseOutputStream();
//	    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("D:/Software/Workplace/bartoc-terminology/WebContent/resources/dataTable.JSON");
	    fc.responseComplete();
	    JsonWriter writer = null;
		lst = getTerminology();
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for(BartocTerminology dataTable:lst){
			builder.add(Json.createObjectBuilder()
					.add("subject", Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
							.add("inScheme", Json.createArrayBuilder()
								.add(Json.createObjectBuilder().add("uri", "http://dewey.info/scheme/")
								.build())
							.build())	
							.add("notation",Json.createArrayBuilder()
								.add(dataTable.getDdc_notation().toString())
			            	.build())
			            .build())
			            .add(Json.createObjectBuilder()
			            	.add("prefLable", Json.createObjectBuilder().add("en", dataTable.getSubject())
			            		.build())
			            .build())
			        .build()) 
			        .add("CLASSES", dataTable.getClasses())
			        .add("notation", Json.createArrayBuilder()
			        		.add(dataTable.getAltLabel())
					.build())
					.add("scopeNote", Json.createArrayBuilder()
			        		.add(dataTable.getScopeNote())
			        .build())
			        .add("type", Json.createArrayBuilder()
			        		.add("http://www.w3.org/2004/o2/skos/core#ConceptScheme")
			        .build())		
					.add("TYPE", dataTable.getType())
					.add("url", dataTable.getUrl())
					.add("creator", Json.createArrayBuilder()
				    	.add(Json.createObjectBuilder().add("prefLabel", Json.createObjectBuilder()
				    		.add("de", dataTable.getCreator())
				    		.build())
				    	.build())
				    .build())
				    .add("prefLabel", Json.createObjectBuilder()
				    	.add("de", dataTable.getPrefLabel())
			        .build())
			        .add("identifier", Json.createArrayBuilder()
			        	.add(dataTable.getWikipedia())
			        .build())
			    .build());
		}
		Map<String, Object> termProperties = new HashMap<>(1);
		termProperties.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonArray btocObject = builder.build();
		JsonWriterFactory writerFactory = Json.createWriterFactory(termProperties);
		writer = writerFactory.createWriter(wr);
		writer.writeArray(btocObject);
		writer.close();
	    wr.close();
	}
	
	/**
	 * Xml download.
	 */
	public void xmlDownload(){
		
	}
	
	/**
	 * Search term.
	 */
	public void searchTerm(){
		DBObject command = BasicDBObjectBuilder
                .start("text", "data_bartoc")
                .append("search", "altLabel")
                .get();

		CommandResult result =  db.command(command);
		BasicDBList results = (BasicDBList) result.get("Description");

		for(Object o : results) {
			DBObject dbo = (DBObject) ((DBObject) o).get("obj");
			String id = (String) dbo.get("_ID");
			System.out.println(id);
		}
		
	}

//	public static void main(String[] args) throws IOException, JSONException {
//		// TODO Auto-generated method stub
//		ShowBartocTerminology col = new ShowBartocTerminology();
//		col.getTerminology();
//		col.fileDownload();
//	}
}