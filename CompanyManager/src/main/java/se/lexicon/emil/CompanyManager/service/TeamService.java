package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;

import java.util.List;

public interface TeamService {

    List<Team> findAll();
    List<Team> findByDepartment(Department department);
    List<Team> findByLeader(Employee employee);

    Team findById(int id);
}
