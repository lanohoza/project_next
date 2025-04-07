package com.pajiniweb.oriachad_backend.administration.services;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.PlanUserAddEditDto;
import com.pajiniweb.oriachad_backend.administration.domains.entities.PlanUser;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.administration.repositories.PlanUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanUserService.class);

    @Autowired
    private PlanUserRepository planUserRepository;

    public List<PlanUserAddEditDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll PlanUser");
        return planUserRepository.findAll().stream()
                .map(planUser -> PlanUserAddEditDto.builder()
                        .id(planUser.getId())
                        .idPlan(planUser.getIdPlan())
                        .idUser(planUser.getIdUser())
                        .isActive(planUser.getIsActive())
                        .dateDebutActivation(planUser.getDateDebutActivation())
                        .dateFinActivation(planUser.getDateFinActivation())
                        .paymentStatus(planUser.getPaymentStatus())
                        .build())
                .toList();
    }

    public Optional<PlanUser> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById PlanUser");
        try {
            return planUserRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Error fetching PlanUser by id", e);
            throw e;
        }
    }

    public PlanUser save(PlanUserAddEditDto planUserDto) {
        LOGGER.info(Messages.START_FUNCTION, "save PlanUser");
        try {
            PlanUser planUser = new PlanUser(); // Create a new PlanUser entity
            planUser.setIdPlan(planUserDto.getIdPlan());
            planUser.setIdUser(planUserDto.getIdUser());
            planUser.setIsActive(planUserDto.getIsActive());
            planUser.setDateDebutActivation(planUserDto.getDateDebutActivation());
            planUser.setDateFinActivation(planUserDto.getDateFinActivation());
            planUser.setPaymentStatus(planUserDto.getPaymentStatus());
            return planUserRepository.save(planUser);
        } catch (Exception e) {
            LOGGER.error("Error saving PlanUser", e);
            throw e;
        }
    }

    public PlanUser update(Long id, PlanUserAddEditDto planUserDto) {
        LOGGER.info(Messages.START_FUNCTION, "update PlanUser");
        try {
            if (!planUserRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "PlanUser");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "PlanUser"));
            }
            PlanUser planUser = new PlanUser(); // Create a new PlanUser entity
            planUser.setId(id);
            planUser.setIdPlan(planUserDto.getIdPlan());
            planUser.setIdUser(planUserDto.getIdUser());
            planUser.setIsActive(planUserDto.getIsActive());
            planUser.setDateDebutActivation(planUserDto.getDateDebutActivation());
            planUser.setDateFinActivation(planUserDto.getDateFinActivation());
            planUser.setPaymentStatus(planUserDto.getPaymentStatus());
            return planUserRepository.save(planUser);
        } catch (Exception e) {
            LOGGER.error("Error updating PlanUser", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById PlanUser");
        try {
            if (planUserRepository.existsById(id)) {
                planUserRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "PlanUser");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "PlanUser");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting PlanUser", e);
            throw e;
        }
    }

    public List<PlanUserAddEditDto> findByIdUser(Long idUser) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdUser PlanUser");
        try {
            return planUserRepository.findByIdUser(idUser).stream()
                    .map(planUser -> PlanUserAddEditDto.builder()
                            .id(planUser.getId())
                            .idPlan(planUser.getIdPlan())
                            .idUser(planUser.getIdUser())
                            .isActive(planUser.getIsActive())
                            .dateDebutActivation(planUser.getDateDebutActivation())
                            .dateFinActivation(planUser.getDateFinActivation())
                            .paymentStatus(planUser.getPaymentStatus())
                            .build())
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Error fetching PlanUser by idUser", e);
            throw e;
        }
    }

    public List<PlanUserAddEditDto> findByIdPlan(Long idPlan) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdPlan PlanUser");
        try {
            return planUserRepository.findByIdPlan(idPlan).stream()
                    .map(planUser -> PlanUserAddEditDto.builder()
                            .id(planUser.getId())
                            .idPlan(planUser.getIdPlan())
                            .idUser(planUser.getIdUser())
                            .isActive(planUser.getIsActive())
                            .dateDebutActivation(planUser.getDateDebutActivation())
                            .dateFinActivation(planUser.getDateFinActivation())
                            .paymentStatus(planUser.getPaymentStatus())
                            .build())
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Error fetching PlanUser by idPlan", e);
            throw e;
        }
    }

    public List<PlanUserAddEditDto> findByIdUserAndIdPlan(Long idUser, Long idPlan) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdUserAndIdPlan PlanUser");
        try {
            return planUserRepository.findByIdUserAndIdPlan(idUser, idPlan).stream()
                    .map(planUser -> PlanUserAddEditDto.builder()
                            .id(planUser.getId())
                            .idPlan(planUser.getIdPlan())
                            .idUser(planUser.getIdUser())
                            .isActive(planUser.getIsActive())
                            .dateDebutActivation(planUser.getDateDebutActivation())
                            .dateFinActivation(planUser.getDateFinActivation())
                            .paymentStatus(planUser.getPaymentStatus())
                            .build())
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Error fetching PlanUser by idUser and idPlan", e);
            throw e;
        }
    }
}
