package ke.or.explorersanddevelopers.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private UUID answerId;

    private String content;
    private boolean isCorrect;


}
