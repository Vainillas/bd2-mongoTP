package mongoblogspringboot.mongoblogspringboot.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private ObjectId id;
    private String title;
    private String author;
    private String text;
    private List<String> tags;
    private String resume;
    private List<String> relatedLinks;
    private LocalDate date;
}
