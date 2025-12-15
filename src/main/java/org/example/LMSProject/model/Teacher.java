package org.example.LMSProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST,  fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public Teacher(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getFullName(){
        return surname + " " + name;
    }
}
