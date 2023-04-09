package ke.or.explorersanddevelopers.lms.model.entity;

import jakarta.persistence.*;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
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
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "INSTRUCTORS")
public class Instructor {

    @Id
    @GeneratedValue
    @Column(name = "INSTRUCTOR_ID")
    private BigDecimal instructorId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "EXPERTISE")
    private String expertise;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Course> courses = new HashSet<>();

    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private AppUser appUser;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Instructor that = (Instructor) o;
        return instructorId != null && Objects.equals(instructorId, that.instructorId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
