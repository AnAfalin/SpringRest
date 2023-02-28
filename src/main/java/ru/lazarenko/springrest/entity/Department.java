package ru.lazarenko.springrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.GenerationType.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "department",
            cascade = {PERSIST, MERGE, DETACH, REFRESH},
            fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee){
        employee.setDepartment(this);
        employees.add(employee);
    }

    public void setEmployees(List<Employee> employees){
        for (Employee employee : employees) {
            employee.setDepartment(this);
        }
        this.employees = employees;
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee);
    }

}
