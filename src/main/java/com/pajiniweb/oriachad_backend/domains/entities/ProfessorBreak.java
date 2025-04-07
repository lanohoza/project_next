package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "professor_breaks")
public class ProfessorBreak {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_professor", nullable = false)
	private Professor professor;

	@Column(name = "id_professor", insertable = false, updatable = false)
	private Long idProfessor;

	@Column(name = "start_hour")

	private LocalTime startHour;
	@Column(name = "end_hour")

	private LocalTime endHour;
	@Column(name = "breakDay")
	private Integer breakDay;

}