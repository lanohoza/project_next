package com.pajiniweb.oriachad_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {

	private Long id;

	private TaskStatus status;

	private Year year;

	private Long idYear;

	private TechnicalCard technicalCard;

	private Long idTechnicalCard;

	private LocalDate createdDate;

	private LocalDate lastModifiedDate;

	private String description;

}