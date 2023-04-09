package ke.or.explorersanddevelopers.lms.bootstrap;

import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.model.security.Role;
import ke.or.explorersanddevelopers.lms.repositories.*;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 12/10/2022
 */

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    public static final String ROLE_USER = "ROLE_USER", ROLE_MANAGER = "ROLE_MANAGER", ROLE_ADMIN = "ROLE_ADMIN", ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN", ROLE_STUDENT = "ROLE_STUDENT";
    public static final String ROLE_INSTRUCTOR = "ROLE_INSTRUCTOR";
    public static final String ROLE_RELATIVE = "ROLE_RELATIVE";
    public static final String PASSWORD = "admin1234";
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final RelativeRepository relativeRepository;
    private final CourseRepository courseRepository;

    private final UserService userService;

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    String email;

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0)
            roleRepository.saveAll(getRoles());

        if (userRepository.count() == 0) {
            userRepository.save(AppUser.builder()
                    .username(email)
                    .password(passwordEncoder.encode(PASSWORD))
                    .isAccountDisabled(false)
                    .build());
            userService.addRoleToUser(email, RolesEnum.ROLE_SUPER_ADMIN.name());
        }

        initUsers();

    }

    private void initUsers() {
        List<Instructor> instructors = new ArrayList<>();

        // save student and user
        if (studentRepository.count() == 0) {
            studentRepository.saveAll(getAllStudents()).forEach(student -> {
                String studentEmail = student.getEmail();
                log.info("Saving user account for student whose email is {}", studentEmail);
                AppUser appUser = userService.saveUser(getAppUser(studentEmail));
                if (appUser != null) {
                    userService.addRoleToUser(studentEmail, ROLE_STUDENT);
                }
                // update the student
                appUser = getUserByUsername(studentEmail);
                student.setAppUser(appUser);
                studentRepository.save(student);
            });
        }

        if (instructorRepository.count() == 0) {
            instructors = instructorRepository.saveAll(getAllInstructors());
            instructors.forEach(instructor -> {
                String instructorEmail = instructor.getEmail();
                AppUser appUser = userService.saveUser(getAppUser(instructorEmail));
                if (appUser != null) {
                    userService.addRoleToUser(instructorEmail, ROLE_INSTRUCTOR);
                }

                // update the instructor
                appUser = getUserByUsername(instructorEmail);
                instructor.setAppUser(appUser);
                instructorRepository.save(instructor);
            });
        }

        if (relativeRepository.count() == 0) {

            relativeRepository.saveAll(getAllRelatives()).forEach(relative -> {

                String relativeEmail = relative.getEmail();
                AppUser appUser = userService.saveUser(getAppUser(relativeEmail));
                if (appUser != null) {
                    userService.addRoleToUser(relativeEmail, ROLE_RELATIVE);
                }
                // update the relative
                appUser = getUserByUsername(relativeEmail);
                relative.setAppUser(appUser);
                relativeRepository.save(relative);
            });
        }

        if (courseRepository.count() == 0 && !instructors.isEmpty()) {
            Instructor instructor = instructors.get(0); // instructor saving the course
            Course course = Course.builder()
                    .thumbnailLink("https://p0.piqsels.com/preview/724/71/644/flowwer-white-blur-leaves.jpg")
                    .price(BigDecimal.valueOf(10))
                    .category(CourseCategoryEnum.AGRICULTURE)
                    .title("Introduction to some useless course")
                    .introductionVideoLink("https://youtu.be/Y2rdmKtVss0")
                    .description("There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words e")
                    .instructors(new HashSet<>(List.of(instructor)))
                    .build();
            Course savedCourse = courseRepository.save(course);

            if (instructor.getCourses() == null)
                instructor.setCourses(new HashSet<>());
            instructor.getCourses().add(savedCourse);

            instructorRepository.save(instructor);
        }
    }

    private AppUser getUserByUsername(String studentEmail) {
        return userRepository.findByUsername(studentEmail).orElseThrow(() -> {
            throw new NoSuchRecordException("User could not be found with the provided email " + studentEmail);
        });
    }

    private AppUser getAppUser(String user) {
        return AppUser.builder()
                .id(null)
                .isAccountDisabled(true)
                .emailVerificationCode(UUID.randomUUID().toString())
                .password(passwordEncoder.encode("pass"))
                .username(user)
                .build();
    }

    private List<Role> getRoles() {
        return Arrays.asList(
                Role.builder().name(ROLE_USER).build(),
//                Role.builder().name().build(),
                Role.builder().name(ROLE_ADMIN).build(),
                Role.builder().name(ROLE_STUDENT).build(),
                Role.builder().name(ROLE_INSTRUCTOR).build(),
                Role.builder().name(ROLE_RELATIVE).build(),
                Role.builder().name(ROLE_SUPER_ADMIN).build()
        );
    }

    private List<Relative> getAllRelatives() {
        Relative relative1 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .lastName("last1")
                .firstName("first1")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative1@gmail.com")
                .build();
        Relative relative2 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .lastName("last2")
                .firstName("first2")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative2@gmail.com")
                .build();
        Relative relative3 = Relative.builder()
                .countryCode("TZ")
                .relativeType(RelativeTypeEnum.PARENT)
                .lastName("last3")
                .firstName("first3")
                .role(RelativeRoleEnum.PASSIVE)
                .email("relative3@gmail.com")
                .build();
        return new ArrayList<>(List.of(relative1, relative2, relative3));
    }

    private List<Instructor> getAllInstructors() {
        String description = "Very passionate about teaching.";
        String title = "Mathematician";
        String expertise = "Mathematics";
        Instructor instructor1 = Instructor.builder()
                .countryCode("UG")
                .firstName("first1")
                .lastName("last1")
                .email("instructor1@gmail.com")
                .description(description)
                .title(title)
                .expertise(expertise)
                .build();
        Instructor instructor2 = Instructor.builder()
                .countryCode("UG")
                .firstName("first2")
                .lastName("last2")
                .email("instructor2@gmail.com")
                .description(description)
                .title(title)
                .expertise(expertise)
                .build();
        Instructor instructor3 = Instructor.builder()
                .countryCode("UG")
                .firstName("first3")
                .lastName("last3")
                .email("instructor3@gmail.com")
                .description(description)
                .title(title)
                .expertise(expertise)
                .build();
        return new ArrayList<>(List.of(instructor1, instructor2, instructor3));
    }

    private List<Student> getAllStudents() {
        Student student1 = Student.builder()
                .lastName("Last1")
                .email("student1@gmail.com")
                .firstName("First1")
                .countryCode("KE")
                .build();
        Student student2 = Student.builder()
                .lastName("Last2")
                .email("student2@gmail.com")
                .firstName("First2")
                .countryCode("KE")
                .build();
        Student student3 = Student.builder()
                .lastName("Last3")
                .email("student3@gmail.com")
                .firstName("First3")
                .countryCode("KE")
                .build();
        return new ArrayList<>(List.of(student1, student2, student3));
    }
}
