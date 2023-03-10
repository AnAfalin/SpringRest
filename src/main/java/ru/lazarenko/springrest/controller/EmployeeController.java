package ru.lazarenko.springrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springrest.dto.*;
import ru.lazarenko.springrest.service.EmployeeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "second/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeDepartmentDto getEmployeeById(@PathVariable Integer employeeId) {
        return employeeService.getEmployeeDtoById(employeeId);
    }

    @PostMapping
    public void saveEmployee(@RequestBody EmployeeAdditionRequest request) {
        employeeService.saveEmployee(request);
    }

    @PutMapping("/{employeeId}")
    public OperationResultDto updateEmployee(@PathVariable Integer employeeId,
                                             @RequestBody EmployeeAdditionRequest request) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @DeleteMapping("/{employeeId}")
    public OperationResultDto deleteEmployee(@PathVariable Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/add-to-department")
    public OperationResultDto addToDepartment(@RequestBody EmployeeAdditionRequest request) {
        return employeeService.addToDepartment(request);
    }
}
