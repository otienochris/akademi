package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.TestTypeEnum;
import ke.or.explorersanddevelopers.lms.enums.YesOrNoEnum;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

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
@Table(name = "TESTS")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEST_ID", nullable = false)
    private UUID testId;

    @OneToMany
    @ToString.Exclude
    private List<Topic> topics = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "TEST_TYPE", nullable = false)
    private TestTypeEnum testType;

    @Column(name = "IS_OPTIONAL", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum isOptional;

    @Column(name = "IS_SCHEDULED", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum isScheduled;

    @Column(name = "START_DATE_AND_TIME", nullable = false)
    private Date startDateAndTime;

    @Column(name = "END_DATE_AND_TIME", nullable = false)
    private Date endDateAndTime;

    @OneToMany
    @ToString.Exclude
    private List<Question> questions = new ArrayList<>();

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
        Test test = (Test) o;
        return testId != null && Objects.equals(testId, test.testId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
