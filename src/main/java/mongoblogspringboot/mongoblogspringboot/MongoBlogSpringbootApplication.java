package mongoblogspringboot.mongoblogspringboot;

import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.apache.catalina.core.ApplicationContext;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MongoBlogSpringbootApplication {
    private PageService pageService;
    private PostService postService;
    public MongoBlogSpringbootApplication(PageService pageService, PostService postService) {
        this.pageService = pageService;
        this.postService = postService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MongoBlogSpringbootApplication.class, args);

    }
    private void insertPage() {
        Page page1 = Page.builder().id(new ObjectId())
                .title("Patagonia, tierra de dinosaurios.")
                .text("Secretos sobre el surgimiento, desarrollo y extinción de los dinosaurios en la Patagonia.")
                .author("Aliberti Mateo")
                .date(LocalDate.now().minusDays(10))
                .build();

        pageService.insertPage(page1);
    }

    private void insertPost() {
        Post post1 = Post.builder().id(new ObjectId())
                .title("El toro carnívoro: Carnotaurus Sastrei")
                .text("El Toro Carnívoro (Carnotaurus) fue uno de los temibles depredadores del periódo Cretácico. Los huesos de este animal fueron locaizados en los campos de un estanciero de apellido Sastre que termnó por darle apellido a esta criatura que paseó sus diez metros de longitud lo que hoy se conoce como la Pampa de Gastre, en la provincia de Chubut.")
                .resume("Uno de los mayores depredadores carnívoros de la Patagonia, el Carnotaurus Sastrei, fue descubierto en la provincia de Chubut.")
                .tags(Arrays.asList("Patagonia", "Cretácico", "Carnívoro"))
                .relatedLinks(Arrays.asList("https://www.patagonia.com.ar/dinosaurios/662_Carnotaurus+Sastrei.html", "https://www.patagonia.com.ar/El+Choc%C3%B3n/207_Rub%C3%A9n+Carolini+el+descubridor+del+dinosaurio+carn%C3%ADvoro+m%C3%A1s+grande.html"))
                .author("Aliberti Mateo")
                .date(LocalDate.now().minusDays(8))
                .build();
        Post post2 = Post.builder().id(new ObjectId())
                .title("El carnívoro más grande del mundo")
                .text("El 25 de julio de 1993, el mecánico desocupado Rubén Carolini se topó con los huesos fosilizados del que se transformaría en el dinosaurio carnívoro más grande del mundo, que desplazó del reinado nada menos que al famosísimo Tyranosuaurus Rex de Estados Unidos.")
                .resume("El descubrimiento del Gigante del Sur")
                .tags(Arrays.asList("Patagonia", "Cretácico", "Carnívoro"))
                .relatedLinks(List.of("https://www.patagonia.com.ar/dinosaurios/663_Gigantosaurus+Carolini.html"))
                .author("Aliberti Mateo")
                .date(LocalDate.now().minusDays(10))
                .build();
        postService.insertPost(post1);
        postService.insertPost(post2);
    }
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            /*insertPage();
            insertPost();*/
        };
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("").allowedMethods("").allowedHeaders("");
            }
        };
    }




}
