package Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import Parser.CourseCSVParser;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoLab {
	
	private Mongo mongo;
	private MongoClient mongoClient;
	private MongoDatabase db;
	
	
	@SuppressWarnings("deprecation")
	private void configureMongoLab()
	{
		mongoClient = new MongoClient(Constants.MONGO_CONNECTION_URL, Constants.MONGO_CONNECTION_URL_PORT);
		String URL = "mongodb://"+Constants.MONGO_DB_USERNAME+":"+Constants.MONGO_DB_PASSWORD+"@"+Constants.MONGO_CONNECTION_URL+":"+Constants.MONGO_CONNECTION_URL_PORT+"/"+Constants.MONGO_DATABASE_NAME;
		MongoClientURI uri = new MongoClientURI(URL);
		mongoClient = new MongoClient(uri);
		db =  mongoClient.getDatabase(Constants.MONGO_DATABASE_NAME);
	}
	
	public void addToCollection(Document object, String collectionName) {
		if (mongoClient == null || db == null) {
			configureMongoLab();
		}
		MongoCollection<Document> recommendationCollection =  db.getCollection(collectionName);
		
		recommendationCollection.insertOne(object);
	}
	
	public HashMap<String,Integer> getMappedQuizIds(String collectionName) {
		if (mongoClient == null || db == null) {
			configureMongoLab();
		}
		MongoCollection<Document> recommendationCollection =  db.getCollection(collectionName);
		ArrayList<Integer> list = new ArrayList<Integer>();
		//org.bson.Document doc = new Document("");
		
		MongoCursor<Document> cursor = recommendationCollection.find().iterator();
		ArrayList<String> quizzes = new CourseCSVParser().getQuizIds();
		HashMap<String,Integer> mapping = new HashMap<String,Integer>();
		
		try {
			int k = 0;int sum = 0;
		    while (cursor.hasNext()) {
		    	Document doc = cursor.next();
		    	Integer id = (Integer) doc.get("peoplesoftId");
		    	list.add(id);
		        System.out.println(id);
		        int count = 0;
		        ArrayList<String> ids = new ArrayList<String>();
		        int quizCount = (int) (Math.random()*10);
		        System.out.println("QuizCount" + quizCount);
		        
		        while(count < quizCount)
		        {
		        	count++;
		        	//ids.add(quizzes.get(k++));
		        	mapping.put(quizzes.get(k++), id);
		        	
		        }
		        sum +=count;
		    }
		    System.out.println("total quizzes " + sum);
		} finally {
		    cursor.close();
		}
		
		return mapping;
		
	}
	
	
	
}
