package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nbr_rakmana", nullable = false)
    private String nbrRakmana;

    @Column(name = "code_student")
    private String codeStudent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_establishment", nullable = false)
    private Establishment establishment;

    @Column(name = "id_establishment", insertable = false, updatable = false)
    private Long idEstablishment;
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "sexe", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender sexe;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "place_birth", nullable = false)
    private String placeBirth;

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    private List<StudentClasse> studentClasses;

    @OneToOne(mappedBy = "student")
    private InfosStudent infosStudent;

    @ManyToOne
    @JoinFormula("(SELECT sc.id_classe FROM student_classes sc JOIN classe c ON sc.id_classe = c.id JOIN year y ON c.idYear = y.id WHERE sc.id_student = id AND y.current = true LIMIT 1)")
    private Classe currentClass;
}