package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.exceptions.EntityNotFoundException;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl extends AbstractService implements TeamService {

    @Autowired
    public TeamServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public List<Team> findByDepartment(int departmentId) {
        Department department = getDepartment(departmentId);
        return teamRepository.findByDepartment(department);
    }

    @Override
    public List<Team> findByLeader(int employeeId) {
        Employee employee = getEmployee(employeeId);
        return teamRepository.findByLeader(employee);
    }

    @Override
    public Team findById(int teamId) {
        return getTeam(teamId);
    }

    @Override
    public void changeDepartment(int departmentId, int teamId) {
        Department department = getDepartment(departmentId);
        Team team = getTeam(teamId);
        team.setDepartment(department);
        teamRepository.save(team);
    }

    @Override
    public void assignEmployees(int teamId, int[] employees) {

    }

    @Override
    public void unassignEmployees(int teamId, int[] employees) {

    }
}
