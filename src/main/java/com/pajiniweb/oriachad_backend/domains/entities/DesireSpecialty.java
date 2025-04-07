package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data.TCO001GuidanceSpecialityAverage;
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
@Table(name = "desire_specialties")
public class DesireSpecialty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_desire", nullable = false)
	private Desire desire;
	@Column(name = "id_desire",insertable = false, updatable = false	)
	private Long idDesire;
	@Column(name = "d_order", nullable = false)
	Integer order;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private OriachadUser user;
	@Column(name = "id_user", insertable = false, updatable = false)
	private Long idUser;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_guidance_Speciality", nullable = false)
	private GuidanceSpeciality guidanceSpeciality;
	@Column(name = "id_guidance_Speciality", insertable = false, updatable = false)
	private Long idGuidanceSpeciality;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_student", nullable = false)
	private Student student;
	@Column(name = "id_student", insertable = false,updatable = false)
	private Long idStudent;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_year", nullable = false)
	private Year year;
	@Column(name = "id_year", updatable = false, insertable = false)
	private Long idYear;
	@OneToMany(mappedBy = "desireSpecialty",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<TCO001GuidanceSpecialityAverage> guidanceSpecialityAverages;
}