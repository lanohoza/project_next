package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.entities.Subject;
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
@Table(name = "tce_002_conditions")
@EntityListeners(AuditingEntityListener.class)

public class TCE002Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "average")
    private Double average;

    @Column(name = "averageMax")
    private Double averageMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "operate")
    private TCE002ConditionOperate operate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_shed_setting")
    private ShedSetting shedSetting;

    @Column(name = "id_shed_setting", updatable = false, insertable = false)
    private Long idShedSetting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_speciality")
    private Speciality speciality;
    @Column(name = "id_speciality", insertable = false, updatable = false)
    private Long idSpeciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_level")
    private Level Level;
    @Column(name = "id_level", insertable = false, updatable = false)
    private Long idLevel;
    @Enumerated(EnumType.STRING)
    @Column(name = "target")
    private DiagnosticType target;

    @ManyToMany()
    @JoinTable(name = "TCE002_condition_types_subjects", joinColumns = @JoinColumn(name = "id_TCE002_condition_type"), inverseJoinColumns = @JoinColumn(name = "id_subject"))
    private List<Subject> subjects;
}
