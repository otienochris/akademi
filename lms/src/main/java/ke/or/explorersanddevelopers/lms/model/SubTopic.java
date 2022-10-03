package ke.or.explorersanddevelopers.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Clob;
import java.util.UUID;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID subTopicId;
    @Column(nullable = false)
    private String title;
    private String description;
    private String link;
    private Clob content;

}
