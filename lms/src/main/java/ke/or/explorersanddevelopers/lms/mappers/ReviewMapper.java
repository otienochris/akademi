package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    /**
     * This method maps a review entity to its equivalent review dto
     *
     * @param review - the review entity to be mapped
     * @return the mapped review dto
     */
    ReviewDto toDto(Review review);

    /**
     * This method maps a review dto to its equivalent review entity
     *
     * @param reviewDto - the review dto to be mapped
     * @return the mapped review entity
     */
    Review toEntity(ReviewDto reviewDto);
}
