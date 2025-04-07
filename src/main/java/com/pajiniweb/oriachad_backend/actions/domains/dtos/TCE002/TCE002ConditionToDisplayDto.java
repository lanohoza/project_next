package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002;

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
public class TCE002ConditionToDisplayDto {
    private Long id;
    private Double average;
    private Double averageMax;
    private TCE002ConditionOperate operate;
    private Long idShedSetting;
    private String shedSetting;
    private Long idShedSettingCategory;
    Long idLevel;
    private DiagnosticType target;
    private List<Long> subjectIds;
    private List<String> subjectTitle;
}
