package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.OrganizationTypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Organizations")
public class Organization {
    @Id
    @Column(name = "ORGANIZATION_ID")
    private BigDecimal organizationId;

    @NotNull
    @Column(name = "TITLE", unique = true)
    private String title;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "TYPE")
    private OrganizationTypeEnum type;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @ManyToMany
    @ToString.Exclude
    private List<Student> students = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    private List<Instructor> instructors = new ArrayList<>();

    @Lob
    @Column(name = "LOGO")
    private byte[] logo;

    @Null
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Null
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
