package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditAverageDto {
    private Long id;

    @NotNull
    @Min(0)
    @Max(20)
    private Double value;

}
