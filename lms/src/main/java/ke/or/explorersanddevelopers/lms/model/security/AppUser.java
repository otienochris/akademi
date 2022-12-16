package ke.or.explorersanddevelopers.lms.model.security;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    private static final long serialVersionUID = -6031797646032425241L;
    @Id
    @GeneratedValue
    private BigDecimal id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "IS_ACCOUNT_DISABLED")
    private boolean isAccountDisabled;

    @Column(name = "EMAIL_VERIFICATION_CODE")
    private String emailVerificationCode;

    @Column(name = "TOKEN")
    private String token;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isAccountDisabled;
    }
}
