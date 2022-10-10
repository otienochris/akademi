package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.RelativeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 09/10/2022
 */
@Transactional
public interface RelativeService {

    /**
     * This method saves a new relative
     *
     * @param relativeDto - the new relative to be saved
     * @return the saved relative
     */
    RelativeDto saveNewRelative(RelativeDto relativeDto);

    /**
     * This method retrieves a relative by id
     *
     * @param relativeId - the id of the student to be retrieved
     * @return the retrieved relative
     */
    RelativeDto getRelativeById(BigDecimal relativeId);

    /**
     * This method retrieves a list of relatives
     *
     * @param pageable - the paging object with details like page number and page size
     * @return a list of relatives
     */
    List<RelativeDto> getListOfRelatives(Pageable pageable);

    /**
     * This method allows a relative to track a student
     *
     * @param relativeId   - the id of the relative that aims at tracking a student
     * @param studentToken - a token for the student the relative intends to track
     */
    RelativeDto trackStudent(BigDecimal relativeId, String studentToken);

    /**
     * This method deletes a relative by id
     *
     * @param relativeId - the id of the relative to be deleted
     * @return true if operation was successful
     */
    Boolean deleteRelativeById(BigDecimal relativeId);
}
