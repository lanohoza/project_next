package com.pajiniweb.oriachad_backend.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "guidance_groups")
@EntityListeners(AuditingEntityListener.class)

public class GuidanceGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "title")
	private String title;
	@ManyToMany()
	@JoinTable(name = "guidance_group_students", joinColumns = @JoinColumn(name = "id_guidance_group"), inverseJoinColumns = @JoinColumn(name = "id_student"))
	private Set<Student> students;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private OriachadUser createdBy;

	@Column(name = "created_by", nullable = false, insertable = false, updatable = false)
	private Long idCreatedBy;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_year", nullable = false)
	private Year year;
	@Column(name = "id_year", updatable = false,insertable = false)
	private Long idYear;
}
