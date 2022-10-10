package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    /**
     * This method maps an address entity to its equivalent address dto
     *
     * @param address - the address entity to be mapped
     * @return the mapped address dto
     */
    AddressDto toDto(Address address);

    /**
     * This method maps an address dto to its equivalent address entity
     *
     * @param addressDto - the address dto to be mapped
     * @return the mapped address entity
     */
    Address toEntity(AddressDto addressDto);
}
