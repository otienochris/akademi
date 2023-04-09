package ke.or.explorersanddevelopers.lms.model.security;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
