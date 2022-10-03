package ke.or.explorersanddevelopers.lms.model.entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 03/10/2022
 */

@Entity
@Table(name = "STUDENTS")
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "STUDENT_ID", nullable = false)
    private UUID studentId;
}
