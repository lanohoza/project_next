package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.FollowUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowupByDateDto {
    private LocalDateTime createdDate;
    private List<FollowUpDto> followUps;
}