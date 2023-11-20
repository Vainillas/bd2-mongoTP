package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.Function;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoblogspringboot.mongoblogspringboot.exceptions.BusinessException;
import org.bson.Document;

public abstract class MongoService {
    public <T> T executeOperation(Function<MongoCollection<Document>, T> function, String collectionName, String databaseName){
        try(MongoClient mongoClient = MongoClients.create("mongodb://root:test.123@localhost:27017/?authSource=admin")){
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            try {
                return function.apply(collection);
            } catch (Exception e) {
                throw new BusinessException("Error al ejecutar la operación", e);
            }
        }
    }
    public  <T> T executeOperation(Function<MongoCollection<Document>, T> function, String collectionName){
        try(MongoClient mongoClient = MongoClients.create("mongodb://root:test.123@localhost:27017/?authSource=admin")){
            MongoDatabase database = mongoClient.getDatabase("mongo-blogs");
            MongoCollection<Document> collection = database.getCollection(collectionName);
            try {
                return function.apply(collection);
            } catch (Exception e) {
                throw new BusinessException("Error al ejecutar la operación", e);
            }
        }
    }
}
