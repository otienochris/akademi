package ke.or.explorersanddevelopers.lms.model.entity;

import jakarta.persistence.*;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@Table(name = "TEST_ENROLLMENTS")
public class TestEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEST_ENROLLMENT_ID", nullable = false)
    private BigDecimal testEnrollmentId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "COMPLETION_DATE")
    private Date completionDate;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @OneToMany
    @ToString.Exclude
    private Set<Question> completedQuestions = new HashSet<>();

    @Column(name = "SCORE", nullable = false)
    private Double score;

    @OneToOne
    private Test test;

    @CreationTimestamp
    @Column(name = "CREATION_DATE")
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
        TestEnrollment that = (TestEnrollment) o;
        return testEnrollmentId != null && Objects.equals(testEnrollmentId, that.testEnrollmentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
