package ru.lazarenko.springrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;
import ru.lazarenko.springrest.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional
    public void saveDepartment(Department department){
        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartment(){
        return departmentRepository.findAllDepartments();
    }

    @Transactional(readOnly = true)
    public Department getDepartmentById(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Department with id='%d' not found".formatted(id)));
    }

    @Transactional
    public void updateDepartment(Integer id, Department department){
        Department updatableDepartment = getDepartmentById(id);
        updatableDepartment.setTitle(department.getTitle());

        departmentRepository.save(updatableDepartment);
    }

    @Transactional
    public void deleteDepartment(Integer id){
        departmentRepository.deleteById(id);
    }

    @Transactional
    public void transferAllEmployees(Integer idFromDepartment, Integer idToDepartment) {
        Department fromDepartment = getDepartmentById(idFromDepartment);
        Department toDepartment = getDepartmentById(idToDepartment);

        for (Employee employee : fromDepartment.getEmployees()) {
            toDepartment.addEmployee(employee);
        }

        fromDepartment.setEmployees(new ArrayList<>());

        departmentRepository.save(fromDepartment);
        departmentRepository.save(toDepartment);
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartmentId(Integer id) {
        return departmentRepository.findEmployeesByDepartmentId(id);
    }

    @Transactional
    public void addEmployeeByDepartmentId(Employee employee, Integer idDepartment) {
        Department updatableDepartment = getDepartmentById(idDepartment);

        updatableDepartment.addEmployee(employee);
        departmentRepository.save(updatableDepartment);
    }

    @Transactional
    public void deleteEmployeeByDepartmentId(Integer employeeId, Integer idDepartment) {
        departmentRepository.deleteEmployeeById(employeeId, idDepartment);
    }
}
