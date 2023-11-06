package mongoblogspringboot.mongoblogspringboot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.dto.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("pages")
public class PagesController {

    private PageService pageService ;

    public PagesController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/pagina-id/{id}")
    @Operation(summary = "Recupera una pagina en base a su id")
    public List<Page> getPageById(@PathVariable String id) {
        // Implementa la lógica para recuperar una página por su ID
        return this.pageService.findById(id);

    }

    @GetMapping("/byautor")
    public List<AuthorPostCount> getPagesByAuthor() {
        // Implementa la lógica para obtener un array de objetos id, count
        // donde id es el nombre del autor y count la cantidad de posts que realizó
        return null;
    }

    @GetMapping("/ultimos4posts")
    public List<Page> getLatest4Posts() {
        // Implementa la lógica para recuperar los últimos 4 posts ordenados por fecha
        return null;
    }

    @GetMapping("/posts-autor/{nombreautor}")
    public List<Page> getPostsByAuthor(@PathVariable String nombreautor) {
        // Implementa la lógica para obtener todos los posts para un autor dado su nombre
        return null;
    }

    @GetMapping("/search/{text}")
    public List<Page> searchPosts(@PathVariable String text) {
        // Implementa la lógica para realizar una búsqueda libre dentro del texto del documento
        return null;
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception exception) {
        exception.printStackTrace();
    }

}
