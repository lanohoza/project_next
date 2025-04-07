package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCardCategory;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditTechnicalCardDto {

    private Long id;

    @NotNull
    private Long idTcCategory;

    @NotNull
    private TypeTc type;

    @NotNull
    private String title;

    @NotNull
    private String materielToots;

    private String feedback;
    @NotNull
    private TypeEstablishment typeEstablishment;

    private String image;

    @Nullable
    private Integer runMonth;

    @Nullable
    private Integer runWeek;

    //private SourceTechnicalCard source;

    @NotNull
    private List<Long> generalObjectiveIds;

    @NotNull
    private List<Long> audienceIds;

    @NotNull
    private List<Long> humanToolIds;

    @NotNull
    private List<Long> officialTxtIds;

    @NotNull
    private List<Long> difficultyIds;


}
