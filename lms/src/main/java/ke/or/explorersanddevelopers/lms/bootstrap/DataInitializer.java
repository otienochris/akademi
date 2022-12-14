package ke.or.explorersanddevelopers.lms.bootstrap;

import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.InstructorRepository;
import ke.or.explorersanddevelopers.lms.repositories.RelativeRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Instructor> instructors = new ArrayList<>();
        if (studentRepository.count() == 0)
            studentRepository.saveAll(getAllStudents());

        if (instructorRepository.count() == 0) {
            instructors = instructorRepository.saveAll(getAllInstructors());
        }

        if (relativeRepository.count() == 0)
            relativeRepository.saveAll(getAllRelatives());

        if (courseRepository.count() == 0 && instructors.size() > 0) {
            Instructor instructor = instructors.get(0); // instructor saving the course
            Course course = Course.builder()
                    .thumbnailLink("https://p0.piqsels.com/preview/724/71/644/flowwer-white-blur-leaves.jpg")
                    .price(BigDecimal.valueOf(10))
                    .category(CourseCategoryEnum.AGRICULTURE)
                    .title("Introduction to some useless course")
                    .introductionVideoLink("https://youtu.be/Y2rdmKtVss0")
                    .description("There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words e")
                    .instructors(new ArrayList<>(List.of(instructor)))
                    .build();
            Course savedCourse = courseRepository.save(course);

            if (instructor.getCourses() == null)
                instructor.setCourses(new ArrayList<>());
            instructor.getCourses().add(savedCourse);
            instructorRepository.save(instructor);
        }


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
