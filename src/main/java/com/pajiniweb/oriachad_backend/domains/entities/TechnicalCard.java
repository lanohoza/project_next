package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Action;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technical_cards")
@EntityListeners(AuditingEntityListener.class)

public class TechnicalCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "number")
	private Long number;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_Category", nullable = false)
	private TechnicalCardCategory category;
	@Column(name = "id_Category", nullable = false, insertable = false, updatable = false)
	private Long idTcCategory;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private TypeTc type;
	@Column(name = "default_tc")
	private Boolean defaultTc;
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "materiel_toots", nullable = false)
	private String materielToots;

	@Column(name = "feedback", nullable = true)
	private String feedback;

	@Column(name = "image")
	private String image;

	@Column(name = "create_date", nullable = false)
	@CreatedDate
	private LocalDate createDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = false, insertable = false, updatable = false)
	private Long idCreatedBy;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "run_month", nullable = true)
	private Integer runMonth;

	@Column(name = "run_week", nullable = true)
	private Integer runWeek;

	@Enumerated(EnumType.STRING)
	@Column(name = "type_establishment", nullable = false)
	private TypeEstablishment typeEstablishment;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_official_txts", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_official_txt"))
	Set<OfficialTxt> officialTxts = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_audiences", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_audience"))
	Set<Audience> audiences = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_difficulties", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_difficulty"))
	Set<Difficulty> difficulties = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_general_objectives", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_general_objective"))
	Set<GeneralObjective> generalObjectives = new HashSet<>();
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_human_tools", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_human_tool"))
	Set<HumanTool> humanTools = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tc_actions", joinColumns = @JoinColumn(name = "id_technical_card"), inverseJoinColumns = @JoinColumn(name = "id_action"))
	Set<Action> actions = new HashSet<>();

	@OneToMany(mappedBy = "technicalCard")
	List<Task> tasks;


}