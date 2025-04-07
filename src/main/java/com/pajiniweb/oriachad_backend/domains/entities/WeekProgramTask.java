package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.DaysOfWeek;
import com.pajiniweb.oriachad_backend.domains.enums.Period;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "week_program_task")
public class WeekProgramTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "days", nullable = false)
    private DaysOfWeek days;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "period", nullable = false)
    private Period period;

    @Column(name = "descrption")
    private String descrption;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_week_program", nullable = false)
    @JsonIgnore
    private WeekProgram weekProgram;

    @Column(name = "id_week_program", insertable = false , updatable = false)
    private Long idWeekProgram;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_task")
    @JsonIgnore
    private TechnicalCard task ;

    @Column(name = "id_task", insertable = false , updatable = false)
    private Long idTask;

    @Column(name = "titleTask")
    private String titleTask;
}
