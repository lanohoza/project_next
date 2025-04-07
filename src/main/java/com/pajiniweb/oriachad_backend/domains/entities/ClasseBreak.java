package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classe_breaks")
public class ClasseBreak {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_classe", nullable = false)
	private Classe classe;

	@Column(name = "id_classe", insertable = false, updatable = false)
	private Long idClasse;

	@Column(name = "start_hour")

	private LocalTime startHour;
	@Column(name = "end_hour")

	private LocalTime endHour;
	@Column(name = "breakDay")
	private Integer breakDay;

}