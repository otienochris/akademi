package ke.or.explorersanddevelopers.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    private final StringBuilder baseUrl = new StringBuilder("/students");
    private final boolean isAccountDisabled = false;
    private final Long version = 0L;
    private final BigDecimal studentId = BigDecimal.ONE;
    private final String email = "abc@xyz.com";
    private final String lastName = "Otieno";
    private final String firstName = "Christopher";
    private final String countryCode = "KE";
    private final String date = "2022-10-01";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    private StudentDto requestBody;
    private StudentDto responseBody;

    @BeforeEach
    void setUp() {

        requestBody = StudentDto.builder()
                .studentId(null)
                .isAccountDisabled(isAccountDisabled)
                .version(version)
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .creationDate(null)
                .modificationDate(null)
                .countryCode(countryCode)
                .addresses(null)
                .reviews(null)
                .certificates(null)
                .build();

        responseBody = StudentDto.builder()
                .studentId(studentId)
                .isAccountDisabled(isAccountDisabled)
                .version(version)
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .countryCode(countryCode)
                .creationDate(Date.valueOf(LocalDate.parse(date).plusDays(1))) // date is off by 1 day
                .addresses(new HashSet<>())
                .reviews(new HashSet<>())
                .certificates(new HashSet<>())
                .build();
    }

    @Test
    void saveNewStudent() throws Exception {

        given(studentService.saveNewStudent(ArgumentMatchers.any())).willReturn(responseBody);

        mockMvc.perform(post(baseUrl.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(jsonPath("$.studentId", is(studentId.intValue())))
                .andExpect(jsonPath("$.accountDisabled", is(isAccountDisabled)))
                .andExpect(jsonPath("$.version", is(version.intValue())))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.lastName", is(lastName)))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.countryCode", is(countryCode)))
                .andExpect(jsonPath("$.creationDate", is(date)))
                .andExpect(jsonPath("$._links.self.href", endsWith(studentId.toPlainString())))
                .andExpect(jsonPath("$._links.collection.href", containsString("pageNo")))
                .andExpect(jsonPath("$._links.collection.href", containsString("pageSize")))
                .andExpect(status().isCreated());
    }

    @Test
    void getStudentById() throws Exception {
        baseUrl.append("/{studentId}");
        given(studentService.getStudentByCode(ArgumentMatchers.any())).willReturn(responseBody);

        mockMvc.perform(get(baseUrl.toString(), studentId))
                .andExpect(jsonPath("$.studentId", is(studentId.intValue())))
                .andExpect(jsonPath("$.accountDisabled", is(isAccountDisabled)))
                .andExpect(jsonPath("$.version", is(version.intValue())))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.lastName", is(lastName)))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.countryCode", is(countryCode)))
                .andExpect(jsonPath("$.creationDate", is(date)))
                .andExpect(jsonPath("$._links.self.href", endsWith(studentId.toPlainString())))
                .andExpect(jsonPath("$._links.collection.href", containsString("pageNo")))
                .andExpect(jsonPath("$._links.collection.href", containsString("pageSize")))
                .andExpect(status().isOk());
    }

    @Test
    void getListOfStudents() throws Exception {
        given(studentService.getListOfStudents(ArgumentMatchers.any(Pageable.class))).willReturn(Set.of(responseBody));

        mockMvc.perform(get(baseUrl.toString()))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].studentId", is(studentId.intValue())))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].accountDisabled", is(isAccountDisabled)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].version", is(version.intValue())))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].email", is(email)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].lastName", is(lastName)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].firstName", is(firstName)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].countryCode", is(countryCode)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0].creationDate", is(date)))
                .andExpect(jsonPath("$._embedded.studentDtoList[0]._links.self.href", endsWith(studentId.toPlainString())))
                .andExpect(jsonPath("$._embedded.studentDtoList[0]._links.collection.href", containsString("pageNo")))
                .andExpect(jsonPath("$._embedded.studentDtoList[0]._links.collection.href", containsString("pageSize")))
                .andExpect(status().isOk());
    }
}