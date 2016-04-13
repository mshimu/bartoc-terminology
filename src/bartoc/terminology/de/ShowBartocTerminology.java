package bartoc.terminology.de;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import bartoc.terminology.de.BartocTerminology;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;  
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;  
import org.primefaces.json.JSONException;
import org.primefaces.model.StreamedContent;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
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


@ManagedBean(name = "showTerminology")  
@ApplicationScoped
public class ShowBartocTerminology {
	DBCollection table = null;
	DB db = null;
	Mongo mongo = null;
	DBCursor cursor = null;
	private DBObject query=null;
	private BasicDBObject fields=null;
	private List<BartocTerminology> list = new ArrayList<BartocTerminology>();
	private List<BartocTerminology> lst;
	private List<BartocTerminology> filteredTerminologies = null;
	private String listName;

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}
	
	public DBObject getQuery() {
		return query;
	}

	public void setQuery(DBObject query) {
		this.query = query;
	}

	public BasicDBObject getFields() {
		return fields;
	}

	public void setFields(BasicDBObject fields) {
		this.fields = fields;
	}
	
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

	public List<BartocTerminology> getTerminology() throws IOException{
//			mongo = new Mongo("localhost",27017);
//			db = mongo.getDB("MongoDatabase");
			mongo = new MongoClient(new ServerAddress("esx-128.gbv.de", 27017));
               //     new MongoClientOptions.Builder().connectTimeout(300000).socketTimeout(300000).build());
			db = mongo.getDB("BartocDatabase");
			table = db.getCollection("data_bartoc");
			list = new ArrayList<BartocTerminology>();
			BartocTerminology bT = null;
			setQuery(new BasicDBObject("Classification", getListName()));
			setFields(new BasicDBObject("_id",0).append("Classification", 0));
	
			DBCursor cursor = table.find(getQuery(), getFields());
				while (cursor.hasNext()) {
					DBObject obj = cursor.next();
//					String ddcnotation = null;
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
								bT.setDdc_notation((String) embedded.get("ddc_notation"));
//								BasicDBList Ddc = (BasicDBList) embedded.get("ddc_notation");
//									for(Object dbObj : Ddc) {
//				    			
//										// shows each item from the ddc_notation array
//										ddcnotation = dbObj.toString();
//									}	
								bT.setClasses((String)embedded.get("classes"));
								list.add(bT);
							}
						}
					}
		return list;
		
	}
	
	public List<BartocTerminology> getFilteredTerminologies() {
		return filteredTerminologies;
	}

	public void setFilteredTerminologies(
			List<BartocTerminology> filteredTerminologies) {
		this.filteredTerminologies = filteredTerminologies;
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
 
        for (org.apache.poi.ss.usermodel.Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                cell.setCellStyle(style);
            }
        }
    }
	
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
		for(BartocTerminology dataTable:lst){
			JsonObject btocObject = (JsonObject) Json.createObjectBuilder()
				.add("Subject", Json.createArrayBuilder()
					.add(Json.createObjectBuilder().add("prefLabel", dataTable.getPrefLabel())
					.build())
		            .add(Json.createObjectBuilder().add("notation",Json.createArrayBuilder()
		            	.add(dataTable.getDdc_notation().toString())
		            	.build())
		            .build())	
    				.add(Json.createObjectBuilder().add("inScheme", Json.createArrayBuilder()
    					 .add(Json.createObjectBuilder().add("altLabel", dataTable.getAltLabel())
		                 .add("url", dataTable.getUrl()).add("identifier", dataTable.getWikipedia())
    					 	.build())
    					 .build())
    				.build())	 
		            .add(Json.createObjectBuilder().add("scopeNote", dataTable.getScopeNote()))
		            .add(Json.createObjectBuilder().add("type", dataTable.getType()))
		            .add(Json.createObjectBuilder().add("creator", dataTable.getCreator()))
		            .add(Json.createObjectBuilder().add("subject", dataTable.getSubject()))
		            .add(Json.createObjectBuilder().add("classes", dataTable.getClasses()))
		        .build())
		    .build();
			writer = Json.createWriter(wr);
		    writer.writeObject(btocObject); 
		}
		writer.close();
	    wr.close();
	}
	
	public void searchTerm(){
		
	}

//	public static void main(String[] args) throws IOException, JSONException {
//		// TODO Auto-generated method stub
//		ShowBartocTerminology col = new ShowBartocTerminology();
//		col.getTerminology();
//		col.fileDownload();
//	}
}
