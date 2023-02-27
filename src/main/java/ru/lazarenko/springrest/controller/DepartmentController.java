package ru.lazarenko.springrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.service.DepartmentService;
import ru.lazarenko.springrest.service.EmployeeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "second/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{departmentId}")
    public Department getDepartmentById(@PathVariable Integer departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping
    public void saveDepartment(@RequestBody Department department){
        departmentService.saveDepartment(department);
    }

    @PutMapping("/{departmentId}")
    public void updateDepartment(@PathVariable Integer departmentId,
                               @RequestBody Department department){
        departmentService.updateDepartment(departmentId, department);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(@PathVariable Integer departmentId){
        departmentService.deleteDepartment(departmentId);
    }

    @GetMapping("/employees/{departmentId}")
    public List<Employee> getEmployeesByDepartmentId(@PathVariable Integer departmentId){
        return employeeService.getEmployeesByDepartmentId(departmentId);
    }

    @PostMapping("/{departmentId}")
    public void addEmployee(@RequestBody Employee employee, @PathVariable Integer departmentId){
        employeeService.addEmployeeByDepartmentId(employee, departmentId);
    }

    @DeleteMapping("/employee/{employeeId}/{departmentId}")
    public void deleteEmployee(@PathVariable Integer employeeId, @PathVariable Integer departmentId){
        employeeService.deleteEmployeeByDepartmentId(employeeId, departmentId);
    }


    @PutMapping("{departmentFromId}/{departmentToId}")
    public void transferAllEmployees(@PathVariable Integer departmentFromId,
                                     @PathVariable Integer departmentToId){
        departmentService.transferAllEmployees(departmentFromId, departmentToId);
    }


}
