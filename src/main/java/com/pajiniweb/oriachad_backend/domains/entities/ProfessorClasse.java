package com.pajiniweb.oriachad_backend.domains.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "professor_classes")
public class ProfessorClasse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_classe", nullable = false)
	Classe classe;

	@Column(name = "id_classe", updatable = false, insertable = false)
	Long idClasse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_professor", nullable = false)
	Professor professor;

	@Column(name = "id_professor", updatable = false, insertable = false)
	Long idProfessor;

	@ManyToOne
	@JoinColumn(name = "id_year", nullable = false)
	Year year;

	@Column(name = "id_year", updatable = false, insertable = false)
	Long idYear;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", insertable = false, updatable = false)
	private Long idCreatedBy;

}
