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
 * @since Monday, 03/10/2022
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "STUDENTS")
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "STUDENT_ID", nullable = false)
    private BigDecimal studentId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Certificate> certificates = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Relative> relatives = new HashSet<>();

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false)
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
        Student student = (Student) o;
        return studentId != null && Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
