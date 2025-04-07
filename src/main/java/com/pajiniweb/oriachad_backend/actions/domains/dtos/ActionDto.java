package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ActionDto {

    private Long id;
    private String code;
    private String title;

}
