package ru.lazarenko.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springrest.entity.Department;
import ru.lazarenko.springrest.entity.Employee;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from Department d left join fetch d.employees")
    List<Department> findAllDepartments();

    @Query("select d.employees from Department d where d.id=:id")
    List<Employee> findEmployeesByDepartmentId(Integer id);

    @Modifying
//    @Query("delete from Employee e where e.department.id=:idDepartment and e.id =:idEmployee")
    @Query("update Employee e set e.department=null where e.department.id=:idDepartment and e.id =:idEmployee")
    void deleteEmployeeById(Integer idEmployee, Integer idDepartment);

}