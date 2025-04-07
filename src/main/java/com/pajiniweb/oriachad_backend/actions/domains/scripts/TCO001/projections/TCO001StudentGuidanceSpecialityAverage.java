package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.projections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class TCO001StudentGuidanceSpecialityAverage {
    private Double average;
    private Integer rank;
    private Integer order;
    private  String title;



}
