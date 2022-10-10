package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mapper.InstructorMapper;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.repositories.InstructorRepository;
import ke.or.explorersanddevelopers.lms.service.InstructorService;
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
 * @since Monday, 10/10/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    @Override
    public InstructorDto saveNewInstructor(InstructorDto instructorDto) {
        log.info("Saving a new instructor");
        Instructor mappedInstructor = instructorMapper.toEntity(instructorDto);
        Instructor savedInstructor = instructorRepository.save(mappedInstructor);
        log.info("Saved a new instructor");
        return instructorMapper.toDto(savedInstructor);
    }

    @Override
    public InstructorDto getInstructorById(BigDecimal instructorId) {
        Instructor instructorByIdFromDb = getInstructorByIdFromDb(instructorId);
        log.info("Successfully retrieved an instructor");
        return instructorMapper.toDto(instructorByIdFromDb);
    }

    @Override
    public List<InstructorDto> getListOfInstructors(Pageable pageable) {
        log.info("Retrieving a list of instructors");
        List<InstructorDto> response = new ArrayList<>();
        instructorRepository.findAll(pageable).forEach(instructor -> response.add(instructorMapper.toDto(instructor)));
        if (response.size() == 0)
            log.warn("Retrieved an empty list of instructors");
        else
            log.info("Successfully retrieved a list of instructors");
        return response;
    }

    @Override
    public Boolean deleteInstructorById(BigDecimal studentId) {
        log.info("Deleting a user of id: " + studentId);
        Instructor instructorByIdFromDb = getInstructorByIdFromDb(studentId);
        instructorRepository.delete(instructorByIdFromDb);
        log.info("Successfully deleted an instructor");
        return true;
    }

    private Instructor getInstructorByIdFromDb(BigDecimal instructorId) {
        log.info("Retrieving an instructor by id [" + instructorId + "]");
        return instructorRepository.getByInstructorId(instructorId).orElseThrow(() -> {
            String message = "We could not find an instructor with the provided id [" + instructorId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }
}
