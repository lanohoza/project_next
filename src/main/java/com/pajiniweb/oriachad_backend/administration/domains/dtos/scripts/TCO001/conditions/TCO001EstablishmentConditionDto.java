package com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001EstablishmentCondition;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001SpecialityCondition;
import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class TCO001EstablishmentConditionDto {

    private Long id;


    private Long idShedSetting;


    private Long idLevel;

    private Long idGuidanceSpeciality;
    private String guidanceSpeciality;
    private Integer number;

    private LevelDto level;
    private Double average;

    private Long idSubject;
    private String subject;
    private Double subjectAverage;

    Double rate;
    private TCE002ConditionOperate guidanceOperate;
    TCO001EstablishmentCondition.TCO001EstablishmentConditionSubjectType subjectType;
    private String shedSetting;
    private TCE002ConditionOperate subjectOperate;
    private Double subjectAverageMax;

    private Double rateMax;
    private Double averageMax;

    private TCE002ConditionOperate rateOperate;
}
