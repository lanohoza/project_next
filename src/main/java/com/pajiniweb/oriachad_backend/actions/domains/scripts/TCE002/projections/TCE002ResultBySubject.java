package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections;

import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TCE002ResultBySubject {

	private Subject subject;  // This represents the subject of the subjectLevel
	private Double sumValue; // This represents the sum of the value for the subject level
}
