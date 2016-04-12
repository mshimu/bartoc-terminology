package bartoc.terminology.de;

import java.io.IOException;
import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import bartoc.terminology.de.BartocTerminology;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Test_Data {
	DBCollection table = null;
	DB db = null;
	Mongo mongo = null;
	private List<BartocTerminology> list = new ArrayList<BartocTerminology>();
	private List<BartocTerminology> lst;
	private BartocTerminology bT = new BartocTerminology();
	private List<BartocTerminology> getLst(){ return lst;}
	private String ddcnotation = null;

	private void setLst(List<BartocTerminology> lst) {
	    this.lst = lst;
	}
	
	public List<BartocTerminology> getThesauriList() throws UnknownHostException{
		try{
			mongo = new Mongo("localhost", 27017);
			db = mongo.getDB("MongoDatabase");
			table = db.getCollection("data_bartoc");
	//		DBCollection table = TerDescription.getConnection("MongoDatabase", "data_bartoc");
			DBObject query = new BasicDBObject("Classification", "Thesauri");
//			query.put("text", "data_bartoc");
//			query.put("search", "SSD");
//			CommandResult commandResult = db.command(query);
//			System.out.println(commandResult);
			BasicDBObject fields = new BasicDBObject("_id",0).append("Classification", 0);
			DBCursor cursor = table.find(query, fields);
//			DBCursor cursor = table.find();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
//				System.out.println(obj);
				
				ArrayList<DBObject> description = (ArrayList<DBObject>)obj.get("Description"); 
					for(DBObject embedded : description){
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
				    			ddcnotation= dbObj.toString();
				    	//		System.out.println(dbObj.toString());
				    		}
				    	bT.add(ddcnotation);
				    	bT.setClasses((String)embedded.get("classes"));
//				    	System.out.println("the prefLabel is : " + (String)embedded.get("prefLabel") 
//				    			+ "\n" +(String)embedded.get("altLabel") + "\n" + ddcnotation);
						
//				JsonReader reader = Json.createReader(new StringReader(obj.toString()));  
//				JsonObject terminologyObject = reader.readObject(); 
//				bT.setPrefLabel(terminologyObject.getString("prefLabel"));
//				System.out.println("Pre: " + terminologyObject.getString("prefLabel"));
//				bT.setAltLabel(terminologyObject.getString("altLabel"));
//		    	bT.setScopeNote(terminologyObject.getString("scopeNote"));
//		    	System.out.println("Pre: " + terminologyObject.getString("prefLabel"));
//		    	bT.setCreator(terminologyObject.getString("creator"));
//		    	bT.setType(terminologyObject.getString("type"));
//		    	bT.setWikipedia(terminologyObject.getString("wikipedia"));
//		    	bT.setUrl(terminologyObject.getString("url"));
//		    	bT.setSubject(terminologyObject.getString("subject"));
//		   // 	bT.setDdc_notation(terminologyObject.getInt("ddc_notation"));
//		    	bT.setClasses(terminologyObject.getString("classes"));
//		    	JsonArray jsonArray = terminologyObject.getJsonArray("ddc_notation");
//		    	String[] numbers = new String[jsonArray.size()];
//		    	int index = 0;
//		    		for(JsonValue value : jsonArray){
//		        		numbers[index++] = value.toString();
//		    		}
//		    	bT.setDdc_notation(numbers);

		    	
//			}			
				    	
						list.add(bT);
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();	
		}
		System.out.println("the is :");
		
		for(BartocTerminology test:list){
			System.out.println(test.getAltLabel());
			System.out.println(test.getPrefLabel());
			System.out.println(test.getDdc_notation());
		}
	return list;
	}
	
	public void getList() throws Exception{
		TerDescription td = new TerDescription();
		DBObject query = new BasicDBObject("Classification", "Thesauri");
	    BasicDBObject fields = new BasicDBObject("_id",0).append("Classification", 0);
		td.getTerminology(query, fields);
		
	}
	public void fileDownload() throws IOException, JSONException{
		lst = getThesauriList();
		for(BartocTerminology dataTable:lst){
			System.out.println("The new data" +dataTable.getAltLabel());
			System.out.println(dataTable.getPrefLabel());
			System.out.println(dataTable.getDdc_notation());
			JsonObject btocObject = (JsonObject) Json.createObjectBuilder()
				.add("Subject", Json.createArrayBuilder()
					.add(Json.createObjectBuilder().add("prefLabel", dataTable.getPrefLabel())
					.build())
//		            .add(Json.createObjectBuilder().add("notation",Json.createArrayBuilder()
//		            	.add(dataTable.getDdc_notation().toString())
//		            	.build())
//		            .build())	
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
			System.out.println("The new data" + btocObject);
		}
		
//	return lst;
//		JSONObject objTable = new JSONObject();
//	    JSONArray arrTable = new JSONArray();
//	    arrTable.put(b);
//	    objTable.put("Description:", arrTable);
//	    System.out.println("Json data:" + objTable);
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Test_Data ts = new Test_Data();
//		ts.getThesauriList();
//		ts.getList();
		ts.fileDownload();
	}

}
