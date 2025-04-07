package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.projections.TCO001StudentGuidanceSpecialityAverage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TCO0001StudentCardDto {

    String previousLevelName;

    List<TCO001StudentGuidanceSpecialityAverage> guidanceSpecialityAverage;
    List<String> diagnostics;

}
