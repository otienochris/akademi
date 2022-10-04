package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.CertificateStatusEnum;
import ke.or.explorersanddevelopers.lms.enums.CertificateTypeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "CERTIFICATES")
public class Certificate {

    @Id
    @GeneratedValue
    @Column(name = "CERTIFICATE_ID")
    private UUID certificateId;

    @Column(name = "TYPE", nullable = false)
    private CertificateTypeEnum type;

    @Column(name = "COURSE_TITLE")
    private String courseTitle;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private CertificateStatusEnum status;

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
        Certificate that = (Certificate) o;
        return certificateId != null && Objects.equals(certificateId, that.certificateId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
