package com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class TCO001GuidanceSpecialityConfigDto {

    private Long id;


    private Long idGuidanceSpeciality;
    private Long idLevel;
String guidanceSpeciality;
    LevelDto level;
    TypeEstablishment typeEstablishment;
    List<TCO001SubjectConfigDto> subjectConfigs;
}
