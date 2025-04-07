package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.Student;
import com.pajiniweb.oriachad_backend.domains.enums.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentNoteDto {

    private Long id;
    private String nbrRakmana;
    private String codeStudent;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String placeBirth;
    ReserveStatus reserveStatus;

    public StudentNoteDto(Student student, boolean status) {
        this.id = student.getId();
        this.nbrRakmana = student.getNbrRakmana();
        this.codeStudent = student.getCodeStudent();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.birthDate = student.getBirthDate();
        this.placeBirth = student.getPlaceBirth();
        if (status) this.reserveStatus = ReserveStatus.reserved;
        else this.reserveStatus = ReserveStatus.unreserved;

    }
}
