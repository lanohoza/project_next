package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShedSettingDto {
    private Long id;
    private Long idShedCategory;
    private Long idSpeciality;
    private DiagnosticType target;
    private String reference;
    private String syndromeDiagnostic;
    private Long number;
    private Boolean hasGroup;
    private String groupName;
    private List<Long> supportCounselors;
    private List<Long> supportStudents;
    private List<Long> requiredProcedures;
    private List<Long> directionSheds;
}
