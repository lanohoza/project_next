package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupSubjectAverage {
    private Long idSubject;
    private Double average;
    public GroupSubjectAverage(Long idSubject,Double average){
        this.idSubject = idSubject;
        this.average = average;
    }

}
