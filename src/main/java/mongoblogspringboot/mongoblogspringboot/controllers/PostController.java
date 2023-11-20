package mongoblogspringboot.mongoblogspringboot.controllers;

import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/latest")
    public List<Post> getLatest4Posts() {
        // Implementa la lógica para recuperar los últimos 4 posts ordenados por fecha
        return this.postService.getLatest4Posts();
    }
    @GetMapping("/{id}")
    public List<Post> getPostById(@PathVariable String id) {
        // Implementa la lógica para recuperar un post por su ID
        return this.postService.findPost(id);
    }


    @GetMapping("/author/{nombreautor}")
    public List<Post> getPostsByAuthor(@PathVariable String nombreautor) {
        // Implementa la lógica para obtener todos los posts para un autor dado su nombre
        return this.postService.getPostsByAuthor(nombreautor);
    }
    @GetMapping("/byauthor")
    public List<AuthorPostCount> getPostCountByAuthor() {
        // Implementa la lógica para obtener todos los posts para un autor dado su nombre
        return this.postService.getPostCounts();
    }
    @GetMapping("/search/{text}")
    public List<Post> searchPosts(@PathVariable String text) {
        // Implementa la lógica para realizar una búsqueda libre dentro del texto del documento
        return this.postService.searchPosts(text);
    }

}
