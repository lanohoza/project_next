package com.pajiniweb.oriachad_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalCardDto {



        private Long id;

        private TypeEstablishment typeEstablishment;

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

        private SourceTechnicalCard source;


        List<Long> officialTxtIds;

        List<Long> audienceIds;

        List<Long> difficultyIds;

        List<Long> generalObjectiveIds;

        List<Long> humanToolIds;

        Boolean defaultTc;

}
