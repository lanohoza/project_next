package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
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
    private DiagnosticType target;
    private List<Long> subjectIds;
}
