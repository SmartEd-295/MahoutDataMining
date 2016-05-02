package Utility;

import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
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
	
}
