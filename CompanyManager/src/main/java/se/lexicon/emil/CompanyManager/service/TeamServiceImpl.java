package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.util.List;

public class TeamServiceImpl implements TeamService {

    TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public List<Team> findByDepartment(Department department) {
        return teamRepository.findByDepartment(department);
    }

    @Override
    public List<Team> findByLeader(Employee employee) {
        return teamRepository.findByLeader(employee);
    }

    @Override
    public Team findById(int id) {
        return teamRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
