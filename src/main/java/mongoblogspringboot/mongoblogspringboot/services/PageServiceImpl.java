package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.dto.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    public List<Page> findById(String id){

        try (MongoClient mongoClient = MongoClients.create();) {
            //Obtengo la BD y la Colección
            MongoDatabase database = mongoClient.getDatabase("unaDb");
            MongoCollection collection = database.getCollection("unaColección");

        }

        return null;
    }

}
