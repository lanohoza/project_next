package com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class TCO001LevelConditionDto {

	private Long id;
	private Double average;
	private Integer number;
	private TCE002ConditionOperate operate;
	private Long idShedSetting;
	private String shedSetting;
	private LevelDto level;
	private Long idLevel;
	private Long idGuidanceSpeciality;
	private String guidanceSpeciality;
	private Double rate;
	private Double rateMax;
	private Double averageMax;
	private TCE002ConditionOperate rateOperate;
}
