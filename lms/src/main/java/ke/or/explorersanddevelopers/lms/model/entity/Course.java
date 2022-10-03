package ke.or.explorersanddevelopers.lms.model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Monday 26/09/2022
 **/

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "COURSES")
public class Course {
    @Column(name = "DESCRIPTION", nullable = false)
    public String description;
    @Id
    @GeneratedValue
    @Column(name = "COURSE_ID", nullable = false)
    private UUID courseId;
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "THUMBNAIL_LINK", nullable = false)
    private String thumbnailLink;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "INTRODUCTION_VIDEO_LINK")
    private String introductionVideoLink;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<CourseEnrollment> courseEnrollments = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Topic> topics = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return courseId != null && Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
