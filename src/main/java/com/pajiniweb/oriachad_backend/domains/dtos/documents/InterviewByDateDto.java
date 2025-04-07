package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewByDateDto {
    private LocalDateTime createdDate;
    private List<InterviewDto> interviews;
}