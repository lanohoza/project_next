package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(
        name = "tce_002_averages",
        indexes = {
                @Index(name = "idx_id_classe", columnList = "id_classe"),
                @Index(name = "idx_id_year", columnList = "id_year"),
                @Index(name = "idx_id_level", columnList = "id_level"),
                @Index(name = "idx_id_student", columnList = "id_student"),
                @Index(name = "idx_id_created_by", columnList = "id_created_by")
        }
)
public class TCE002Average {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_year", nullable = false)
    private Year year;
    @Column(name = "id_year", updatable = false,insertable = false)
    private Long idYear;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;
    @Column(name = "id_student", insertable = false,updatable = false)
    private Long idStudent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_classe", nullable = false)
    private Classe classe;
    @Column(name = "id_classe", insertable = false,updatable = false)
    private Long idClasse;
    @Column(name = "value", nullable = false)
    private Double value;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_created_by", nullable = false)
    private OriachadUser createdBy;

    @Column(name = "id_created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_level", nullable = false)
    private Level level;
    @Column(name = "id_level", insertable = false,updatable = false)
    private Long idLevel;

}