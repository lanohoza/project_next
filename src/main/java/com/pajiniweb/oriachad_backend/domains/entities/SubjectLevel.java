package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subject_levels")
public class SubjectLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;
    @Column(name = "id_subject",insertable = false,updatable = false)

    private  Long idSubject;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_speciality", nullable = false)
    private Speciality speciality;
    @Column(name = "id_speciality", updatable = false, insertable = false)
    private Long idSpeciality;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_level", nullable = false)
    private Level level;
    @Column(name = "id_level", updatable = false,insertable = false)
    private Long idLevel;


}