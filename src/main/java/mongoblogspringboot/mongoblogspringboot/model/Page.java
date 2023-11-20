package mongoblogspringboot.mongoblogspringboot.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {
    private String id;
    private String title;
    private String author;
    private String text;
    private LocalDate date;

}
