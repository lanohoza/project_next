package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.entities.Subject;
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
@Entity
@Table(name = "tco_001_speciality_conditions")
@EntityListeners(AuditingEntityListener.class)

public class TCO001SpecialityCondition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;



	@ManyToOne()
	@JoinColumn(name = "id_shed_setting")
	private ShedSetting shedSetting;

	@Column(name = "id_shed_setting", updatable = false, insertable = false)
	private Long idShedSetting;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_level")
	private Level level;
	@Column(name = "id_level", insertable = false, updatable = false)
	private Long idLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_guidanceSpeciality")
	private GuidanceSpeciality guidanceSpeciality;
	@Column(name = "id_guidanceSpeciality", insertable = false, updatable = false)
	private Long idGuidanceSpeciality;


	@Column(name = "average")
	private Double average;
	@Column(name = "average_max")
	private Double averageMax;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_subject")
	private Subject subject;
	@Column(name = "id_subject", insertable = false, updatable = false)
	private Long idSubject;
	@Column(name = "subject_average")
	private Double subjectAverage;
	@Column(name = "subject_average_max")
	private Double subjectAverageMax;
	@Column(name = "rate")
	Double rate;


	@Enumerated(EnumType.STRING)
	@Column(name = "subject_type")
	TCO001SpecialityConditionSubjectType subjectType;
	@Column(name = "number")
	private Integer number;

	@Enumerated(EnumType.STRING)
	@Column(name = "guidance_operate")
	private TCE002ConditionOperate guidanceOperate;


	@Enumerated(EnumType.STRING)
	@Column(name = "subject_operate")
	private TCE002ConditionOperate subjectOperate;
	@Column(name = "rate_max")
	Double rateMax;
	@Enumerated(EnumType.STRING)
	@Column(name = "rate_operate")
	private TCE002ConditionOperate rateOperate;
	public enum TCO001SpecialityConditionSubjectType {
		all, one, empty
	}
}
