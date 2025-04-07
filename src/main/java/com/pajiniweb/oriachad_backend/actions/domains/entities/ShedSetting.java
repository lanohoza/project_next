package com.pajiniweb.oriachad_backend.actions.domains.entities;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.domains.entities.FollowUp;
import com.pajiniweb.oriachad_backend.domains.entities.Interview;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "shed_settings")
@EntityListeners(AuditingEntityListener.class)
public class ShedSetting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shed_category_id", nullable = false)
	private ShedCategory shedCategory;

	@Column(name = "shed_category_id", nullable = false, updatable = false, insertable = false)
	private Long idShedCategory;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_speciality")
	private Speciality speciality;
	@Column(name = "id_speciality", insertable = false, updatable = false)
	private Long idSpeciality;
	@Enumerated(EnumType.STRING)
	@Column(name = "target")
	private DiagnosticType target;

	@Column(name = "reference")
	private String reference;

	@Column(name = "syndrome_diagnostic")
	private String syndromeDiagnostic;

	@Column(name = "number")
	private Long number;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shed_setting_support_counselor", joinColumns = @JoinColumn(name = "id_shed_setting"), inverseJoinColumns = @JoinColumn(name = "id_support_counselor"))
	private Set<SupportCounselor> supportCounselors = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shed_setting_support_student", joinColumns = @JoinColumn(name = "id_shed_setting"), inverseJoinColumns = @JoinColumn(name = "id_support_student"))
	private Set<SupportStudent> supportStudents = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shed_setting_required_procedures", joinColumns = @JoinColumn(name = "id_shed_setting"), inverseJoinColumns = @JoinColumn(name = "id_required_procedures"))
	private Set<RequiredProcedures> requiredProcedures = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shed_setting_directions_shed", joinColumns = @JoinColumn(name = "id_shed_setting"), inverseJoinColumns = @JoinColumn(name = "id_directions_shed"))
	private Set<DirectionsShed> directionSheds = new HashSet<>();
	@Column(name = "has_group")
	private	Boolean hasGroup;
	@Column(name = "group_name")
	private String groupName;
	@ManyToMany(mappedBy = "shedSettings", fetch = FetchType.LAZY)
	private List<Interview> interviews;
	@ManyToMany(mappedBy = "shedSettings", fetch = FetchType.LAZY)
	private List<FollowUp> followUps;
}
