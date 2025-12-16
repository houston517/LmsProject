package org.example.lmsproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    private List<Schedule> schedules;

}
