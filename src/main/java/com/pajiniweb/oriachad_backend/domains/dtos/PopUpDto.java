package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopUpDto {
    private Long id;
    private String title;
    private String description;
    private String sourceUrl;
    private String targetUrl;
    private Boolean publish;
    private LocalDate createdDate;

    private String image;
}

