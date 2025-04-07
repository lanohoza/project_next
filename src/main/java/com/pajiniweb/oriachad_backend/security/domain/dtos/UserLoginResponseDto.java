package com.pajiniweb.oriachad_backend.security.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
    private String token;
    private String type;

}
