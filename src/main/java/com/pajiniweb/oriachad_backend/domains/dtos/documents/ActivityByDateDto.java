package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityByDateDto {
    private LocalDate createdDate;
    private List<ActivityDto> activities;
}