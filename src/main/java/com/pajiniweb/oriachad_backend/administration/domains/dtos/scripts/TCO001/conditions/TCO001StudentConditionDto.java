package com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001StudentCondition;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Subject;
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

public class TCO001StudentConditionDto {

	private Long id;

	private Double average;
	private Double subjectAverage;

	private Integer rank;
	private Integer number;
	private TCE002ConditionOperate guidanceOperate;
	TCO001StudentCondition.TCO001StudentConditionSubjectType subjectType;
	private Long idShedSetting;
	private String shedSetting;
	private Long idGuidanceSpeciality;

	private TCE002ConditionOperate subjectOperate;
	private Double subjectAverageMax;
	private Double averageMax;

}
