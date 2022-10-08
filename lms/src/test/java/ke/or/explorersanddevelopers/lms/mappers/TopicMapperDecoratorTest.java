package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TopicMapperDecoratorTest {

    private final SubTopic subtopicEntity = SubTopic.builder().subTopicId(BigDecimal.ONE).build();
    private final SubTopicDto subtopicDto = SubTopicDto.builder().subTopicId(BigDecimal.ONE).build();
    @Mock
    private TopicMapper topicMapper;
    @Mock
    private SubTopicMapper subTopicMapper;
    @InjectMocks
    private TopicMapperDecorator topicMapperDecorator;
    private Topic topicEntity;
    private TopicDto topicDto;
    private TopicDto partialTopicDto;

    @BeforeEach
    void setUp() {
        Long version = 0L;
        String content = "Some content";
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        String description = "some description";
        String link = "https://xyz/sdfs";
        Date modificationDate = Date.valueOf(now.plusDays(2));
        String title = "Some Title";
        BigDecimal topicId = BigDecimal.ONE;
        topicEntity = Topic.builder()
                .topicId(topicId)
                .subTopics(List.of(subtopicEntity))
                .version(version)
                .content(content)
                .creationDate(creationDate)
                .description(description)
                .link(link)
                .modificationDate(modificationDate)
                .title(title)
                .build();

        partialTopicDto = TopicDto.builder()
                .topicId(topicId)
                .subTopics(null)
                .version(version)
                .content(content)
                .creationDate(creationDate)
                .description(description)
                .link(link)
                .modificationDate(modificationDate)
                .title(title)
                .build();
        topicDto = TopicDto.builder()
                .topicId(topicId)
                .subTopics(List.of(subtopicDto))
                .version(version)
                .content(content)
                .creationDate(creationDate)
                .description(description)
                .link(link)
                .modificationDate(modificationDate)
                .title(title)
                .build();
    }

    @Test
    void toDto() {
        given(topicMapper.toDto(any())).willReturn(partialTopicDto);
        given(subTopicMapper.toDto(any())).willReturn(subtopicDto);

        TopicDto actualResponse = topicMapperDecorator.toDto(topicEntity);

        assertThat(actualResponse).isEqualTo(topicDto);
    }
}