package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportStudentDto {
    private Long id;
    private String name;
    private String url;
}
