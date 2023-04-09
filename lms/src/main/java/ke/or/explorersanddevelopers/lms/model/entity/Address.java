package ke.or.explorersanddevelopers.lms.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
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
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ADDRESSES")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private BigDecimal addressId;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

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
        Address address = (Address) o;
        return addressId != null && Objects.equals(addressId, address.addressId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
