package ru.lazarenko.springrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springrest.dto.DepartmentDto;
import ru.lazarenko.springrest.dto.EmployeeDto;
import ru.lazarenko.springrest.dto.Response;
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
    public List<DepartmentDto> getAllDepartments(){
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartmentById(@PathVariable Integer departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping
    public void saveDepartment(@RequestBody DepartmentDto departmentDto){
        departmentService.saveDepartment(departmentDto);
    }

    @PutMapping("/{departmentId}")
    public Response updateDepartment(@PathVariable Integer departmentId,
                                     @RequestBody DepartmentDto departmentDto){
        return departmentService.updateDepartment(departmentId, departmentDto);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(@PathVariable Integer departmentId){
        departmentService.deleteDepartment(departmentId);
    }

    @GetMapping("/{departmentId}/employees")
    public List<EmployeeDto> getEmployeesByDepartmentId(@PathVariable Integer departmentId){
        return departmentService.getEmployeesByDepartmentId(departmentId);
    }

    // TODO сделать через dto
    @PostMapping("/{departmentId}")
    public Response addEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Integer departmentId){
        return departmentService.addEmployeeByDepartmentId(employeeDto, departmentId);
    }

    // TODO сделать через dto
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
