package mongoblogspringboot.mongoblogspringboot;

import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

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
        Page page1 = Page.builder()
                .title("Patagonia, tierra de dinosaurios.")
                .text("Secretos sobre el surgimiento, desarrollo y extinción de los dinosaurios en la Patagonia.")
                .author("Aliberti Mateo")
                .date(LocalDate.now().minusDays(10))
                .build();

        pageService.insertPage(page1);
    }

    private void insertPost() {
    }
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            insertPage();
            // insertPost();
        };
    }



}
