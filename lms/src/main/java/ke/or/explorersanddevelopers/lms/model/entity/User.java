package ke.or.explorersanddevelopers.lms.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Getter
@Setter
//@Builder
@ToString
//@AllArgsConstructor
//@NoArgsConstructor

@MappedSuperclass
public abstract class User {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "IS_ACCOUNT_DISABLED")
    private boolean isAccountDisabled;

    @Column(name = "EMAIL_VERIFICATION_CODE")
    private UUID emailVerificationCode;

    @OneToMany
    @ToString.Exclude
    private List<Address> addresses;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews;

    @Column(name = "PASSWORD")
    private String password;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
