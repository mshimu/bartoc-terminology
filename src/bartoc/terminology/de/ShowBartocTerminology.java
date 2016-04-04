package bartoc.terminology.de;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;

import bartoc.terminology.de.BartocTerminology;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;  
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.naming.Context;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;  

import org.primefaces.component.row.Row;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;

import org.apache.poi.hssf.usermodel.HSSFRow;
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
	private List<BartocTerminology> list = new ArrayList<BartocTerminology>();
	private DBObject query=null;
	private BasicDBObject fields=null;
	private String queryName;
	private String fieldName;
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
//		System.out.println(getListName());
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

	public List<BartocTerminology> getTerminology(){
		try {
			mongo = new Mongo("localhost",27017);
			db = mongo.getDB("MongoDatabase");
			table = db.getCollection("data_bartoc");
			list = new ArrayList<BartocTerminology>();
			BartocTerminology bT = null;
			setQuery(new BasicDBObject("Classification", getListName()));
			setFields(new BasicDBObject("_id",0).append("Classification", 0));
	
			DBCursor cursor = table.find(getQuery(), getFields());
				while (cursor.hasNext()) {
					DBObject obj = cursor.next();
					String ddcnotation = null;
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
								BasicDBList Ddc = (BasicDBList) embedded.get("ddc_notation");
									for(Object dbObj : Ddc) {
				    			
										// shows each item from the ddc_notation array
										ddcnotation = dbObj.toString();
									}
								bT.setClasses((String)embedded.get("classes"));
								list.add(bT);
								
							}
						}
					}		
		}catch (IOException e) {
			 e.printStackTrace();	
		}
		return list;
		
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
	
	public void searchTerm(){
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ShowBartocTerminology col = new ShowBartocTerminology();
		col.getTerminology();
	}
}
