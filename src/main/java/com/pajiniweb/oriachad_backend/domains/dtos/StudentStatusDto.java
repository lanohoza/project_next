package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatusDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private boolean status;

    public void StudentNoteDto(Student student, boolean status) {

    }

}
