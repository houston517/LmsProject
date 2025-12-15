package org.example.LMSProject.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"id", "name", "students"})
public class GroupOutDto {
    private Long id;
    private String name;
    private List<StudentOutDto> students;
}
