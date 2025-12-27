package org.example.lmsproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleOutDto {
    private Long id;
    private String group;
    private String teacher;
    private String course;
    private String lessonDate;
}
