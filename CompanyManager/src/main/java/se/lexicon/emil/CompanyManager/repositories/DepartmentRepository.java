package se.lexicon.emil.CompanyManager.repositories;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    List<Department> findByNameContaining(String name);
    List<Department> findByHead(Employee head);

}
