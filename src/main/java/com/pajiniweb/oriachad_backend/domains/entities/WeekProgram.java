package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.TypeWeekProgram;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "week_program")
public class WeekProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "startWeek", nullable = false)
    private LocalDate startWeek;

    @Column(name = "endWeek", nullable = false)
    private LocalDate endWeek;

    @Column(name = "url_doc", nullable = true)
    private String urlDoc;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeWeekProgram type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_month", nullable = false)
    private Months month;

    @Column(name = "id_month", insertable = false, updatable = false)
    private Integer idMonth;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private OriachadUser createdBy;

    @Column(name = "created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;

    @OneToMany(mappedBy = "weekProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeekProgramTask> weekProgramTasks;

}
