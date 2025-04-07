package com.pajiniweb.oriachad_backend.security.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
