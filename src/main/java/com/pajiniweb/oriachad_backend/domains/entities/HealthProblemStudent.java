package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "health_problem_student")
public class HealthProblemStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student idStudent;

    @Column(name = "title_Health_problem", nullable = false)
    private String titleHealthProblem;

    @Column(name = "date_Health_problem", nullable = false)
    private LocalDate dateHealthProblem;

    @Lob
    @Column(name = "description_Health_problem", nullable = false)
    private String descriptionHealthProblem;

    @Lob
    @Column(name = "Health_categorie", nullable = false)
    private String healthCategorie;

}