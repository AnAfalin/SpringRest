package ru.lazarenko.springrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.service.DepartmentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "second/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

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

    @GetMapping("/{departmentId}/employees")
    public List<Employee> getEmployeesByDepartmentId(@PathVariable Integer departmentId){
        return departmentService.getEmployeesByDepartmentId(departmentId);
    }

    @PostMapping("/{departmentId}")
    public void addEmployee(@RequestBody Employee employee, @PathVariable Integer departmentId){
        departmentService.addEmployeeByDepartmentId(employee, departmentId);
    }

    @DeleteMapping("/{departmentId}/employees/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId, @PathVariable Integer departmentId){
        departmentService.deleteEmployeeByDepartmentId(employeeId, departmentId);
    }


    @PutMapping("/departments/transfer/from/{deptFromId}/to/{deptToId}")
    public void transferAllEmployees(@PathVariable Integer deptFromId,
                                     @PathVariable Integer deptToId){
        departmentService.transferAllEmployees(deptFromId, deptToId);
    }


}
