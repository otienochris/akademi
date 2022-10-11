package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.exception.ResourceNotFoundException;
import ke.or.explorersanddevelopers.lms.mappers.CourseMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author oduorfrancis134@gmail.com;
 * @since Friday 07/10/2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final CourseRepository courseRepository;

    @Override
    public CourseDto createNewCourse(CourseDto courseDto) {
        log.info("Creating  a new course ");
        Course courseEntity = courseMapper.toEntity(courseDto);
        courseEntity.setVersion(null);
        Course createdCourse = courseRepository.save(courseEntity);
        log.info("Successfully created a course");
        return courseMapper.toDto(createdCourse);
    }

    @Override
    public CourseDto getCourseByCode(BigDecimal courseId) {
        log.info("Retrieving a course with the following id: " + courseId);
        Course course = courseRepository.getByCourseId(courseId)
                .orElseThrow(() -> new NoSuchRecordException("Course with id:" + courseId  + ", not found"));
        log.info("Retrieved course with id: " + courseId);
        return courseMapper.toDto(course);
    }

    @Override
    public Boolean deleteCourseByCode(BigDecimal courseId) {
        log.info("Deleting a course with  the following id: " + courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id: " + courseId + ", not found"));

        courseRepository.delete(course);
        log.info("Successfully deleted the course");
        return true;
    }

    @Override
    public List<CourseDto> getListOfCourses(Pageable pageable) {
        log.info("Retrieving a list of courses");

        List<CourseDto> listOfCourses = new ArrayList<>();
        courseRepository.findAll(pageable).forEach(course -> listOfCourses.add(courseMapper.toDto(course)));
        if (listOfCourses.size() == 0)
            log.warn("Retrieved an empty list of courses");
        else
            log.info("Successfully retrieved list of courses");

        return listOfCourses;
    }

    @Override
    public CourseDto editCourseByCode(BigDecimal courseId, CourseDto courseDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchRecordException("Course with id: " + courseId + ", not found"));

        //get course dto details to save to db
        course.setTitle(courseDto.getTitle());
        course.setVersion(courseDto.getVersion());
        course.setCategory(courseDto.getCategory());
        course.setDescription(courseDto.getDescription());
        course.setIntroductionVideoLink(courseDto.getIntroductionVideoLink());
        course.setPrice(courseDto.getPrice());
        course.setRating(courseDto.getRating());
        course.setThumbnailLink(courseDto.getThumbnailLink());

        final Course updatedCourse = courseRepository.save(course);

        return courseMapper.toDto(updatedCourse);

    }
}
