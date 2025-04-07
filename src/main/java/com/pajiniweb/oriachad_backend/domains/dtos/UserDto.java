package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.Commune;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
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
    private CommuneDto idCommune;
    private Long code;
    private EstablishmentDto idEstablishment;
    private Boolean active;
    private String userAttestationPath;
}
