package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.GuidanceSpecialityType;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "guidance_specialities")
public class GuidanceSpeciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GuidanceSpecialityType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "establishment_type", nullable = false)
    private TypeEstablishment establishmentType;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_speciality", nullable = false)
    private Speciality speciality;
    @Column(name = "id_speciality", insertable = false, updatable = false)
    private Long idSpeciality;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_speciality_for", nullable = false)
    private Speciality specialityFor;
    @Column(name = "id_speciality_for", insertable = false, updatable = false)
    private Long idSpecialityFor;
}