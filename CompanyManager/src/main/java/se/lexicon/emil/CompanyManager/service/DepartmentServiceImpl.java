package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, TeamRepository teamRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Department> findAll() {
        //List<Department> departments = new ArrayList<>();
        //departmentRepository.findAll().forEach(department -> departments.add(department));
        //return departments;

        return (List<Department>) departmentRepository.findAll();
    }

    @Override
    public List<Department> findByNameContaining(String name) {
        return departmentRepository.findByNameContaining(name);
    }

    @Override
    public List<Department> findByHeadId(int employeeId) {
        return departmentRepository.findByHead(getEmployee(employeeId));
    }

    @Override
    public Department findById(int departmentId) {
        return getDepartment(departmentId);
    }

    @Override
    public Department createDepartment(String departmentName) {
        Department department = new Department(departmentName, null);

        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(int departmentId) {
        departmentRepository.delete(getDepartment(departmentId));
    }

    @Override
    public void assignEmployees(int departmentId, int[] employeeIds) {
        Department department = getDepartment(departmentId);

        for (int id : employeeIds) {
            Employee employee = getEmployee(id);
            employee.setDepartment(department);
        }
    }

    @Override
    public void deleteEmployees(int departmentId, int[] employeeIds) throws IllegalAccessException {
        Department department = getDepartment(departmentId);

        for (int id : employeeIds) {
            Employee employee = getEmployee(id);

            if (!employee.getDepartment().equals(department))
                throw new IllegalAccessException("Employee with id " + id + " does not belong to specified department");

            employeeRepository.delete(employee);
        }
    }

    @Override
    public void addTeam(int departmentId) {
        Department department = getDepartment(departmentId);
        Team team = new Team(department, null);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(int departmentId, int teamId) throws IllegalAccessException {
        Department department = getDepartment(departmentId);
        Team team = getTeam(teamId);

        if (!team.getDepartment().equals(department))
            throw new IllegalAccessException("Team with id " + teamId + " does not belong to specified department");

        teamRepository.delete(team);
    }
}
