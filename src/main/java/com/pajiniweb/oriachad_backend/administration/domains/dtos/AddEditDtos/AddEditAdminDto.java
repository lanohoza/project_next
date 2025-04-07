package com.pajiniweb.oriachad_backend.administration.domains.dtos.AddEditDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEditAdminDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
