package ke.or.explorersanddevelopers.lms.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

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
public class Instructor extends User {

    @Id
    @GeneratedValue
    @Column(name = "INSTRUCTOR_ID")
    private UUID instructorId;

    @Column(name = "EXPERTISE")
    private String expertise;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;


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
