package com.pajiniweb.oriachad_backend.actions.domains.entities;

import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "activities")
@EntityListeners(AuditingEntityListener.class)
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	/*@ManyToOne()
	@JoinColumn(name = "id_actionTask")
	private ActionTask actionTask;
	@Column(name = "id_actionTask", insertable = false, updatable = false)
	private Long idActionTask;*/
	@Column(name = "content", nullable = false)
	String content;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private OriachadUser user;
	@Column(name = "id_user", insertable = false, updatable = false)
	private Long idUser;
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDate createdDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ActivityType type;
}
