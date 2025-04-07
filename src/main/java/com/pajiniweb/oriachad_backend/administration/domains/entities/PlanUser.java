package com.pajiniweb.oriachad_backend.administration.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.administration.domains.enums.planPaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "plan_user")
public class PlanUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name = "plan_id", insertable = false , updatable = false , nullable = false)
    private Long idPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private OriachadUser user;

    @Column(name = "user_id", insertable = false, updatable = false , nullable = false)
    private Long idUser;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_debut_activation")
    private Date dateDebutActivation;

    @Column(name = "date_fin_activation")
    private Date dateFinActivation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    private planPaymentStatus paymentStatus;
}
