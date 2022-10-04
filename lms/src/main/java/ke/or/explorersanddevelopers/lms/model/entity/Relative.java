package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "RELATIVES")

public class Relative extends User {

    @Id
    @GeneratedValue
    @Column(name = "RELATIVE_ID")
    private UUID relativeId;

    @Column(name = "RELATIVE_TYPE")
    @Enumerated(EnumType.STRING)
    private RelativeTypeEnum relativeType;

    @Column(name = "ROLE")
    private RelativeRoleEnum role;
}
