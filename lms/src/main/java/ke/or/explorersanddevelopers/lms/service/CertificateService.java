package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class provides methods to manage certificates
 *
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
public interface CertificateService {

    /**
     * This method retrieves a certificate by id
     *
     * @param certificateId - the id of the certificate to be retrieved
     * @return the retrieved certificate
     */
    CertificateDto getCertificateById(BigDecimal certificateId);

    /**
     * This method returns a list of certificates
     *
     * @param pageable - the paging object with details like page number and page size
     * @return a list of certificates
     */
    List<CertificateDto> getListOfCertificates(Pageable pageable);
}
