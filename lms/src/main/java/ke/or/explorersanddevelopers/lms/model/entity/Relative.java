package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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

public class Relative  {

    @Id
    @GeneratedValue
    @Column(name = "RELATIVE_ID")
    private BigDecimal relativeId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "RELATIVE_TYPE")
    @Enumerated(EnumType.STRING)
    private RelativeTypeEnum relativeType;

    @Column(name = "ROLE")
    private RelativeRoleEnum role;

    @Column(name = "IS_ACCOUNT_DISABLED")
    private boolean isAccountDisabled;

    @Column(name = "EMAIL_VERIFICATION_CODE")
    private UUID emailVerificationCode;

    @OneToMany
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "relatives")
    @ToString.Exclude
    private List<Student> students = new ArrayList<>();

    @Column(name = "PASSWORD")
    private String password;

    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
