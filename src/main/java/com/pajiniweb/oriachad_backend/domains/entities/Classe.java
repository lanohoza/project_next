package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classe")
public class Classe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_establishment", nullable = false)
	private Establishment establishment;

	@Column(name = "id_establishment", insertable = false, updatable = false)
	private Long idEstablishment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_level", nullable = false)
	private Level level;

	@Column(name = "id_level", insertable = false, updatable = false)
	private Long idLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_speciality")
	private Speciality speciality;
	@Column(name = "id_speciality", insertable = false, updatable = false)
	private Long idSpeciality;


	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_professor", nullable = true)
	private Professor professor;
	@Column(name = "id_professor", insertable = false, updatable = false)
	private Long idProfessor;
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "number", nullable = false)
	private Integer number;

	@Column(name = "idYear", updatable = false, insertable = false)
	private Long idYear;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idYear", nullable = false)
	private Year year;
	@OneToMany(mappedBy = "classe",orphanRemoval = true,cascade = CascadeType.ALL)
	private List<ClasseBreak> breaks;
	@OneToMany(mappedBy = "classe", fetch = FetchType.LAZY)
	private List<StudentClasse> studentClasses;
	//TODO:add student and techer name
	private String teacherFullName;
	private String studentFullName;
}