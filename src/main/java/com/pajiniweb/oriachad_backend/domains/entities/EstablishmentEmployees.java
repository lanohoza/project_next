package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishmentEmployees;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "establishment_employees")
public class EstablishmentEmployees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstName", nullable = false)
    String firstName;

    @Column(name = "lastName", nullable = false)
    String lastName;

    @Column(name = "phoneNumber", nullable = false, unique = true)
    String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEstablishmentEmployees type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private OriachadUser createdBy;

    @Column(name = "created_by", nullable = false, insertable = false, updatable = false)
    private Long idCreatedBy;
}
