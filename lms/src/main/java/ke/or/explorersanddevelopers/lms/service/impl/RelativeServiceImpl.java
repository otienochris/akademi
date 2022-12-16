package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.RelativeMapper;
import ke.or.explorersanddevelopers.lms.model.dto.RelativeDto;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.repositories.RelativeRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import ke.or.explorersanddevelopers.lms.service.RelativeService;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 09/10/2022
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RelativeServiceImpl implements RelativeService {
    private final AppUserRepository appUserRepository;

    private final RelativeRepository relativeRepository;
    private final RelativeMapper relativeMapper;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public RelativeDto saveNewRelative(RelativeDto relativeDto) {
        log.info("Saving a new relative");
        Relative mappedRelative = relativeMapper.toEntity(relativeDto);

        log.info("Saving an app user associated with the relative");
        String username = relativeDto.getEmail();
        AppUser savedAppUser = appUserRepository.save(AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(relativeDto.getNewPassword()))
                .emailVerificationCode(UUID.randomUUID().toString())
                .build());

        log.info("Assigning role to the relative");
        userService.addRoleToUser(username, RolesEnum.ROLE_RELATIVE.name());

        log.info("Associating the app user details with the relative details ");
        mappedRelative.setAppUser(savedAppUser);
        Relative savedRelative = relativeRepository.save(mappedRelative);

        log.info("Saved a new relative");
        return relativeMapper.toDto(savedRelative);
    }

    @Override
    public RelativeDto getRelativeById(BigDecimal relativeId) {
        log.info("Retrieving a relative by id [" + relativeId + "]");
        Relative relative = getRelativeByIdFromDb(relativeId);
        log.info("Retrieved a relative by id [" + relativeId + "]");
        return relativeMapper.toDto(relative);
    }

    @Override
    public List<RelativeDto> getListOfRelatives(Pageable pageable) {
        log.info("Retrieving a list of relatives");
        List<RelativeDto> response = new ArrayList<>();
        relativeRepository.findAll(pageable).forEach(relative -> response.add(relativeMapper.toDto(relative)));

        if (response.size() == 0)
            log.warn("Retrieved an empty list of relatives");
        else
            log.info("Successfully retrieved a list of relatives");
        return response;
    }

    @Override
    public RelativeDto trackStudent(BigDecimal relativeId, String studentToken) {
        Relative relativeByIdFromDb = getRelativeByIdFromDb(relativeId);

        appUserRepository.getByToken(studentToken).ifPresentOrElse(appUser -> {
            Student student = studentRepository.getByEmail(appUser.getUsername()).orElseThrow(() -> {
                String message = "We could not find a student with the provided token [" + studentToken + "]";
                log.error(message);
                throw new NoSuchRecordException(message);
            });

            // save student to relative
            if (relativeByIdFromDb.getStudents() == null)
                relativeByIdFromDb.setStudents(new ArrayList<>());

            relativeByIdFromDb.getStudents().add(student);
            Relative savedRelative = relativeRepository.save(relativeByIdFromDb); // save the updates

            // save relative to student
            if (student.getRelatives() == null)
                student.setRelatives(new ArrayList<>());
            student.getRelatives().add(savedRelative);
            studentRepository.save(student);

        }, () -> {

        });

        return relativeMapper.toDto(relativeRepository.getByRelativeId(relativeId).orElseThrow());
    }

    @Override
    public Boolean deleteRelativeById(BigDecimal relativeId) {
        Relative relativeByIdFromDb = getRelativeByIdFromDb(relativeId);
        relativeRepository.delete(relativeByIdFromDb);
        return true;
    }

    private Relative getRelativeByIdFromDb(BigDecimal relativeId) {
        return relativeRepository.getByRelativeId(relativeId).orElseThrow(() -> {
            String message = "We could not find a relative with the provided id [" + relativeId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }
}
