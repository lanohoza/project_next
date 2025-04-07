package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tco_001_diagnostics",
        indexes = {
                @Index(name = "idx_id_classe", columnList = "id_classe"),
                @Index(name = "idx_id_year", columnList = "id_year"),
                @Index(name = "idx_id_level", columnList = "id_level"),
                @Index(name = "idx_id_student", columnList = "id_student"),
                @Index(name = "idx_id_created_by", columnList = "id_created_by")
        }
)
public class TCO001Diagnostic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_year", nullable = false)
    private Year year;

    @Column(name = "id_year", updatable = false, insertable = false)
    private Long idYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    @Column(name = "id_student", insertable = false, updatable = false)
    private Long idStudent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_classe", nullable = false)
    private Classe classe;

    @Column(name = "id_classe", insertable = false, updatable = false)
    private Long idClasse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_created_by", nullable = false)
    private OriachadUser createdBy;

    @Column(name = "id_created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_level", nullable = false)
    private Level level;

    @Column(name = "id_level", insertable = false, updatable = false)
    private Long idLevel;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @Column(name = "id_subject", insertable = false, updatable = false)
    private Long idSubject;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;

    @Column(name = "id_professor", insertable = false, updatable = false)
    private Long idProfessor;

    @ManyToOne()
    @JoinColumn(name = "id_shed_setting")
    private ShedSetting shedSetting;

    @Column(name = "id_shed_setting", updatable = false, insertable = false)
    private Long idShedSetting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_speciality")
    private Speciality speciality;
    @Column(name = "id_speciality", insertable = false, updatable = false)
    private Long idSpeciality;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
	DiagnosticType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_establishment", nullable = false)
    private Establishment establishment;

    @Column(name = "id_establishment", insertable = false, updatable = false)
    private Long idEstablishment;
}