package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.AddressMapper;
import ke.or.explorersanddevelopers.lms.mappers.InstructorMapper;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.repositories.AddressRepository;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.repositories.InstructorRepository;
import ke.or.explorersanddevelopers.lms.service.InstructorService;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {
    private final AppUserRepository appUserRepository;

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;

    @Override
    public InstructorDto saveNewInstructor(InstructorDto instructorDto) {
        log.info("Saving a new instructor");
        Instructor mappedInstructor = instructorMapper.toEntity(instructorDto);
        String username = instructorDto.getEmail();

        log.info("Creating an app user associated with the instructor");
        AppUser savedAppUser = appUserRepository.save(
                AppUser.builder()
                        .emailVerificationCode(UUID.randomUUID().toString())
                        .password(passwordEncoder.encode(instructorDto.getNewPassword()))
                        .username(username)
                        .build());

        log.info("Adding role to instructor");
        userService.addRoleToUser(username, RolesEnum.ROLE_INSTRUCTOR.name());

        log.info("Saving the instructor details");
        mappedInstructor.setAppUser(savedAppUser);
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
        if (response.isEmpty())
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

    @Override
    public InstructorDto addAddress(BigDecimal instructorId, AddressDto addressDto) {
        Instructor oldInstructorRecord = getInstructorByIdFromDb(instructorId);
        Address addressEntity = addressMapper.toEntity(addressDto);

        Address savedAddress = addressRepository.save(addressEntity);
        List<Address> addresses = oldInstructorRecord.getAddresses();
        if (addresses == null) {
            oldInstructorRecord.setAddresses(new ArrayList<>());
        }
        oldInstructorRecord.getAddresses().add(savedAddress);

        Instructor newInstructorRecord = instructorRepository.save(oldInstructorRecord);

        return instructorMapper.toDto(newInstructorRecord);
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
