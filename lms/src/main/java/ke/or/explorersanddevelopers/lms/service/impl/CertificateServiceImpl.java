package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CertificateMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import ke.or.explorersanddevelopers.lms.repositories.CertificateRepository;
import ke.or.explorersanddevelopers.lms.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateRepository certificateRepository;

    @Override
    public CertificateDto getCertificateById(BigDecimal certificateId) {
        log.info("Retrieving a certificate by id: [" + certificateId + "]");
        Certificate certificateByIdFromDb = getCertificateByIdFromDb(certificateId);
        log.info("Successfully retrieved a certificate by id: [" + certificateId + "]");
        return certificateMapper.toDto(certificateByIdFromDb);
    }

    @Override
    public List<CertificateDto> getListOfCertificates(Pageable pageable) {
        log.info("Retrieving a list of certificates: Page number: " + pageable.getPageNumber() + "; Page size: " + pageable.getPageSize() + ";");
        List<CertificateDto> response = new ArrayList<>();
        certificateRepository.findAll(pageable).forEach(certificate -> response.add(certificateMapper.toDto(certificate)));
        if (response.size() > 0)
            log.info("Successfully retrieved a list of certificates: Page number: " + pageable.getPageNumber() + "; Page size: " + pageable.getPageSize() + ";");
        else
            log.warn("Retrieved an empty list of certificates");
        return response;
    }

    private Certificate getCertificateByIdFromDb(BigDecimal certificateId) {
        return certificateRepository.getByCertificateId(certificateId).orElseThrow(() -> {
            String message = "We could not find a certificate by the provided id [" + certificateId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }
}
