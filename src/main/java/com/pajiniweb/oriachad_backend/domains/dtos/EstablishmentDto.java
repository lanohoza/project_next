package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.RegimeEtablissement;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentDto {
    private Long id;
    private String name;
    private String faxNumber;
    private String adresse;
    private Long idCommune;
    private String mobileNumber;
    private String nameAdministrator;
    private String facebook;
    private String youtube;
    private RegimeEtablissement regime;
    private TypeEstablishment type;
    private String email;
}
