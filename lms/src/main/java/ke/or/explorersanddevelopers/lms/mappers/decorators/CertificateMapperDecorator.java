package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.CertificateMapper;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
public class CertificateMapperDecorator implements CertificateMapper {

    @Autowired
    @Qualifier("delegate")
    private CertificateMapper certificateMapper;

    @Autowired
    @Qualifier("delegate")
    private StudentMapper studentMapper;

    @Override
    public CertificateDto toDto(Certificate certificate) {
        CertificateDto mappedCertificateDto = certificateMapper.toDto(certificate);

        Student student = certificate.getStudent();
        if (student != null) {
            StudentDto studentDto = studentMapper.toDto(student);
            mappedCertificateDto.setStudent(studentDto);
        }
        return mappedCertificateDto;
    }

    @Override
    public Certificate toEntity(CertificateDto certificateDto) {
        Certificate mappedCertificate = certificateMapper.toEntity(certificateDto);

        StudentDto studentDto = certificateDto.getStudent();
        if (studentDto != null) {
            Student student = studentMapper.toEntity(studentDto);
            mappedCertificate.setStudent(student);
        }

        return mappedCertificate;
    }
}
