package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.exceptions.BusinessException;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Service
public class PostServiceImpl extends MongoService implements PostService {

    @Override
    public List<Post> getLatest4Posts() {
        return executeOperation(collection -> {
            Document sort = new Document("date", -1);
            return collection.find().projection(fields(include("_id", "title", "resume"))).sort(sort).limit(4).map(document ->
                            Post.builder()
                                    .id(document.getObjectId("_id"))
                                    .title(document.getString("title"))
                                    .resume(document.getString("resume"))
                                    .build())
                    .into(new ArrayList<>());
        });
    }

    @Override
    public List<Post> getPostsByAuthor(String nombreautor) {
        return executeOperation(documentMongoCollection -> {
            Document query = new Document("author", nombreautor);
            return documentMongoCollection.find(query)
                    .map(document -> Post.builder()
                            .id(document.getObjectId("_id"))
                            .title(document.getString("title"))
                            .author(document.getString("author"))
                            .tags(document.getList("tags", String.class))
                            .date(LocalDate.parse(document.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .resume(document.getString("resume"))
                            .relatedLinks(document.getList("relatedLinks", String.class))
                            .text(document.getString("text"))
                            .build()
                    ).into(new ArrayList<>());
        });
    }

    @Override
    public List<Post> searchPosts(String text) {
        return executeOperation(collection -> {
            Document query = new Document("$text", new Document("$search", text));
            collection.createIndex(Indexes.text("text"));
            return collection
                    .find(query)
                    .projection(fields(include("id", "title", "resume", "author", "date")))
                    .map(document -> Post.builder()
                            .id(document.getObjectId("_id"))
                            .title(document.getString("title"))
                            .resume(document.getString("resume"))
                            .author(document.getString("author"))
                            .date(LocalDate.parse(document.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .build())
                    .into(new ArrayList<>());
        });
    }

    @Override
    public List<AuthorPostCount> getPostCounts() {
        return executeOperation(collection -> collection
                .aggregate(List.of(
                        new Document("$group", new Document("_id", "$author").append("count", new Document("$sum", 1))),
                        new Document("$sort", new Document("count", -1))
                ))
                .map(document -> AuthorPostCount.builder()
                        .author(document.getString("_id"))
                        .count(document.getInteger("count"))
                        .build())
                .into(new ArrayList<>()));
    }

    @Override
    public List<Post> findPost(String id) {
        return executeOperation(collection -> collection
                .find(Filters.eq("_id", new ObjectId(id)))
                .map(document -> Post.builder()
                        .id(document.getObjectId("_id"))
                        .title(document.getString("title"))
                        .text(document.getString("text"))
                        .tags(document.getList("tags", String.class))
                        .resume(document.getString("resume"))
                        .relatedLinks(document.getList("related-links", String.class))
                        .author(document.getString("author"))
                        .date(LocalDate.parse(document.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .into(new ArrayList<>())
        );
    }

    @Override
    public Post insertPost(Post post) {
        return executeOperation(collection -> {
            collection.insertOne(new Document("_id", post.getId())
                    .append("title", post.getTitle())
                    .append("author", post.getAuthor())
                    .append("tags", post.getTags())
                    .append("date", post.getDate().toString())
                    .append("resume", post.getResume())
                    .append("relatedLinks", post.getRelatedLinks())
                    .append("text", post.getText()));
            return post;
        });
    }

    private <T> T executeOperation(Function<MongoCollection<Document>, T> function) {
        try {
            return super.executeOperation(function, "posts", "mongo-blogs");
        } catch (Exception e) {
            throw new BusinessException("Error al ejecutar la operación", e);
        }
    }
}
