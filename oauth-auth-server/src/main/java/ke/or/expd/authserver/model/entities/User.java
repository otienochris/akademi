package ke.or.expd.authserver.model.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(schema = "AKADEMI", name = "users")
public class User {

    @Id
    @GeneratedValue
    private BigDecimal id;
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private String role;
    private boolean enabled = false;
}
