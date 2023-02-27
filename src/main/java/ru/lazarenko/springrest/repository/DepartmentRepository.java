package ru.lazarenko.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lazarenko.springrest.entity.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from Department d left join fetch d.employees")
    List<Department> findAllDepartments();

}
