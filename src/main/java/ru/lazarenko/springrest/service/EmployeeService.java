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
    public Response saveEmployee(EmployeeAdditionRequest request) {
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
        return new Response(true, "Employee saved");
    }

    @Transactional(readOnly = true)
    public List<EmployeeWithoutDepartmentDto> getAllEmployees() {
        List<EmployeeWithoutDepartmentDto> responseList = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            responseList.add(new EmployeeWithoutDepartmentDto(employee.getName(), employee.getEmail()));
        }

        return responseList;
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Integer id) {
        Employee employee = getOrThrowEmployeeById(id);

        return new EmployeeDto(employee.getName(), employee.getEmail(), employee.getDepartment().getTitle());
    }

    @Transactional
    public Response updateEmployee(Integer id, EmployeeAdditionRequest request) {
        Optional<Employee> employeeOptional = getOptionalEmployeeById(id);

        if(employeeOptional.isEmpty()){
            return new Response(false, "Updatable employee by id='%s' not found".formatted(id));
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
        return new Response(true, "Employee updated");
    }

    @Transactional
    public Response deleteEmployee(Integer id) {
        Optional<Employee> employeeOptional = getOptionalEmployeeById(id);
        if (employeeOptional.isEmpty()) {
            return new Response(false, "Employee not found");
        }

        employeeRepository.deleteById(id);
        return new Response(true, "Employee deleted");
    }

    @Transactional
    public Response addToDepartment(EmployeeAdditionRequest request) {
        Integer employeeId = request.getEmployeeId();

        Employee employee;
        if (employeeId == null) {
            employee = new Employee();
            employee.setEmail(request.getEmail());
            employee.setName(request.getName());
        } else {
            Optional<Employee> employeeOptional = getOptionalEmployeeById(employeeId);
            if (employeeOptional.isEmpty()) {
                return new Response(false, "Employee not found");
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
                return new Response(false, "Department not found");
            }
            department = departmentOptional.get();
        }
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return new Response(true, "Operation confirmed");
    }

    private Employee getOrThrowEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Employee with id='%d' not found".formatted(employeeId)));
    }

    private Optional<Employee> getOptionalEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
