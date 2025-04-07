package com.pajiniweb.oriachad_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TechnicalCardCategoryDto {


	private Long id;

	private String name;

}