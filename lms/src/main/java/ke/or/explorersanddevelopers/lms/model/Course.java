package ke.or.explorersanddevelopers.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Monday 26/09/2022
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private UUID courseId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    public String description;
    @Column(nullable = false)
    private String thumbnailLink;
    private Integer rating;
    private BigDecimal price;
   @Column(nullable = false)
    private String category;
//    private List reviews;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;
    private String introductionVideoLink;

//    @OneToMany
//    private List<CourseEnrollment> courseEnrollments;
//    private List topics;
}
