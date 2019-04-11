package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.exceptions.EntityNotFoundException;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

public abstract class AbstractService {

    protected EmployeeRepository employeeRepository;
    protected TeamRepository teamRepository;
    protected DepartmentRepository departmentRepository;

    protected Employee getEmployee(int employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("No employee with id: " + employeeId + " exist."));
    }
    protected Department getDepartment(int departmentId){
        return departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("No department with id: " + departmentId + " exist."));
    }
    protected Team getTeam(int teamId){
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("No team with id: " + teamId + " exist."));
    }
}
