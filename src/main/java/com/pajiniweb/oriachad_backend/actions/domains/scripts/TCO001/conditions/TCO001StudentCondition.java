package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
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
@Entity
@Table(name = "tco_001_student_conditions")
@EntityListeners(AuditingEntityListener.class)

public class TCO001StudentCondition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "average")
	private Double average;
	@Column(name = "average_max")
	private Double averageMax;
	@Column(name = "rank")
	private Integer rank;
	@Enumerated(EnumType.STRING)
	@Column(name = "subject_type")
	TCO001StudentConditionSubjectType subjectType;
	@Column(name = "number")
	private Integer number;

	@Enumerated(EnumType.STRING)
	@Column(name = "guidance_operate")
	private TCE002ConditionOperate guidanceOperate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_shed_setting")
	private ShedSetting shedSetting;

	@Column(name = "id_shed_setting", updatable = false, insertable = false)
	private Long idShedSetting;

	@Enumerated(EnumType.STRING)
	@Column(name = "subject_operate")
	private TCE002ConditionOperate subjectOperate;
	@Column(name = "subject_average")
	private Double subjectAverage;
	@Column(name = "subject_average_max")
	private Double subjectAverageMax;
	public enum TCO001StudentConditionSubjectType {
		all, one,empty
	}
}
