package org.example.LMSProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInDto {
    private Long id;
    @NotNull(message = "Поле 'name' не должно быть пустым.")
    @Size(min = 3, message = "Поле 'name' должно быть больше 2 символов")
    private String name;
    @NotNull(message = "Поле 'surname' не должно быть пустым.")
    @Size(min = 3, message = "Поле 'surname' должно быть больше 2 символов")
    private String surname;
}
