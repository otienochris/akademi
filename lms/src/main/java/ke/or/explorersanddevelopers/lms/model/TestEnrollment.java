package ke.or.explorersanddevelopers.lms.model;

import ke.or.explorersanddevelopers.lms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID testEnrollmentId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;
    private Double amount;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
//    private List<String> completedQuestions = new ArrayList<>();

    private Double score;
//    private List<UUID> tests;
















}
