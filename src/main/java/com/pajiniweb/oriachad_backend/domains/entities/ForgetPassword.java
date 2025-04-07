package com.pajiniweb.oriachad_backend.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forget_password")
public class ForgetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "otp", nullable = false)
    private Integer otp;

    @Column(name = "expirationTime", nullable = false)
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private OriachadUser User;
}
