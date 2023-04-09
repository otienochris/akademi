package ke.or.explorersanddevelopers.lms.model.entity;

import jakarta.persistence.*;
import ke.or.explorersanddevelopers.lms.enums.InstitutionTypeEnum;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "INSTITUTIONS")
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    @Id
    @GeneratedValue
    @Column(name = "INSTITUTION_ID", nullable = false)
    private BigDecimal institutionId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private InstitutionTypeEnum type;

    @OneToOne
    private Address address;

    @Column(name = "LOGO")
    private byte[] logo;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Institution that = (Institution) o;
        return institutionId != null && Objects.equals(institutionId, that.institutionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
