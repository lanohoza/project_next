package com.pajiniweb.oriachad_backend.actions.domains.entities;

import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_scripts")
@EntityListeners(AuditingEntityListener.class)
public class UserScript {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_user", nullable = false)
	private OriachadUser user;
	@Column(name = "id_user", insertable = false, updatable = false)
	private Long idUser;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_year", nullable = false)
	private Year year;
	@Column(name = "id_year", updatable = false, insertable = false)
	private Long idYear;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_script", nullable = false)
	private Script script;
	@Column(name = "id_script", insertable = false, updatable = false)
	private Long idScript;

	@Column(name = "created_date", nullable = false)
	@CreatedDate
	private LocalDateTime createdDate;
}
