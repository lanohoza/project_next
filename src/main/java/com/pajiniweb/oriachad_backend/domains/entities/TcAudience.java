package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tc_audiences")
public class TcAudience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_technical_card", nullable = false)
    private TechnicalCard technicalCard;

    @Column(name = "id_technical_card", insertable = false,updatable = false)
    private Long idTechnicalCard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_audience", nullable = false)
    private Audience audience;

    @Column(name = "id_audience", insertable = false,updatable = false)
    private Long idAudience;

}