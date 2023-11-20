package mongoblogspringboot.mongoblogspringboot.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private String id;
    private String title;
    private String author;
    private String text;
    private List<String> tags;
    private String resume;
    private List<String> relatedLinks;
    private LocalDate date;
}
