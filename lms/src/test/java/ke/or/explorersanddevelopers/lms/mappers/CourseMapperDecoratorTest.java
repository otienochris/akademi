package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
import ke.or.explorersanddevelopers.lms.mappers.decorators.CourseMapperDecorator;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CourseMapperDecoratorTest {

    private final Set<CourseEnrollment> courseEnrollmentEntities = Set.of(CourseEnrollment.builder().courseEnrollmentId(BigDecimal.ONE).build());
    private final Set<Topic> topicEntities = Set.of(Topic.builder().topicId(BigDecimal.ONE).build());
    private final Set<Review> reviews = Set.of(Review.builder().reviewId(BigDecimal.ONE).build());
    private final CourseEnrollmentDto courseEnrollmentDto = CourseEnrollmentDto.builder().courseEnrollmentId(BigDecimal.ONE).build();
    private final Set<CourseEnrollmentDto> courseEnrollmentDtos = Set.of(courseEnrollmentDto);
    private final TopicDto topicDto = TopicDto.builder().topicId(BigDecimal.ONE).build();
    private final Set<TopicDto> topicDtos = Set.of(topicDto);
    private final ReviewDto reviewDto = ReviewDto.builder().reviewId(BigDecimal.ONE).build();
    private final Set<ReviewDto> reviewDtos = Set.of(reviewDto);
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private CourseEnrollmentMapper courseEnrollmentMapper;
    @Mock
    private TopicMapper topicMapper;
    @InjectMocks
    private CourseMapperDecorator courseMapperDecorator;
    private Course courseEntity;
    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        BigDecimal courseId = BigDecimal.TEN;
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        String description = "Some Description";
        String title = "Introduction to something";
        int rating = 4;
        long version = 0L;
        Date modificationDate = Date.valueOf(now.plusDays(2));
        String introductionVideoLink = "https://sfsfsfs/csfsf";
        String thumbnailLink = "https://dfsfs/fsfsfs";
        BigDecimal price = BigDecimal.valueOf(100);
        courseEntity = Course.builder()
                .courseId(BigDecimal.ONE)
                .courseEnrollments(courseEnrollmentEntities)
                .topics(topicEntities)
                .courseId(courseId)
                .creationDate(creationDate)
                .description(description)
                .rating(rating)
                .title(title)
                .version(version)
                .price(price)
                .modificationDate(modificationDate)
                .category(CourseCategoryEnum.AGRICULTURE)
                .introductionVideoLink(introductionVideoLink)
                .reviews(reviews)
                .thumbnailLink(thumbnailLink)
                .build();

        courseDto = CourseDto.builder()
                .courseId(BigDecimal.ONE)
                .courseEnrollments(courseEnrollmentDtos)
                .topics(topicDtos)
                .courseId(courseId)
                .creationDate(creationDate)
                .description(description)
                .rating(rating)
                .title(title)
                .version(version)
                .price(price)
                .modificationDate(modificationDate)
                .category(CourseCategoryEnum.AGRICULTURE)
                .introductionVideoLink(introductionVideoLink)
                .reviews(reviewDtos)
                .thumbnailLink(thumbnailLink)
                .build();
    }

    @Test
    void toDto() {
        given(courseMapper.toDto(any())).willReturn(courseDto);
        given(reviewMapper.toDto(any())).willReturn(reviewDto);
        given(courseEnrollmentMapper.toDto(any())).willReturn(courseEnrollmentDto);
        given(topicMapper.toDto(any())).willReturn(topicDto);

        CourseDto actualResponse = courseMapperDecorator.toDto(courseEntity);

        assertThat(actualResponse).isEqualTo(courseDto);
    }
}