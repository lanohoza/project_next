package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operate_objectives")
public class OperateObjective {
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
	@JsonIgnore
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = true, insertable = false, updatable = false)
	private Long idCreatedBy;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_Admin", nullable = true)
	@JsonIgnore
	private Admin createdByAdmin;

	@Column(name = "created_by_Admin", nullable = true, insertable = false, updatable = false)
	private Long idAdmin;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_general_objective")
	@JsonIgnore
	GeneralObjective generalObjective;

	@Column(name = "id_general_objective", updatable = false, insertable = false)
	Long idGeneralObjective;
}