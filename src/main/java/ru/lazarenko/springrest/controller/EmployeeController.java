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
    public List<EmployeeWithoutDepartmentDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping
    public Response saveEmployee(@RequestBody EmployeeAdditionRequest request) {
        return employeeService.saveEmployee(request);
    }

    @PutMapping("/{employeeId}")
    public Response updateEmployee(@PathVariable Integer employeeId,
                               @RequestBody EmployeeAdditionRequest request) {
        return employeeService.updateEmployee(employeeId, request);
    }

    @DeleteMapping("/{employeeId}")
    public Response deleteEmployee(@PathVariable Integer employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/add-to-department")
    public Response addToDepartment(@RequestBody EmployeeAdditionRequest request) {
        return employeeService.addToDepartment(request);
    }
}
