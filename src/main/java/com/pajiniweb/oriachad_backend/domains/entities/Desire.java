package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "desires")
public class Desire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;
    @Column(name = "id_student", insertable = false,updatable = false)
    private Long idStudent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_year", nullable = false)
    private Year year;
    @Column(name = "id_year", updatable = false, insertable = false)
    private Long idYear;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private OriachadUser user;
    @Column(name = "id_user", insertable = false, updatable = false)
    private Long idUser;
    @OneToMany(mappedBy = "desire")
    List<DesireSpecialty> desireSpecialties;
}