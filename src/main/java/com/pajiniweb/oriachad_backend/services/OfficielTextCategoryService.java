package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.OfficialTxtDto;
import com.pajiniweb.oriachad_backend.domains.dtos.OfficielTextCategoryDto;
import com.pajiniweb.oriachad_backend.domains.entities.OfficialTxt;
import com.pajiniweb.oriachad_backend.domains.entities.OfficielTextCategory;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.OfficielTextCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficielTextCategoryService {

    private static final Logger log = LoggerFactory.getLogger(OfficialTxtService.class);

    @Autowired
    OfficielTextCategoryRepository officielTextCategoryRepository;

    private OfficielTextCategoryDto toDto(OfficielTextCategory officielTextCategory) {
        return OfficielTextCategoryDto.builder()
                .id(officielTextCategory.getId())
                .name(officielTextCategory.getName())
                .build();
    }

    // Convert DTO to OfficialTxt entity
    private OfficielTextCategory toEntity(OfficielTextCategoryDto officielTextCategoryDto) {
        OfficielTextCategory officielTextCategory = new OfficielTextCategory();
        officielTextCategory.setId(officielTextCategoryDto.getId());
        officielTextCategory.setName(officielTextCategoryDto.getName());

        return officielTextCategory;
    }

    public List<OfficielTextCategoryDto> getAllOfficialTxtsCategory() {
        try {
            log.info(Messages.START_FUNCTION, "getAllOfficialTxtsCategory");
            return officielTextCategoryRepository.findAll().stream().map(this::toDto)
                    .toList();
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getAllOfficialTxtsCategory", e);
            throw e;
        }
    }
}
