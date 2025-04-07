package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.data;

import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tco_001_guidance_speciality_configs")
@EntityListeners(AuditingEntityListener.class)

public class TCO001GuidanceSpecialityConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_guidanceSpeciality")
    private GuidanceSpeciality guidanceSpeciality;
    @Column(name = "id_guidanceSpeciality", insertable = false, updatable = false)
    private Long idGuidanceSpeciality;

    @OneToMany(mappedBy = "guidanceSpecialityConfig",orphanRemoval = true,cascade = CascadeType.ALL)
    List<TCO001SubjectConfig> subjectConfigs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_level")
    private Level level;
    @Column(name = "id_level", insertable = false, updatable = false)
    private Long idLevel;

}
