package com.pajiniweb.oriachad_backend.actions.domains.entities;

import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "actions")
@EntityListeners(AuditingEntityListener.class)

public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "title")
	private String title;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ActionType type;

	@Column(name = "result_type")
	@Enumerated(EnumType.STRING)
	private ActionResultType resultType;

	@Column(name = "result_value")
	private String resultValue;

	@ManyToOne()
	@JoinColumn(name = "id_script")
	Script script;
	@Column(name = "id_script", insertable = false, updatable = false)
	Long idScript;

	@ManyToMany()
	@JoinTable(name = "action_dependencies", joinColumns = @JoinColumn(name = "id_action"), inverseJoinColumns = @JoinColumn(name = "id_depend_action"))
	Set<Action> dependencies;

	@OneToMany(mappedBy = "action")
	Set<ActionTask> actionTasks;
	@Column(name = "description")

	String description;
}
