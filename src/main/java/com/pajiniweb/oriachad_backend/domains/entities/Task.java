package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ActionTask;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_Category", nullable = false)
	private TechnicalCardCategory category;

	@Column(name = "id_Category", nullable = false, insertable = false, updatable = false)
	private Long idTcCategory;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private TypeTc type;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "materiel_toots", nullable = false)
	private String materielToots;

	@Column(name = "feedback", nullable = true)
	private String feedback;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TaskStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_year", nullable = false)
	private Year year;

	@Column(name = "id_year", insertable = false, updatable = false)
	private Long idYear;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = false, insertable = false, updatable = false)
	private Long idCreatedBy;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_technical_card", nullable = false)
	private TechnicalCard technicalCard;

	@Column(name = "id_technical_card", insertable = false, updatable = false)
	private Long idTechnicalCard;

	@Column(name = "createdDate")
	@CreatedDate
	private LocalDate createdDate;

	@Column(name = "lastModifiedDate")
	@LastModifiedDate
	private LocalDate lastModifiedDate;
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<ActionTask> actions;
	@Column(name = "code", nullable = false)
	private String code;
	@Column(name = "run_month")
	private Integer runMonth;
	@Column(name = "run_week")
	private Integer runWeek;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "task_official_txts", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_official_txt"))
	Set<OfficialTxt> officialTxts = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "task_audiences", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_audience"))
	Set<Audience> audiences = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "task_difficulties", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_difficulty"))
	Set<Difficulty> difficulties = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "task_general_objectives", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_general_objective"))
	Set<GeneralObjective> generalObjectives = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "task_human_tools", joinColumns = @JoinColumn(name = "id_task"), inverseJoinColumns = @JoinColumn(name = "id_human_tool"))
	Set<HumanTool> humanTools = new HashSet<>();


}