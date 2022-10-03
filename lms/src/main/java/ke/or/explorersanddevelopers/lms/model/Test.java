package ke.or.explorersanddevelopers.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private UUID testId;

    @GeneratedValue
    @Column(nullable = false)
    private UUID topicId;
    @Column(nullable = false)
    private String testType;
    @Column(nullable = false)
    private boolean isOptional;
    @Column(nullable = false)
    private boolean isScheduled;
    @Column(nullable = false)
    private Date startDateAndTime;
    @Column(nullable = false)
    private Date endDateAndTime;
//    private List<String> questions = new ArrayList<>();



}
