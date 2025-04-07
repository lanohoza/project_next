package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;
import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tco_001_level_conditions")
@EntityListeners(AuditingEntityListener.class)

public class TCO001LevelCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "average")
    private Double average;
    @Column(name = "average_max")
    private Double averageMax;
    @Column(name = "number")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "operate")
    private TCE002ConditionOperate operate;

    @ManyToOne()
    @JoinColumn(name = "id_shed_setting")
    private ShedSetting shedSetting;

    @Column(name = "id_shed_setting", updatable = false, insertable = false)
    private Long idShedSetting;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_level")
    private Level level;
    @Column(name = "id_level", insertable = false, updatable = false)
    private Long idLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_guidanceSpeciality")
    private GuidanceSpeciality guidanceSpeciality;
    @Column(name = "id_guidanceSpeciality", insertable = false, updatable = false)
    private Long idGuidanceSpeciality;

    @Column(name = "rate")
    Double rate;
    @Column(name = "rate_max")
    Double rateMax;
    @Enumerated(EnumType.STRING)
    @Column(name = "rate_operate")
    private TCE002ConditionOperate rateOperate;
}
