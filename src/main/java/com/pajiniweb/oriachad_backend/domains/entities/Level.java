package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
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
@Table(name = "level")
public class Level {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "number", nullable = false)
	private int number;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private TypeEstablishment type;

	@OneToMany(mappedBy = "level")
	private List<Classe> classes;
}