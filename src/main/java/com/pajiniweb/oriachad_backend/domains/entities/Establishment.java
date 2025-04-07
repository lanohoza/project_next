package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.RegimeEtablissement;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "establishment")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "code",unique = true)
    private String code;
    @Column(name = "fax_number", nullable = true, length = 100)
    private String faxNumber;

    @Column(name = "adresse")
    private String adresse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commune", nullable = false)
    private Commune commune;
    @Column(name = "id_commune", updatable = false,insertable = false)
    private Long idCommune;

    @Column(name = "mobile_number", nullable = true, length = 100)
    private String mobileNumber;

    @Column(name = "name_administrator", nullable = true)
    private String nameAdministrator;

    @Column(name = "facebook", nullable = true)
    private String facebook;

    @Column(name = "youtube", nullable = true)
    private String youtube;

    @Enumerated(EnumType.STRING)
    @Column(name = "regime")
    private RegimeEtablissement regime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEstablishment type;

    @Column(name = "email")
    private String email;
}