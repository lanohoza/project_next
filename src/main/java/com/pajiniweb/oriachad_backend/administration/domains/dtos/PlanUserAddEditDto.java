package com.pajiniweb.oriachad_backend.administration.domains.dtos;

import com.pajiniweb.oriachad_backend.administration.domains.enums.planPaymentStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanUserAddEditDto {
    private Long id;
    private Long idPlan;
    private Long idUser;
    private Boolean isActive;
    private Date dateDebutActivation;
    private Date dateFinActivation;
    private planPaymentStatus paymentStatus;
}
