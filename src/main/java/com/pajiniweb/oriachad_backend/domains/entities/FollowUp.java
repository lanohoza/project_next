package com.pajiniweb.oriachad_backend.domains.entities;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.domains.enums.FlowUpCreateType;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "follow_ups")
@EntityListeners(AuditingEntityListener.class)

public class FollowUp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "create_date", nullable = false)
	@CreatedDate
	private LocalDateTime createdDate;

	@Column(name = "create_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private FlowUpCreateType createType;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private FollowUpType type;
	@Column(name = "number", nullable = false)
	private Long number;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private FollowupStatus status;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_year", nullable = false)
	private Year year;
	@Column(name = "id_year", updatable = false, insertable = false)
	private Long idYear;

	@Column(name = "description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "id_student")
	private Student student;
	@Column(name = "id_student", updatable = false, insertable = false)
	private Long idStudent;
	@ManyToOne
	@JoinColumn(name = "id_guidanceGroup")
	private GuidanceGroup guidanceGroup;
	@Column(name = "id_guidanceGroup", updatable = false, insertable = false)
	private Long idGuidanceGroup;
	@ManyToOne()
	@JoinColumn(name = "id_task")
	private Task task;
	@Column(name = "id_task", insertable = false, updatable = false)
	private Long idTask;

//	@ManyToMany()
//	@JoinTable(name = "follow_ups_difficulties", joinColumns = @JoinColumn(name = "id_follow_up"), inverseJoinColumns = @JoinColumn(name = "id_difficulty"))
//	Set<Difficulty> difficulties;
//	@ManyToMany()
//	@JoinTable(name = "follow_ups_solutions", joinColumns = @JoinColumn(name = "id_follow_up"), inverseJoinColumns = @JoinColumn(name = "id_solution"))
//	Set<Solution> solutions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shed_category_id", nullable = false)
	private ShedCategory shedCategory;

	@Column(name = "shed_category_id", nullable = false, updatable = false, insertable = false)
	private Long idShedCategory;
	@ManyToMany()
	@JoinTable(name = "follow_ups_shed_settings", joinColumns = @JoinColumn(name = "id_follow_up"), inverseJoinColumns = @JoinColumn(name = "id_shed_setting"))
	Set<ShedSetting> shedSettings= new HashSet<>();
	@Column(name = "resourceUrl")
	String resourceUrl;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = false, insertable = false, updatable = false)
	private Long idCreatedBy;
}