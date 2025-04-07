package com.pajiniweb.oriachad_backend.domains.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
     List<ResultDto> results;
     AverageDto average;
     Long idStudent;

     Long idTrimestre;

}
