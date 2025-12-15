package org.example.LMSProject.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "surname", "group"})
public class StudentOutDto {
    private Long id;
    private String name;
    private String surname;
    private String group;
}
