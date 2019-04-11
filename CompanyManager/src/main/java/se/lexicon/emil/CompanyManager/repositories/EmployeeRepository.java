package se.lexicon.emil.CompanyManager.repositories;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findByFirstNameIgnoreCase(String firstName);

    List<Employee> findByLastNameIgnoreCase(String lastName);

    List<Employee> findByAddressIgnoreCase(String address);

    List<Employee> findByEmailIgnoreCase(String email);

    List<Employee> findByTeam(Team team);

    List<Employee> findByDepartment(Department department);
}
