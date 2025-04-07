package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.TypeWeekProgram;

import java.util.Date;

public class WeekProgramDto {
    private Long id;
    private Date startWeek;
    private Date endWeek;
    private String urlDoc;
    private TypeWeekProgram type;
    private Integer idMonth;
    private Long idCreatedBy;
}
