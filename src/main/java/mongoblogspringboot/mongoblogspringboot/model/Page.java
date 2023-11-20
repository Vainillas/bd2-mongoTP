package mongoblogspringboot.mongoblogspringboot.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {
    private ObjectId id;
    private String title;
    private String author;
    private String text;
    private LocalDate date;

}
