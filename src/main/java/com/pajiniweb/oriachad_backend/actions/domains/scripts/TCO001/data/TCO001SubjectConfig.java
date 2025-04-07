package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
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
@Entity
@Table(name = "tco_001_subject_configs")
@EntityListeners(AuditingEntityListener.class)

public class TCO001SubjectConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private Subject subject;
    @Column(name = "id_subject", insertable = false, updatable = false)
    private Long idSubject;

    @Column(name = "Coefficient")
    Integer coefficient;

    @Column(name = "basic")
    Boolean basic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tco_001_guidance_speciality_config")
    TCO001GuidanceSpecialityConfig guidanceSpecialityConfig;
}
