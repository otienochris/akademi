package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name = "COURSE_ENROLLMENTS")
public class CourseEnrollment {
    @Id
    @GeneratedValue
    @Column(name = "COURSE_ENROLLMENT_ID", nullable = false)
    private BigDecimal courseEnrollmentId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "COMPLETION_DATE")
    private Date completionDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "STUDENT_ID", name = "STUDENT_ID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(referencedColumnName = "COURSE_ID", name = "COURSE_ID", nullable = false)
    private Course course;

    @OneToMany
    @ToString.Exclude
    private List<TestEnrollment> testEnrollments = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Topic> completedTopics = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CourseEnrollment that = (CourseEnrollment) o;
        return courseEnrollmentId != null && Objects.equals(courseEnrollmentId, that.courseEnrollmentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
