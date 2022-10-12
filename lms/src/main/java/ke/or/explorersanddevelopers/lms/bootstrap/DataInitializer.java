package ke.or.explorersanddevelopers.lms.bootstrap;

import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.repositories.InstructorRepository;
import ke.or.explorersanddevelopers.lms.repositories.RelativeRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 12/10/2022
 */

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final RelativeRepository relativeRepository;

    @Override
    public void run(String... args) throws Exception {

        if (studentRepository.count() == 0)
            studentRepository.saveAll(getAllStudents());

        if (instructorRepository.count() == 0)
            instructorRepository.saveAll(getAllInstructors());

        if (relativeRepository.count() == 0)
            relativeRepository.saveAll(getAllRelatives());

    }

    private List<Relative> getAllRelatives() {
        Relative relative1 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .password("relativePass1")
                .lastName("last1")
                .firstName("first1")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative1@gmail.com")
                .isAccountDisabled(false)
                .build();
        Relative relative2 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .password("relativePass2")
                .lastName("last2")
                .firstName("first2")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative2@gmail.com")
                .isAccountDisabled(false)
                .build();
        Relative relative3 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .password("relativePass3")
                .lastName("last3")
                .firstName("first3")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative3@gmail.com")
                .isAccountDisabled(false)
                .build();
        return new ArrayList<>(List.of(relative1, relative2, relative3));
    }

    private List<Instructor> getAllInstructors() {
        Instructor instructor1 = Instructor.builder()
                .countryCode("UG")
                .firstName("first1")
                .lastName("last1")
                .email("instructor1@gmail.com")
                .description("Very passionate about teaching.")
                .title("Mathematician")
                .expertise("Mathematics")
                .password("instructorPass1")
                .isAccountDisabled(false)
                .build();
        Instructor instructor2 = Instructor.builder()
                .countryCode("UG")
                .firstName("first2")
                .lastName("last2")
                .email("instructor2@gmail.com")
                .description("Very passionate about teaching.")
                .title("Mathematician")
                .expertise("Mathematics")
                .password("instructorPass2")
                .isAccountDisabled(false)
                .build();
        Instructor instructor3 = Instructor.builder()
                .countryCode("UG")
                .firstName("first3")
                .lastName("last3")
                .email("instructor3@gmail.com")
                .description("Very passionate about teaching.")
                .title("Mathematician")
                .expertise("Mathematics")
                .password("instructorPass3")
                .isAccountDisabled(false)
                .build();
        return new ArrayList<>(List.of(instructor1, instructor2, instructor3));
    }

    private List<Student> getAllStudents() {
        Student student1 = Student.builder()
                .password("studentPass1")
                .isAccountDisabled(false)
                .lastName("Last1")
                .email("student1@gmail.com")
                .firstName("First1")
                .countryCode("KE")
                .build();
        Student student2 = Student.builder()
                .password("pass2")
                .isAccountDisabled(false)
                .lastName("Last2")
                .email("student2@gmail.com")
                .firstName("First2")
                .countryCode("KE")
                .build();
        Student student3 = Student.builder()
                .password("pass3")
                .isAccountDisabled(false)
                .lastName("Last3")
                .email("student3@gmail.com")
                .firstName("First3")
                .countryCode("KE")
                .build();
        return new ArrayList<>(List.of(student1, student2, student3));
    }
}
