package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.PopUpDto;
import com.pajiniweb.oriachad_backend.domains.entities.PopUp;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.PopUpRepository;
import com.pajiniweb.oriachad_backend.services.fileStorage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
public class PopUpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopUpService.class);

    @Autowired
    private PopUpRepository popUpRepository;

    @Autowired
    private FtpService ftpService;

    @Autowired
    private FileStorageService fileStorageService;

    public Page<PopUpDto> findAll(Pageable pageable, String search) throws IOException {
        LOGGER.info(Messages.START_FUNCTION, "findAll PopUp with pagination");
        return popUpRepository.search(pageable, search)
                .map(popUp -> {
                    try {
                        return toDto(popUp);
                    } catch (IOException e) {
                        LOGGER.error("Error converting PopUp to DTO", e);
                        throw new RuntimeException("Error converting PopUp to DTO", e);
                    }
                });
    }


    public Optional<PopUpDto> findById(Long id) throws IOException {
        LOGGER.info(Messages.START_FUNCTION, "findById PopUp");
        try {
            return popUpRepository.findById(id).map(popUp -> {
                try {
                    return toDto(popUp);
                } catch (IOException e) {
                    LOGGER.error("Error converting PopUp to DTO", e);
                    throw new RuntimeException("Error converting PopUp to DTO", e);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error fetching PopUp by ID", e);
            throw e;
        }
    }

    public PopUpDto save(PopUpDto popUpDto, MultipartFile file) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "save PopUp");
        try {
            if (popUpDto.getCreatedDate() == null) {
                popUpDto.setCreatedDate(LocalDate.now());
            }
            if (file != null) {
                if (popUpDto.getId() != null && popUpDto.getSourceUrl() != null) {
                    // this part for delete the old image and create new image in update case
                    PopUpDto dto = findById(popUpDto.getId()).get();
                    fileStorageService.deleteFile(dto.getSourceUrl());
                }
                String popUpImagePath = fileStorageService.saveFile(file);
                popUpDto.setSourceUrl(popUpImagePath);
            }
            PopUp popUp = toEntity(popUpDto);
            return toDto(popUpRepository.save(popUp));
        } catch (Exception e) {
            LOGGER.error("Error saving PopUp", e);
            throw e;
        }
    }

    public PopUpDto update(Long id, PopUpDto PopUpDto) throws IOException {
        LOGGER.info(Messages.START_FUNCTION, "update PopUp");
        try {
            if (!popUpRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "PopUp");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "PopUp"));
            }
            PopUp popUp = toEntity(PopUpDto);
            popUp.setId(id);
            return toDto(popUpRepository.save(popUp));
        } catch (Exception e) {
            LOGGER.error("Error updating PopUp", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) throws IOException {
        LOGGER.info(Messages.START_FUNCTION, "deleteById PopUp");
        try {
            if (popUpRepository.existsById(id)) {
                String sourceUrl = findById(id).get().getSourceUrl();
                if (sourceUrl != null) {
                    fileStorageService.deleteFile(sourceUrl);
                }

                popUpRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "PopUp");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "PopUp");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting PopUp", e);
            throw e;
        }
    }

    public boolean publish(Long id, Boolean publish) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "publish PopUp");
        if (publish) {
            if (popUpRepository.existsByPublishTrue()) {
                throw new Exception("لا يمكن تفعيل أكثر من إعلان في آن واحد");
            }
        }

        if (popUpRepository.existsById(id)) {
            PopUpDto popUpDto = findById(id).get();
            popUpDto.setPublish(publish);

            PopUp popUp = toEntity(popUpDto);
            popUpRepository.save(popUp);
            LOGGER.info(Messages.PROCESS_SUCCESSFULLY, "publish PopUp");
            return true;
        } else {
            LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "PopUp");
            return false;
        }
    }

    public PopUpDto getPublishedPopUp() {
        try {
            LOGGER.info(Messages.START_FUNCTION, "getPublishedPopUp");
            PopUp popUp = popUpRepository.findByPublishTrue();
            if (popUp != null) {
                return toDto(popUp);
            } else {
                LOGGER.info("There are no pop up page to display.");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error(Messages.PROCESS_FAILED, "getPublishedPopUp because :" + e.getMessage());
            return null;
        }
    }

    private PopUpDto toDto(PopUp popUp) throws IOException {
        PopUpDto.PopUpDtoBuilder popUpDto = PopUpDto.builder()
                .id(popUp.getId())
                .title(popUp.getTitle())
                .description(popUp.getDescription())
                .sourceUrl(popUp.getSourceUrl())
                .targetUrl(popUp.getTargetUrl())
                .publish(popUp.getPublish())
                .createdDate(popUp.getCreateDate());

        if (popUp.getSourceUrl() != null && !popUp.getSourceUrl().trim().isEmpty()) {
            byte[] photoBytes = fileStorageService.loadFile(popUp.getSourceUrl());
            String base64Image = Base64.getEncoder().encodeToString(photoBytes);
            popUpDto.image(base64Image);
        }

        return popUpDto.build();
    }

    private PopUp toEntity(PopUpDto popUpDto) {
        return PopUp.builder()
                .id(popUpDto.getId())
                .title(popUpDto.getTitle())
                .description(popUpDto.getDescription())
                .sourceUrl(popUpDto.getSourceUrl())
                .targetUrl(popUpDto.getTargetUrl())
                .publish(popUpDto.getPublish())
                .createDate(popUpDto.getCreatedDate())
                .build();
    }
}
