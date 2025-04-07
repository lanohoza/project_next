package com.pajiniweb.oriachad_backend.administration.domains.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
