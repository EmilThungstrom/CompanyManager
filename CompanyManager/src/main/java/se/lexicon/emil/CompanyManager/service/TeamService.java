package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    List<Team> findAll();
    List<Team> findByDepartment(Department department);
    List<Team> findByLeader(Employee employee);

    Team findById(int id);
}
