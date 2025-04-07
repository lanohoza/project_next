package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TCO0001EstablishmentCardDto {


    List<GuidanceSpecialityAverage> guidanceSpecialityAverage;
    List<String> diagnostics;
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static  class  GuidanceSpecialityAverage{

        String specialityTitle;
        double average;
        double subjectAverage1;
        double subjectAverage2;
        double countLess10;
        double percentageLess10;
        double countGreater10;
        double percentageGreater10;
        double countGreater14;
        double percentageGreater14;

        double count10_14;
        double percentage10_14;
    }
}
