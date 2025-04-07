package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.domains.entities.*;
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
@Table(name = "tco_001_subject_averages")
@EntityListeners(AuditingEntityListener.class)

public class TCO001SubjectAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private Subject subject;
    @Column(name = "id_subject", insertable = false, updatable = false)
    private Long idSubject;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;
    @Column(name = "id_student", insertable = false, updatable = false)
    private Long idStudent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_year", nullable = false)
    private Year year;
    @Column(name = "id_year", updatable = false,insertable = false)
    private Long idYear;
    @Column(name = "Coefficient")
    Integer coefficient;
    @Column(name = "average", nullable = false)
    Double average;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_speciality")
    private Speciality speciality;
    @Column(name = "id_speciality", insertable = false, updatable = false)
    private Long idSpeciality;
    @Column(name = "average_total", nullable = false)

    Double averageTotal;
    @ManyToOne()
    @JoinColumn(name = "id_tco_001_guidance_speciality_average")
    TCO001GuidanceSpecialityAverage guidanceSpecialityAverage;
    @Column(name = "id_tco_001_guidance_speciality_average", nullable = false, insertable = false, updatable = false)
    private Long idGuidanceSpecialityAverage;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_created_by", nullable = false)
    private OriachadUser createdBy;
    @Column(name = "id_created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_classe", nullable = false)
    private Classe classe;
    @Column(name = "id_classe", insertable = false,updatable = false)
    private Long idClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_level", nullable = false)
    private Level level;
    @Column(name = "id_level", insertable = false,updatable = false)
    private Long idLevel;
}
