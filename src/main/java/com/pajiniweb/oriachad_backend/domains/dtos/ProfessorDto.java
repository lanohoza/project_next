package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {

    private Long id;

    private	String firstName;

    private	String lastName;

    private	String phoneNumber;
    private	String email;
    Long idSubject;
    Boolean coordinator;

    String subjectTitle;
    List<ProfessorBreakDto> breaks;
    List<Long> classes;
}
