package ke.or.explorersanddevelopers.lms.model.entity;

import ke.or.explorersanddevelopers.lms.enums.YesOrNoEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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
@Table(name = "ANSWERS")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ANSWER_ID", nullable = false)
    private UUID answerId;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IS_CORRECT", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum isCorrect;

    @Column(name = "REASON")
    private String reason;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getAnswerId().equals(answer.getAnswerId()) && Objects.equals(getContent(), answer.getContent()) && getIsCorrect() == answer.getIsCorrect() && Objects.equals(getReason(), answer.getReason()) && getCreationDate().equals(answer.getCreationDate()) && Objects.equals(getModificationDate(), answer.getModificationDate()) && getVersion().equals(answer.getVersion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

