package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.OrganizationDto;
import ke.or.explorersanddevelopers.lms.model.entity.Organization;
import org.mapstruct.Mapper;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    /**
     * This method maps an organization entity to its equivalent organization dto
     *
     * @param organization - the organization entity to be mapped
     * @return the mapped dto
     */
    OrganizationDto toDto(Organization organization);

    /**
     * This method maps an organization dto to its equivalent organization entity
     *
     * @param organizationDto - the organization dto to be mapped
     * @return the mapped entity
     */
    Organization toEntity(OrganizationDto organizationDto);
}
