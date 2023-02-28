package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.repository.EmployeeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Transactional
    public void saveEmployee(Employee employee){
        Department department = employee.getDepartment();
        if(department != null){
            department = departmentService.getDepartmentById(department.getId());
            department.addEmployee(employee);
            departmentService.updateDepartment(department.getId(), department);
            employee.setDepartment(department);
        }
//        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAllEmployees();
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Integer id){
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(id)));
    }

    @Transactional
    public void updateEmployee(Integer id, Employee employee){
        Employee updatableEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(id)));

        Department department = departmentService.getDepartmentById(employee.getDepartment().getId());
        if(department != null){
            department = departmentService.getDepartmentById(department.getId());
        }

        updatableEmployee.setName(employee.getName());
        updatableEmployee.setEmail(employee.getEmail());
        updatableEmployee.setDepartment(department);

        employeeRepository.save(updatableEmployee);
    }

    @Transactional
    public void deleteEmployee(Integer id){
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartmentId(Integer id) {
        return employeeRepository.findAllEmployeesByDepartmentId(id);
    }


    @Transactional
    public void addEmployeeByDepartmentId(Employee employee, Integer departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        if(department != null){
            department = departmentService.getDepartmentById(department.getId());
            employee.setDepartment(department);
        }
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployeeByDepartmentId(Integer employeeId, Integer departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(employeeId)));

        Department department = departmentService.getDepartmentById(departmentId);
        if(department != null){
            department = departmentService.getDepartmentById(department.getId());
            department.deleteEmployee(employee);
            departmentService.updateDepartment(departmentId, department);
        }
        employee.setDepartment(null);
        employeeRepository.save(employee);
    }
}
