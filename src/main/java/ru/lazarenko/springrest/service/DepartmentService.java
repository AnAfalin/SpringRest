package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.dto.DepartmentDto;
import ru.lazarenko.springrest.dto.EmployeeDto;
import ru.lazarenko.springrest.dto.Response;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void saveDepartment(DepartmentDto departmentDto){
        Department department = new Department();
        department.setTitle(departmentDto.getTitle());
        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartment(){
        List<DepartmentDto> departmentsDto = new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();

        for (Department department : departments) {
            departmentsDto.add(new DepartmentDto(department.getTitle()));
        }
        return departmentsDto;
    }

    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Integer id){
        Optional<Department> departmentOptional = getDepartmentOptionalById(id);
        if(departmentOptional.isEmpty()){
            return null;
        }
        return new DepartmentDto(departmentOptional.get().getTitle());
    }

    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentOptionalById(Integer id){
        return departmentRepository.findById(id);
    }

    @Transactional
    public Response updateDepartment(Integer id, DepartmentDto departmentDto){
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        Department updatableDepartment;

        if(departmentOptional.isEmpty()){
            updatableDepartment = new Department();
            departmentRepository.save(updatableDepartment);
            return new Response(false, "Department by id='%s' not found. Department created".formatted(id));
        }else {
            updatableDepartment = departmentOptional.get();
            updatableDepartment.setTitle(departmentDto.getTitle());
        }
        departmentRepository.save(updatableDepartment);
        return new Response(true, "Department updated");
    }

    @Transactional
    public void deleteDepartment(Integer id){
        departmentRepository.deleteById(id);
    }

    @Transactional
    public void transferAllEmployees(Integer idFromDepartment, Integer idToDepartment) {
        Department fromDepartment = departmentRepository.findById(idFromDepartment)
                .orElseThrow(() -> new NoSuchElementException(
                        "Department with id='%d' not found".formatted(idFromDepartment)));
        Department toDepartment = departmentRepository.findById(idToDepartment)
                .orElseThrow(() -> new NoSuchElementException(
                        "Department with id='%d' not found".formatted(idToDepartment)));

        for (Employee employee : fromDepartment.getEmployees()) {
            toDepartment.addEmployee(employee);
        }

        fromDepartment.setEmployees(new ArrayList<>());

        departmentRepository.save(fromDepartment);
        departmentRepository.save(toDepartment);
    }

    // TODO в ответе не должно быть департамента
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByDepartmentId(Integer id) {
        List<Employee> employees = departmentRepository.findEmployeesByDepartmentId(id);
        List<EmployeeDto> employeesDto = new ArrayList<>();
        for (Employee employee : employees) {
            employeesDto.add(new EmployeeDto(employee.getName(), employee.getEmail(), employee.getDepartment().getTitle()));
        }
        return employeesDto;
    }

    @Transactional
    public Response addEmployeeByDepartmentId(EmployeeDto employeeDto, Integer idDepartment) {
        Optional<Department> departmentOptional = departmentRepository.findById(idDepartment);

        if(departmentOptional.isEmpty()){
            return new Response(false, "Department by id='%s' not found".formatted(idDepartment));
        }
        Department updatableDepartment = departmentOptional.get();

        Employee employee = new Employee();
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());

        updatableDepartment.addEmployee(employee);
        departmentRepository.save(updatableDepartment);

        return new Response(true, "Employee added in department with id='%s'".formatted(idDepartment));

    }

    @Transactional
    public void deleteEmployeeByDepartmentId(Integer employeeId, Integer idDepartment) {
        departmentRepository.deleteEmployeeById(employeeId, idDepartment);
    }

}
