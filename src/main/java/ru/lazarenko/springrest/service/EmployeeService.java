package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.dto.*;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Transactional
    public void saveEmployee(EmployeeAdditionRequest request) {
        Employee employee = new Employee();

        employee.setEmail(request.getEmail());
        employee.setName(request.getName());

        Integer departmentId = request.getDepartmentId();

        if(departmentId != null) {
            Optional<Department> departmentOptional = departmentService.getDepartmentOptionalById(departmentId);

            if(departmentOptional.isPresent()){
                employee.setDepartment(departmentOptional.get());
            }
        }

        employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getName(), employee.getEmail()));
        }

        return employeeDtos;
    }

    @Transactional(readOnly = true)
    public EmployeeDepartmentDto getEmployeeDtoById(Integer id) {
        Employee employeeDto = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(id)));

        return new EmployeeDepartmentDto(employeeDto.getName(), employeeDto.getEmail(),
                employeeDto.getDepartment().getTitle());
    }

    @Transactional
    public OperationResultDto updateEmployee(Integer id, EmployeeAdditionRequest request) {
        Optional<Employee> employeeOptional = getOptionalEmployeeById(id);

        if(employeeOptional.isEmpty()){
            return new OperationResultDto(false, "Updatable employee by id='%s' not found".formatted(id));
        }
        Employee updatableEmployee = employeeOptional.get();

        updatableEmployee.setName(request.getName());
        updatableEmployee.setEmail(request.getEmail());


        Integer departmentId = request.getDepartmentId();
        if (departmentId != null) {
            Optional<Department> departmentOptional = departmentService.getDepartmentOptionalById(departmentId);
            if (departmentOptional.isPresent()) {
                updatableEmployee.setDepartment(departmentOptional.get());
            }
        }
        employeeRepository.save(updatableEmployee);
        return new OperationResultDto(true, "Employee updated");
    }

    @Transactional
    public OperationResultDto deleteEmployee(Integer id) {
        Optional<Employee> employeeOptional = getOptionalEmployeeById(id);
        if (employeeOptional.isEmpty()) {
            return new OperationResultDto(false, "Employee not found");
        }

        employeeRepository.deleteById(id);
        return new OperationResultDto(true, "Employee deleted");
    }

    @Transactional
    public OperationResultDto addToDepartment(EmployeeAdditionRequest request) {
        Integer employeeId = request.getEmployeeId();

        Employee employee;
        if (employeeId == null) {
            employee = new Employee();
            employee.setEmail(request.getEmail());
            employee.setName(request.getName());
        } else {
            Optional<Employee> employeeOptional = getOptionalEmployeeById(employeeId);
            if (employeeOptional.isEmpty()) {
                return new OperationResultDto(false, "Employee not found");
            }
            employee = employeeOptional.get();
        }

        Integer departmentId = request.getDepartmentId();

        Department department;
        if (employeeId == null) {
            department = new Department();
            department.setTitle(request.getDepartmentTitle());
        } else {
            Optional<Department> departmentOptional = departmentService.getDepartmentOptionalById(departmentId);
            if (departmentOptional.isEmpty()) {
                return new OperationResultDto(false, "Department not found");
            }
            department = departmentOptional.get();
        }

        employee.setDepartment(department);
        employeeRepository.save(employee);
        return new OperationResultDto(true, "Operation confirmed");
    }

    private Optional<Employee> getOptionalEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }
}