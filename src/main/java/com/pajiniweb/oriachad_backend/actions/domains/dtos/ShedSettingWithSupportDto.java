package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.entities.*;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
//@AllArgsConstructor
//@Builder
public class ShedSettingWithSupportDto {
    private Long id;
    private String reference;
    private String syndromeDiagnostic;
    private Long number;
    private List<SupportCounselorDto> supportCounselors;
    private List<SupportStudentDto> supportStudents;
    private List<RequiredProceduresDto> requiredProcedures;
    private List<DirectionsShedDto> directionSheds;

    public ShedSettingWithSupportDto(ShedSetting shedSetting, Set<DirectionsShed> directionsShed,
                                     Set<SupportStudent> supportStudent, Set<SupportCounselor> supportCounselor, Set<RequiredProcedures> requiredProcedures){


        this.id = shedSetting.getId();
        this.reference = shedSetting.getReference();
        this.syndromeDiagnostic = shedSetting.getSyndromeDiagnostic();
        this.number = shedSetting.getNumber();
        this.supportCounselors = supportCounselor.stream().map(counselor -> SupportCounselorDto.builder().id(counselor.getId()).name(counselor.getName()).url(counselor.getUrl()).build()).toList();
        this.supportStudents = supportStudent.stream().map(supportStudent1 -> SupportStudentDto.builder().id(supportStudent1.getId()).name(supportStudent1.getName()).url(supportStudent1.getUrl()).build()).toList();
        this.requiredProcedures = requiredProcedures.stream().map(requiredProcedures1 -> RequiredProceduresDto.builder().id(requiredProcedures1.getId()).name(requiredProcedures1.getName()).build()).toList();
        this.directionSheds = directionsShed.stream().map(directionsShed1 -> DirectionsShedDto.builder().id(directionsShed1.getId()).name(directionsShed1.getName()).build()).toList();


    }
}
