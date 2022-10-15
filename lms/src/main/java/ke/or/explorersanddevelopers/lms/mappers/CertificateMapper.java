package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

@Mapper(componentModel = "spring")
@DecoratedWith(CertificateMapperDecorator.class)
public interface CertificateMapper {

    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    /**
     * This method maps a certificate entity to its equivalent certificate dto
     *
     * @param certificate - the certificate entity to be mapped
     * @return the mapped certificate dto
     */
    @Mappings(value = {
            @Mapping(target = "student", ignore = true)
    })
    CertificateDto toDto(Certificate certificate);

    /**
     * This method maps a certificate dto to its equivalent certificate entity
     *
     * @param certificateDto - the certificate dto to be mapped
     * @return the mapped certificate entity
     */
    @Mappings(value = {
            @Mapping(target = "student", ignore = true)
    })
    Certificate toEntity(CertificateDto certificateDto);
}
