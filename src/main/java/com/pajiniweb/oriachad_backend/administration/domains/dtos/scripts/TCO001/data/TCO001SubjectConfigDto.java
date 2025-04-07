package com.pajiniweb.oriachad_backend.administration.domains.dtos.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.domains.entities.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class TCO001SubjectConfigDto {

    private Long id;

    private Long idSubject;

    Integer coefficient;
    Boolean basic;


}
