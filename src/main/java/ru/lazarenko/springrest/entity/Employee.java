package ru.lazarenko.springrest.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Builder

@NoArgsConstructor  // конструктор без параметров
@AllArgsConstructor // конструктор со всеми полями
@Getter // геттеры
@Setter // сеттеры
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;


}
