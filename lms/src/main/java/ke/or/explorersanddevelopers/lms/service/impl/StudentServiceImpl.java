package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.*;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.repositories.*;
import ke.or.explorersanddevelopers.lms.service.StudentService;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    public static String EMAIL_VERIFICATION_URL = "http://localhost:3000/verify-email/";
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentMapper courseEnrollmentMapper;
    private final TestRepository testRepository;
    private final TestEnrollmentMapper testEnrollmentMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CertificateMapper certificateMapper;
    private final InstructorRepository instructorRepository;
    private final TopicRepository topicRepository;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String NOTIFICATION_ENDPOINT = "http://localhost:8081/notification-service/mail/send-simple-message";

    @Override
    public StudentDto saveNewStudent(StudentDto studentDto) {
        log.info("Saving a new student");
        Student studentEntity = studentMapper.toEntity(studentDto);
        studentEntity.setVersion(null);

        String username = studentDto.getEmail();

        log.info("Saving app user details associated with the new student");
        String emailVerificationCode = UUID.randomUUID().toString();
        AppUser savedAppUser = appUserRepository.save(AppUser.builder()
                .emailVerificationCode(emailVerificationCode)
                .password(passwordEncoder.encode(studentDto.getNewPassword()))
                .username(username)
                .isAccountDisabled(true)
                .build());

        log.info("Adding role to student");
        userService.addRoleToUser(username, RolesEnum.ROLE_STUDENT.name());

        log.info("Associating the app user details with the new student details");
        studentEntity.setAppUser(savedAppUser);
        Student savedStudent = studentRepository.save(studentEntity);

        sendEmailVerificationCode(username, emailVerificationCode,studentDto.getLastName());

        log.info("Student saved successfully");
        return studentMapper.toDto(savedStudent);
    }

    public static boolean sendEmailVerificationCode(String email, String emailVerificationCode, String lastName) {
        //TODO send email verification code
        // https://www.springcloud.io/post/2022-03/resttemplate-multipart/#gsc.tab=0
        log.info("Sending email verification code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("to", email);
        multipartBodyBuilder.part("subject", "EMAIL VERIFICATION CODE");
        String template = "<section style=\"background-color: #33475b; color: white; margin: 0; padding: 20px; min-height: 200px;\">\n" +
                "        <h2>Dear "+lastName+",</h2>\n" +
                "        <p style=\"margin-bottom: 50px; color: white; font-weight: bold;\">Welcome to Akademi. Click the following button to verify your email:</p>\n" +
                "        <div style=\"margin: 10px 2px;\">\n" +
                "            <a href=\"" +
                "" + EMAIL_VERIFICATION_URL + emailVerificationCode +
                "\" style=\"text-decoration: none; color: black; font-weight: bolder; background-color: orange; padding: 10px; margin: 2px; border-radius: 5px;\">verify email</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div></div>\n" +
                "    </section>";
//        String link  = "<a href=\"" + EMAIL_VERIFICATION_URL + emailVerificationCode +"\">Verify email</a>";
        multipartBodyBuilder.part("text", template);

        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> httpEntity = new HttpEntity<>(multipartBody, headers);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(NOTIFICATION_ENDPOINT, httpEntity, Object.class);
        int status = responseEntity.getStatusCodeValue();
        Object body = responseEntity.getBody();
        if (status >= 200 && status < 300) {
            log.info("Successfully sent the email verification code");
            return true;
        } else {
            log.error("Error sending email verification code");
            return false;
        }
    }

    @Override
    public StudentDto getStudentByCode(BigDecimal studentId) {
        log.info("Retrieving a student of id: " + studentId);
        Student student = getStudentByCodeFromDb(studentId);
        log.info("Successfully retrieved a student of id: " + studentId);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto updateStudent(BigDecimal studentId, StudentDto studentDto) {
        log.info("Updating a student of id: " + studentId);
        Student student = getStudentByCodeFromDb(studentId);

        student.setEmail(studentDto.getEmail());
        student.setCountryCode(studentDto.getCountryCode());
        student.setFirstName(student.getFirstName());
        student.setLastName(student.getLastName());

        Student savedStudent = studentRepository.save(student);

        log.info("Updated a student of id: " + studentId);
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public Boolean deleteStudentByCode(BigDecimal studentId) {
        log.info("Deleting a student of student id: " + studentId);
        Student student = getStudentByCodeFromDb(studentId);
        studentRepository.delete(student);
        log.info("Successfully Deleted a student of student id: " + studentId);
        return true;
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {

        CourseEnrollment courseEnrollment;
        Student student = getStudentByCodeFromDb(studentId);
        Course course = getCourseByCodeFromDb(courseId);

        // check if already enrolled for the course
        Boolean existsByCourseAndStudent = courseEnrollmentRepository.existsByCourseAndStudent(course, student);
        if (existsByCourseAndStudent) {
            String message = "Student of id: " + studentId + " is already enrolled on course of id: " + courseId;
            log.error(message);
            throw new DuplicateRecordException(message);
        } else {
            courseEnrollment = CourseEnrollment.builder()
                    .course(course)
                    .testEnrollments(new ArrayList<>())
                    .student(student)
                    .status(StatusEnum.PENDING)
                    .completedTopics(new ArrayList<>())
                    .build();
            courseEnrollmentRepository.save(courseEnrollment);
        }

        return courseEnrollmentMapper.toDto(courseEnrollment);
    }

    @Override
    public TestEnrollmentDto enrollStudentToTest(BigDecimal studentId, BigDecimal courseId, BigDecimal testId) {

        Student student = getStudentByCodeFromDb(studentId);
        Course course = getCourseByCodeFromDb(courseId);

        CourseEnrollment courseEnrollment = courseEnrollmentRepository.getByCourseAndStudent(course, student).orElseThrow(() -> {
            String message = "The student [" + student + "] has not enrolled for course [" + courseId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });

        List<TestEnrollment> oldTestEnrollments = courseEnrollment.getTestEnrollments();
        if (oldTestEnrollments != null && oldTestEnrollments.size() > 0) {
            oldTestEnrollments.forEach(testEnrollment -> {
                Test test = testEnrollment.getTest();
                if (test != null && Objects.equals(test.getTestId(), testId)) {
                    String message = "The student [" + student + "] has already enrolled for the test [" + courseId + "]";
                    log.error(message);
                    throw new DuplicateRecordException(message);
                }
            });
        }

        Test test = getTestByCodeFromDb(testId);
        TestEnrollment newTestEnrollment = TestEnrollment.builder()
                .amount(BigDecimal.ZERO)
                .test(test)
                .score(0.0)
                .status(StatusEnum.PENDING)
                .build();

        if (oldTestEnrollments == null || oldTestEnrollments.size() == 0) {
            oldTestEnrollments = new ArrayList<>();
        }
        oldTestEnrollments.add(newTestEnrollment);
        courseEnrollment.setTestEnrollments(oldTestEnrollments);


        return testEnrollmentMapper.toDto(newTestEnrollment);
    }

    @Override
    public List<StudentDto> getListOfStudents(Pageable pageable) {
        log.info("Retrieving a list of students");
        List<StudentDto> response = new ArrayList<>();
        studentRepository.findAll(pageable).forEach(student -> response.add(studentMapper.toDto(student)));
        if (response.size() == 0)
            log.warn("Retrieve an empty list of students");
        else
            log.info("Successfully retrieved a list of students");
        return response;
    }

    @Override
    public Boolean submitReview(BigDecimal studentId, BigDecimal targetID, ReviewDto reviewDto) {
        Student studentByCodeFromDb = getStudentByCodeFromDb(studentId);

        Review mappedReview = reviewMapper.toEntity(reviewDto);

        switch (reviewDto.getType()) {
            case USER:
                instructorRepository.getByInstructorId(targetID)
                        .ifPresentOrElse(
                                (instructor) -> {
                                    List<Review> reviews = instructor.getReviews();
                                    Review savedReview = reviewRepository.save(mappedReview);
                                    if (reviews == null) {
                                        instructor.setReviews(new ArrayList<>());
                                        instructor.getReviews().add(savedReview);
                                    } else {
                                        instructor.getReviews().add(savedReview);
                                    }

                                    instructorRepository.save(instructor); // update changes
                                },
                                () -> {
                                    String message = "We could not find an instructor with the provided id [" + targetID + "]";
                                    log.error(message);
                                    throw new NoSuchRecordException(message);
                                });

                break;
            case COURSE:
                Course course = courseRepository.getByCourseId(targetID).orElseThrow(() -> {
                    String message = "We could not find a course with id: " + targetID;
                    log.error(message);
                    throw new NoSuchRecordException(message);
                });
                Review savedReview = reviewRepository.save(mappedReview);
                List<Review> reviews = course.getReviews();
                if (reviews == null) {
                    course.setReviews(new ArrayList<>());
                    course.getReviews().add(savedReview);
                } else {
                    course.getReviews().add(savedReview);
                }
                courseRepository.save(course); // update changes
                break;
            case APPLICATION:
                //TODO
                break;
            default:
        }

        Review savedReview = reviewRepository.save(mappedReview);
        List<Review> reviews = studentByCodeFromDb.getReviews();
        if (reviews != null) {
            studentByCodeFromDb.getReviews().add(savedReview);
        } else {
            studentByCodeFromDb.setReviews(new ArrayList<>());
            studentByCodeFromDb.getReviews().add(savedReview);
        }

        studentRepository.save(studentByCodeFromDb);
        return true;
    }

    @Override
    public AddressDto addAddress(BigDecimal studentId, AddressDto addressDto) {

        Student student = getStudentByCodeFromDb(studentId);

        List<Address> addresses = student.getAddresses();
        // map the address from dto to entity
        Address mappedAddress = addressMapper.toEntity(addressDto);

        // save the address first
        Address savedAddress = addressRepository.save(mappedAddress);

        // set eh address
        if (addresses == null) {
            student.setAddresses(new ArrayList<>());
            student.getAddresses().add(savedAddress);
        } else {
            student.getAddresses().add(savedAddress);
        }

        // save changes
        studentRepository.save(student);
        return addressMapper.toDto(savedAddress);
    }

    @Override
    public List<CertificateDto> retrieveCertificates(BigDecimal studentId) {
        Student studentByCodeFromDb = getStudentByCodeFromDb(studentId);
        List<CertificateDto> response = new ArrayList<>();
        studentByCodeFromDb.getCertificates().forEach(certificate -> response.add(certificateMapper.toDto(certificate)));
        return response;
    }

    @Override
    public UUID generateToken(BigDecimal studentId) {
        Student student = getStudentByCodeFromDb(studentId);
        AppUser appUser = student.getAppUser();
        UUID token = null;
        if (appUser != null) {
            token = UUID.randomUUID();
            appUser.setToken(token.toString());
            appUserRepository.save(appUser);
        }
        return token;
    }

    private static List<StatusEnum> getTestStatuses(CourseEnrollment oldCourseEnrollment) {
        return oldCourseEnrollment.getTestEnrollments().stream().flatMap(testEnrollment -> Stream.of(testEnrollment.getStatus())).collect(Collectors.toList());
    }

    @Override
    public CourseEnrollmentDto completeTopic(BigDecimal studentId, BigDecimal courseId, BigDecimal topicId) {

        CourseEnrollment currentCourseEnrollment = null;
        Topic topic = getTopicByCodeFromDb(topicId);
        Student student = getStudentByCodeFromDb(studentId);
        Course course = getCourseByCodeFromDb(courseId);
        CourseEnrollment oldCourseEnrollment = getCourseEnrollmentByStudentAndCourseFromDb(student, course);


        if (oldCourseEnrollment.getCompletedTopics() == null) {
            oldCourseEnrollment.setCompletedTopics(new ArrayList<>());
        }

        if (oldCourseEnrollment.getCompletedTopics() != null && oldCourseEnrollment.getCompletedTopics().size() == 0) {

            // add the new completed topic to the empty list
            oldCourseEnrollment.getCompletedTopics().add(topic);

            // check if that was the last topic and test are done
            List<StatusEnum> testStatuses = getTestStatuses(oldCourseEnrollment);
            List<Topic> topics = course.getTopics();
            if (topics != null && topics.size() == oldCourseEnrollment.getCompletedTopics().size() && !testStatuses.contains(StatusEnum.PENDING)) {
                oldCourseEnrollment.setStatus(StatusEnum.COMPLETE);
            }

            // save changes
            currentCourseEnrollment = courseEnrollmentRepository.save(oldCourseEnrollment);

        } else if (oldCourseEnrollment.getCompletedTopics() != null) {

            // check if topic is already in the list of completed topics
            boolean[] topicIsPresent = new boolean[]{false};
            oldCourseEnrollment.getCompletedTopics().parallelStream().forEach(topic1 -> {
                if (Objects.equals(topic1.getTopicId(), topicId)) {
                    topicIsPresent[0] = true;
                }
            });

            if (topicIsPresent[0]) {
                return courseEnrollmentMapper.toDto(oldCourseEnrollment);
            } else {
                oldCourseEnrollment.getCompletedTopics().add(topic); // add the new completed topic

                // check if that was the last topic and tests are all done
                List<StatusEnum> testStatuses = getTestStatuses(oldCourseEnrollment);
                List<Topic> topics = course.getTopics();
                if (topics != null && topics.size() == oldCourseEnrollment.getCompletedTopics().size() && testStatuses.contains(StatusEnum.PENDING)) {
                    oldCourseEnrollment.setStatus(StatusEnum.COMPLETE);
                }

                // save changes
                currentCourseEnrollment = courseEnrollmentRepository.save(oldCourseEnrollment);
            }
        }
        return courseEnrollmentMapper.toDto(currentCourseEnrollment);
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        log.info("Retrieving a student with email: " + email);
        Student student = getStudentByEmailFromDb(email);
        log.info("Successfully retrieved a student with email: " + email);
        return studentMapper.toDto(student);
    }

    private Student getStudentByEmailFromDb(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() -> {
            throw new NoSuchRecordException("Student not found");
        });
    }

    private CourseEnrollment getCourseEnrollmentByStudentAndCourseFromDb(Student student, Course course) {
        return courseEnrollmentRepository.getByCourseAndStudent(course, student).orElseThrow(() -> {
            String message = "The student of id: " + student.getStudentId() + " is not enrolled in the course of id: " + course.getCourseId();
            throw new NoSuchRecordException(message);
        });
    }

    private Topic getTopicByCodeFromDb(BigDecimal topicId) {
        return topicRepository.getByTopicId(topicId).orElseThrow(() -> {
            String message = "We could not find a topic of id: [" + topicId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }

    private Student getStudentByCodeFromDb(BigDecimal studentId) {
        return studentRepository.getByStudentId(studentId).orElseThrow(() -> {
            String message = "Student of id " + studentId + " could not be found.";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }

    private Course getCourseByCodeFromDb(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> {
            String message = "A course with id: " + courseId + " could no be found";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }

    private Test getTestByCodeFromDb(BigDecimal testId) {
        return testRepository.getByTestId(testId).orElseThrow(() -> {
            String message = "A test of id: " + testId + " could not be found! ";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }
}
