package ke.or.explorersanddevelopers.lms.model.security;


import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private BigDecimal id;

    private String name;
}
