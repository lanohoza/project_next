package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.StudyType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@Where(clause = "removed = true")
@Table(name = "student_classes")
public class StudentClasse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_student", nullable = false)
	private Student student;
	@Column(name = "id_student", insertable = false, updatable = false)
	private Long idStudent;


	@Column(name = "schooling_system", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudyType schoolingSystem;
	@Column(name = "date_student_inscription", nullable = false)
	private LocalDate dateStudentInscription;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_classe", nullable = false)
	private Classe classe;
	@Column(name = "id_classe", updatable = false, insertable = false)
	private Long idClasse;
	@Column(name = "removed")
	private Boolean removed;

}