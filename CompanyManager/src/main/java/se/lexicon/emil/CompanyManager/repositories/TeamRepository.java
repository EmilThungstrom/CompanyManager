package se.lexicon.emil.CompanyManager.repositories;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.entity.Project;
import se.lexicon.emil.CompanyManager.entity.Team;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    List<Team> findByLeader(Employee employee);
    List<Team> findByDepartment(Department department);
    List<Team> findByMainProject(Project project);

    List<Team> findByOldProjects(Project project);
}
