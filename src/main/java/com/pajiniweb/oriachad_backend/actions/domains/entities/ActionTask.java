package com.pajiniweb.oriachad_backend.actions.domains.entities;

import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionStatus;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "action_tasks")
@EntityListeners(AuditingEntityListener.class)
public class ActionTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	ActionStatus status;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private OriachadUser user;
	@Column(name = "id_user", insertable = false, updatable = false)
	private Long idUser;
	@ManyToOne()
	@JoinColumn(name = "id_task")
	Task task;
	@Column(name = "id_task", insertable = false, updatable = false)
	Long idTask;
	@ManyToOne()
	@JoinColumn(name = "id_action")
	Action action;
	@Column(name = "id_action", insertable = false, updatable = false)
	Long idAction;
	@ManyToOne()
	@JoinColumn(name = "id_script")
	Script script;
	@Column(name = "id_script", insertable = false, updatable = false)
	Long idScript;

	@Column(name = "result_type")
	@Enumerated(EnumType.STRING)
	private ActionResultType resultType;

	@Column(name = "result_value")
	private String resultValue;
}
