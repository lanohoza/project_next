package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoInterviewDto {

    private Long idInterview;
    private String interviewDate;
}
