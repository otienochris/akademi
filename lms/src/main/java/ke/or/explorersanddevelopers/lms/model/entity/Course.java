package ke.or.explorersanddevelopers.lms.model.entity;

import jakarta.persistence.*;
import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
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

    @Id
    @GeneratedValue
    @Column(name = "COURSE_ID", nullable = false)
    private BigDecimal courseId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    public String description;

    @Column(name = "THUMBNAIL_LINK", nullable = false)
    private String thumbnailLink;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum category;

    @Column(name = "INTRODUCTION_VIDEO_LINK")
    private String introductionVideoLink;

    @OneToMany
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<CourseEnrollment> courseEnrollments = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<Topic> topics = new HashSet<>();

    @ManyToMany
    @ToString.Exclude
    private Set<Instructor> instructors = new HashSet<>();

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
        Course course = (Course) o;
        return courseId != null && Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
