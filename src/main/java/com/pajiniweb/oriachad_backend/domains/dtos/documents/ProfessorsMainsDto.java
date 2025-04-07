package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorsMainsDto {
    String wilayaName;
    String userName;
    String establishmentName;
    String yearTitle;

    List<ProfessorMain> professors;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfessorMain {
        String fullName;
        String classeTitle;
        String subjectTitle;

    }
}
