package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "general_objective")
public class GeneralObjective {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "content", nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "source")
	private SourceTechnicalCard source;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = true)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = true, insertable = false, updatable = false)
	private Long idCreatedBy;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_admin", nullable = true)
	private Admin createdByAdmin;

	@Column(name = "created_by_admin", nullable = true, insertable = false, updatable = false)
	private Long idAdmin;

	@OneToMany(mappedBy = "generalObjective", fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnore
	List<OperateObjective> operateObjectives;

	@ManyToMany()
	@JoinTable(
			name = "tc_general_objectives",
			joinColumns = @JoinColumn(name = "id_general_objective"),
			inverseJoinColumns = @JoinColumn(name = "id_technical_card"))
	Set<TechnicalCard> technicalCards;
}



