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

import java.util.ArrayList;
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
    public void assignEmployees(int teamId, int[] employeesIds) throws IllegalAccessException {
        Team team = getTeam(teamId);
        Department department = team.getDepartment();

        List<Employee> employees = new ArrayList<>();

        for(int id : employeesIds){
            Employee employee = getEmployee(id);

            if(!department.equals(employee.getDepartment()))
                throw new IllegalAccessException("Team department do not match specified employee's department");

            employee.setTeam(team);
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
    }

    @Override
    public void unassignEmployees(int teamId, int[] employeesIds) throws IllegalAccessException {
        Team team = getTeam(teamId);
        List<Employee> employees = new ArrayList<>();

        for(int id : employeesIds){
            Employee employee = getEmployee(id);

            if(!team.equals(employee.getTeam()))
                throw new IllegalAccessException("Employee with id " + id + " do not belong to the specified team");

            employee.setTeam(null);
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
    }
}
