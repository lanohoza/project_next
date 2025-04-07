package com.pajiniweb.oriachad_backend.domains.dtos;


import com.pajiniweb.oriachad_backend.domains.enums.Gender;
import com.pajiniweb.oriachad_backend.domains.enums.StudyType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String nbrRakmana;
    private String codeStudent;
    private String firstName;
    private String lastName;
    private Gender sexe;
    private LocalDate birthDate;
    private String placeBirth;
    private StudyType schoolingSystem;
    private String classeTitle;
    private Long idClasse;
    private Long idStudentClasse;

    private Boolean repeatClasseActual ;

    private Integer nbrRepeatClasse;

    private String fatherProfession;

    private String motherProfession;

    private String tutorName;

    private String tutorMobPhone;

    private String tutorEmail;

    private LocalDate dateStudentInscription;
    private String healthProblem;


    private Boolean isMotherOrphan;

    private Boolean isFatherOrphan;


    private Boolean isNeed;

    private Boolean isDisease;
    private Boolean isMain;

}
