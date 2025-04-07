package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishmentEmployees;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditEstablishmentEmployeesDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private TypeEstablishmentEmployees type;
    private Long idCreatedBy;
}