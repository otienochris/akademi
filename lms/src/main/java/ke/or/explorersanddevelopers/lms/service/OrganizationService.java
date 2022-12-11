package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.OrganizationDto;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
public interface OrganizationService {

    /**
     * This method saves a new organization record
     *
     * @param organizationDto - the organization to be saved
     * @return the saved organization record
     */
    OrganizationDto saveNewOrganization(OrganizationDto organizationDto);
}
