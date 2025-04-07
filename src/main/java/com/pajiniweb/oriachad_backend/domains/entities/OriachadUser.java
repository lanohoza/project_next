package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class OriachadUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "image")
    private String image;

    @Column(name = "addresse", nullable = false)
    private String addresse;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fax_number", length = 45)
    private String faxNumber;

    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;

    @Column(name = "webSite")
    private String webSite;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commune", nullable = false)
    @JsonIgnore
    private Commune commune;

    @Column(name = "id_commune", nullable = false, insertable = false, updatable = false)
    private Long idCommune;

    @Column(name = "code", nullable = false)
    private Long code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_establishment", nullable = false)
    private Establishment establishment;

    @Column(name = "id_establishment", nullable = false, updatable = false, insertable = false)
    private Long idEstablishment;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "user_attestation_path")
    private String userAttestationPath;

}