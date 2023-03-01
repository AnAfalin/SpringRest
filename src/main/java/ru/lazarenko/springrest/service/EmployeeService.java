package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.repository.EmployeeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Transactional
    public void saveEmployee(Employee employee) {
        Department department = employee.getDepartment();
        if (department != null && department.getId() != null) {
            department = departmentService.getDepartmentById(department.getId());
            employee.setDepartment(department);
        }
        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(id)));
    }

    @Transactional
    public void updateEmployee(Integer id, Employee employee) {
        Employee updatableEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(id)));

        updatableEmployee.setName(employee.getName());
        updatableEmployee.setEmail(employee.getEmail());

        Department department = employee.getDepartment();

        if (department != null && department.getId() != null) {
            department = departmentService.getDepartmentById(department.getId());

        }
        updatableEmployee.setDepartment(department);
        employeeRepository.save(updatableEmployee);
    }

    @Transactional
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
