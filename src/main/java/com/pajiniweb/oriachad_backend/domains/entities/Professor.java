package com.pajiniweb.oriachad_backend.domains.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "professors")
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "firstName", nullable = false)

	String firstName;
	@Column(name = "lastName", nullable = false)

	String lastName;
	@Column(name = "phoneNumber", nullable = false)

	String phoneNumber;
	@Column(name = "email", nullable = false)
	String email;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_establishment", nullable = false)
	private Establishment establishment;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = false, insertable = false, updatable = false)
	private Long idCreatedBy;
	@Column(name = "id_establishment", insertable = false, updatable = false)
	private Long idEstablishment;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_subject", nullable = false)
	Subject subject;
	@Column(name = "id_subject", insertable = false, updatable = false)
	Long idSubject;
	@Column(name = "is_coordinator")
	Boolean coordinator;
	@OneToMany(mappedBy = "professor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ProfessorBreak> breaks;

	@OneToMany(mappedBy = "professor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ProfessorClasse> classes;
}
