package ke.or.explorersanddevelopers.lms.model;

import ke.or.explorersanddevelopers.lms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private UUID courseEnrollmentId;

    private UUID studentId;
    private UUID courseId;

    @Enumerated(value = EnumType.STRING)
    private Status status;

//    private List<UUID> testEnrollment;
    private Double amount;
    private Date completionDate;
//    private List<UUID> completedTopics;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

}
