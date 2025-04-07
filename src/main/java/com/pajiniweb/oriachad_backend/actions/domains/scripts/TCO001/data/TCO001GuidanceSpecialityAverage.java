package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.repositories.DesireSpecialtyRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tco_001_guidance_speciality_averages")
@EntityListeners(AuditingEntityListener.class)

public class TCO001GuidanceSpecialityAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_guidance_Speciality", nullable = false)
    private GuidanceSpeciality guidanceSpeciality;
    @Column(name = "id_guidance_Speciality", insertable = false, updatable = false)
    private Long idGuidanceSpeciality;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_created_by", nullable = false)
    private OriachadUser createdBy;
    @Column(name = "id_created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_year", nullable = false)
    private Year year;
    @Column(name = "id_year", updatable = false,insertable = false)
    private Long idYear;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;
    @Column(name = "id_student", insertable = false, updatable = false)
    private Long idStudent;
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
    @Column(name = "value", nullable = false)
    private Double value;

    @OneToMany(mappedBy = "guidanceSpecialityAverage",orphanRemoval = true,cascade = CascadeType.ALL)
    List<TCO001SubjectAverage> subjectAverages;

    @Column(name = "d_order")
    Integer order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_desire_specialty", nullable = false)
    private DesireSpecialty desireSpecialty;
    @Column(name = "id_desire_specialty", insertable = false,updatable = false)
    private Long idDesireSpecialty;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_speciality")
    private Speciality speciality;
    @Column(name = "id_speciality", insertable = false, updatable = false)
    private Long idSpeciality;
}
