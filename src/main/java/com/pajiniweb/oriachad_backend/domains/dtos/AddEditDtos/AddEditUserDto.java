package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEditUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String image;
    private String addresse;
    private String email;
    private String faxNumber;
    private String phoneNumber;
    private String webSite;
    private String password;
    private Long idCommune;
    private Long code;
    private Long idEstablishment;
    private Boolean active;
    private String userAttestationPath;
}
