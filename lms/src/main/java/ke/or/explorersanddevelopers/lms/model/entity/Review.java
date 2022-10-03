package ke.or.explorersanddevelopers.lms.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 03/10/2022
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "REVIEWS")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID")
    private UUID reviewId;
}
