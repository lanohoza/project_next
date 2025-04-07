package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "infos_student")
public class InfosStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;
    @Column(name = "id_student", insertable = false, updatable = false)
    private Long idStudent;

    /**
     * study Student Information
     */
    @Column(name = "repeat_classe_actual")
    private Boolean repeatClasseActual ;

    @Column(name = "nbr_repeat_classe")
    private Integer nbrRepeatClasse;

    @Column(name = "father_profession")
    private String fatherProfession;

    @Column(name = "mother_profession")
    private String motherProfession;

    @Column(name = "tutor_name")
    private String tutorName;

    @Column(name = "tutor_mob_phone")
    private String tutorMobPhone;

    @Column(name = "tutor_fixe_phone")
    private String tutorFixePhone;

    @Column(name = "tutor_email")
    private String tutorEmail;


    @Column(name = "Health_problem")
    private String healthProblem;

    @Column(name = "isMotherOrphan")
    @ColumnDefault("false")
    private Boolean isMotherOrphan;
    @Column(name = "isFatherOrphan")
    @ColumnDefault("false")
    private Boolean isFatherOrphan;

    @Column(name = "isNeed")
    @ColumnDefault("false")
    private Boolean isNeed;
    @Column(name = "isDisease")
    @ColumnDefault("false")
    private Boolean isDisease;
    @Column(name = "isMain")
    @ColumnDefault("false")
    private Boolean isMain;

}