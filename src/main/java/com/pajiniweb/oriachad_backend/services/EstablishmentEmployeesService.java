package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditEstablishmentEmployeesDto;
import com.pajiniweb.oriachad_backend.domains.entities.EstablishmentEmployees;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishmentEmployees;
import com.pajiniweb.oriachad_backend.repositories.EstablishmentEmployeesRepository;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstablishmentEmployeesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstablishmentEmployeesService.class);

    @Autowired
    private EstablishmentEmployeesRepository establishmentEmployeesRepository;

    @Autowired
    private HelperService helperService;

    public List<AddEditEstablishmentEmployeesDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll EstablishmentEmployees");
        return establishmentEmployeesRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AddEditEstablishmentEmployeesDto> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById EstablishmentEmployees");
        try {
            return establishmentEmployeesRepository.findById(id).map(this::toDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching EstablishmentEmployees by ID", e);
            throw e;
        }
    }

    public AddEditEstablishmentEmployeesDto save(AddEditEstablishmentEmployeesDto dto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "save EstablishmentEmployees");
        try {
            if (establishmentEmployeesRepository.existsByEmail(dto.getEmail())) {
                throw new Exception("البريد الإلكتروني مستخدم من قبل");
            }
            if (establishmentEmployeesRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
                throw new Exception("رقم الهاتف مستخدم من قبل");
            }
            EstablishmentEmployees employee = toEntity(dto);
            employee.setCreatedBy(getAuthenticatedUser());
            return toDto(establishmentEmployeesRepository.save(employee));
        } catch (Exception e) {
            LOGGER.error("Error saving EstablishmentEmployees", e);
            throw e;
        }
    }

    public AddEditEstablishmentEmployeesDto update(Long id, AddEditEstablishmentEmployeesDto dto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "update EstablishmentEmployees");
        try {
            if (!establishmentEmployeesRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "EstablishmentEmployees");
                throw new Exception(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "EstablishmentEmployees"));
            }
            if (establishmentEmployeesRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
                throw new Exception("البريد الإلكتروني مستخدم من قبل");
            }
            if (establishmentEmployeesRepository.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id)) {
                throw new Exception("رقم الهاتف مستخدم من قبل");
            }
            EstablishmentEmployees employee = toEntity(dto);
            employee.setId(id);
            employee.setCreatedBy(getAuthenticatedUser());
            return toDto(establishmentEmployeesRepository.save(employee));
        } catch (Exception e) {
            LOGGER.error("Error updating EstablishmentEmployees", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById EstablishmentEmployees");
        try {
            if (establishmentEmployeesRepository.existsById(id)) {
                establishmentEmployeesRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "EstablishmentEmployees");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "EstablishmentEmployees");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting EstablishmentEmployees", e);
            throw e;
        }
    }

    public Page<AddEditEstablishmentEmployeesDto> findByCreatedBy(int page, int size) {
        LOGGER.info(Messages.START_FUNCTION, "findByCreatedBy EstablishmentEmployees");
        try {
            Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
            return establishmentEmployeesRepository.findByIdCreatedBy(getAuthenticatedUser().getId(), pageable)
                    .map(this::toDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching paginated EstablishmentEmployees by createdBy", e);
            throw e;
        }
    }

    public Page<AddEditEstablishmentEmployeesDto> search(String search, Pageable pageable) {
        LOGGER.info(Messages.START_FUNCTION, "searchByUserAndQuery EstablishmentEmployees");
        try {
            Long idUser = getAuthenticatedUser().getId();
            return establishmentEmployeesRepository.search(idUser, search, pageable)
                    .map(this::toDto);
        } catch (Exception e) {
            LOGGER.error("Error fetching paginated EstablishmentEmployees by search query", e);
            throw e;
        }
    }

    private AddEditEstablishmentEmployeesDto toDto(EstablishmentEmployees employee) {
        return AddEditEstablishmentEmployeesDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .type(employee.getType())
                .idCreatedBy(employee.getCreatedBy().getId())
                .build();
    }

    private EstablishmentEmployees toEntity(AddEditEstablishmentEmployeesDto dto) {
        return EstablishmentEmployees.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .type(dto.getType())
                .build();
    }

    private OriachadUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getOriachadUser();
    }
}
