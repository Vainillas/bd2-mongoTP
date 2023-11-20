package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.Function;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import mongoblogspringboot.mongoblogspringboot.exceptions.BusinessException;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageServiceImpl extends MongoService implements PageService {

    public List<Page> findById(String id){
        return executeOperation(collection -> {
            Document query = new Document("_id", id);
            return collection.find(query).map(
                    page -> Page.builder()
                            .id(page.getObjectId("_id"))
                            .title(page.getString("title"))
                            .author(page.getString("author"))
                            .text(page.getString("text"))
                            .date(LocalDate.parse(page.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .build()
            ).into(new ArrayList<>());
        });
    }



    @Override
    public Page insertPage(Page page) {
        return executeOperation(collection -> {
            collection.insertOne(new Document("_id", page.getId())
                    .append("title", page.getTitle())
                    .append("author", page.getAuthor())
                    .append("text", page.getText())
                    .append("date", page.getDate().toString()));
            return page;
        });
    }

    private <T> T executeOperation(Function<MongoCollection<Document>, T> function){
        try{
         return super.executeOperation(function, "pages", "mongo-blogs");
        }catch (Exception e){
            throw new BusinessException("Error al ejecutar la operación", e);
        }
    }


}
