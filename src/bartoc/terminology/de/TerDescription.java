package bartoc.terminology.de;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class TerDescription {
	
	DBCollection table = null;
	DB db = null;
	Mongo mongo = null;
	private List<BartocTerminology> list = new ArrayList<BartocTerminology>();
	private BartocTerminology bT = new BartocTerminology();
	
	public static DBCollection getConnection(String dbName, String collectionName)throws UnknownHostException {
		 
		/** Connecting to MongoDB */
		MongoClient mongo = new MongoClient("localhost", 27017);

		        /**Gets database, incase if the database is not existing MongoDB Creates it for you*/
		DB db = mongo.getDB(dbName);
		
		/**Gets collection / table from database specified if 
		* collection doesn't exists, MongoDB will create it for you*/
		
		DBCollection table = db.getCollection(collectionName);
		return table;
	}
	
	private  void InsertDB() throws UnknownHostException{
		//DBCollection table = MongodbConnection.getConnection("testdatabase", "mappings1");
		mongo = new Mongo("localhost",27017);
		db = mongo.getDB("MongoDatabase");
		table = db.getCollection("data_bartoc");
		
		String strLine = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:/Software/Project_ColiConc/Data/file.Json"));
				while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				 
				DBObject dbo = (DBObject) JSON.parse(strLine);
//				List<DBObject> list = new ArrayList<>();
//				list.add(dbo);
//				System.out.println(list);
//				new MongoClient().getDB("testdatabase").getCollection("mappings1").insert(list);
				
				//DBObject dbo= null;
	            //sb.append(dbo = (DBObject) JSON.parse(strLine));
				table.insert(dbo);
				
	//                map.save(dbo);
//	                DBCursor cursorDoc = map.find();
//	                
//	                while (cursorDoc.hasNext()) {
//	                    System.out.println(cursorDoc.next());
//	                }
			 }
		} catch (IOException e) {
			 e.printStackTrace();	
		}
	}
	
	public List<BartocTerminology> getTerminology(DBObject dso, BasicDBObject bso){
		try {
			mongo = new Mongo("localhost",27017);
			db = mongo.getDB("MongoDatabase");
			table = db.getCollection("data_bartoc");
			DBObject query = dso;
			DBObject fields = bso;
			DBCursor cursor = table.find(query, fields);
				while (cursor.hasNext()) {
					DBObject obj = cursor.next();
					String ddcnotation = null;
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
									ddcnotation = dbObj.toString();
								}
							bT.setClasses((String)embedded.get("classes"));
							System.out.println("the prefLabel is : " + (String)embedded.get("prefLabel") 
			    				+ "\n" +(String)embedded.get("altLabel") + "\n" + ddcnotation);
							list.add(bT);
							
						}	
				}		
		}
		catch (IOException e) {
			 e.printStackTrace();	
		}
		System.out.println("the is :" + list);
		return list;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TerDescription col = new TerDescription();
		//col.InsertDB();
		DBObject query = new BasicDBObject("Classification", "Subject Classifications");
	    BasicDBObject fields = new BasicDBObject("_id",0).append("Classification", 0);
		col.getTerminology(query, fields);
	}

}
