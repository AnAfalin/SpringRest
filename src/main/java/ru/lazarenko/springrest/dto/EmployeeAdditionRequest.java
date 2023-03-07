package ru.lazarenko.springrest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeAdditionRequest {
    private Integer employeeId;
    private String email;
    private String name;
    private Integer departmentId;
    private String departmentTitle;
}
