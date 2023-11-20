package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.ConnectionString;
import com.mongodb.Function;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoblogspringboot.mongoblogspringboot.config.LocalDateCodecProvider;
import mongoblogspringboot.mongoblogspringboot.exceptions.BusinessException;
import org.bson.Document;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.IterableCodecProvider;
import org.bson.codecs.MapCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.types.ObjectId;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.LocalDateCodec;
import org.bson.codecs.pojo.PojoCodecProvider;

public abstract class MongoService {
    MongoClientSettings settings;
    CodecRegistry codecRegistry;
    /*MongoService(){
        ConnectionString connectionString = new ConnectionString("mongodb://root:test.123@localhost:27017/?authSource=admin");
        codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build(),
                        new DocumentCodecProvider(),
                        new IterableCodecProvider(),
                        new MapCodecProvider(),
                        new LocalDateCodecProvider()
                ));
        settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                // Otras configuraciones
                .applyConnectionString(connectionString)

                .build();
    }*/
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
