package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalCardYearDto {
	private Long id;
	private Long idTcCategory;
	private TypeTc type;
	private String title;
	private String materielToots;
	private String feedback;
	private LocalDate createDate;
	private Long idCreatedBy;
	private String code;
	private Integer runMonth;
	private Integer runWeek;
	private Long idTask;
	private TaskStatus statusTask;
	private Long idYear;
	private LocalDate createdDateTask;
	private LocalDate lastModifiedDateTask;
	private String descriptionTask;
	private SourceTechnicalCard source;


}
