package org.example.lmsproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleInDto {
    private Long id;
    @NotNull(message = "Поле 'groupId' не должно быть пустым.")
    private Long groupId;
    @NotNull(message = "Поле 'teacherId' не должно быть пустым.")
    private Long teacherId;
    @NotNull(message = "Поле 'courseId' не должно быть пустым.")
    private Long courseId;
    private String lessonDate;
}
