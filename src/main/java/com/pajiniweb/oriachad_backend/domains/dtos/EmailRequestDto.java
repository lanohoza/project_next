package com.pajiniweb.oriachad_backend.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailRequestDto implements Serializable {
    private MultipartFile[] files;
    private String to;
    @Builder.Default
    private String[] cc= new String[0];
    private String subject;
    private String body;

}
