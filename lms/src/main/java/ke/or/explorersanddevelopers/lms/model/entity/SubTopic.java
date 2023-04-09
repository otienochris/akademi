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
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name = "SUBTOPICS")
public class SubTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBTOPIC_ID", nullable = false)
    private BigDecimal subTopicId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "LINK")
    private String link;

    @Lob
    @Column(name = "CONTENT")
    private String content;

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
        SubTopic subTopic = (SubTopic) o;
        return subTopicId != null && Objects.equals(subTopicId, subTopic.subTopicId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
