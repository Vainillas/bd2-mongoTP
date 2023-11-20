package mongoblogspringboot.mongoblogspringboot.services;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.exceptions.BusinessException;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.bson.Document;
import org.springframework.stereotype.Service;

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
                                    .id(String.valueOf(document.getObjectId("_id")))
                                    .title(document.getString("title"))
                                    .resume(document.getString("resume"))
                                    .build())
                    .into(List.of());
        });
    }

    @Override
    public List<Post> getPostsByAuthor(String nombreautor) {
        return executeOperation(documentMongoCollection -> {
            Document query = new Document("author", nombreautor);
            return documentMongoCollection.find(query, Post.class).into(List.of());
        });
    }

    @Override
    public List<Post> searchPosts(String text) {
        return executeOperation(collection -> {
            Document query = new Document("text", new Document("$search", text));
            return collection.find(query, Post.class).into(List.of());
        });
    }

    @Override
    public AuthorPostCount getPostCounts() {
        return executeOperation(collection -> {
            Document group = new Document("_id", "$author")
                    .append("count", new Document("$sum", 1));
            Document sort = new Document("count", -1);
            return collection.aggregate(List.of(
                    new Document("$group", group),
                    new Document("$sort", sort)
            ), AuthorPostCount.class).into(List.of()).get(0);
        });
    }

    @Override
    public Post findPost(String id) {
        return executeOperation(collection -> {
            Document query = new Document("_id", id);
            return collection.find(query, Post.class).into(List.of()).get(0);
        });
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
