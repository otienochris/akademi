package ke.or.explorersanddevelopers.lms.model;

import ke.or.explorersanddevelopers.lms.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private UUID questionId;
    @Column(nullable = false)
    private String question;
    private  String hint;
    private String level;
    private Double successRate;
    private Long period;
    private AnswerType answerType;
//    private List<UUID> answers;

}
