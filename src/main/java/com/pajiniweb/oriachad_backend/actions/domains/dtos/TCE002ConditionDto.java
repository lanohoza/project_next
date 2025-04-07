package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
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
public class TCE002ConditionDto {
    private Long id;
    private Double average;
    private Double averageMax;
    private TCE002ConditionOperate operate;
    private Long idShedSetting;
    Long idLevel;
    private DiagnosticType target;
    private List<Long> subjectIds;
}
