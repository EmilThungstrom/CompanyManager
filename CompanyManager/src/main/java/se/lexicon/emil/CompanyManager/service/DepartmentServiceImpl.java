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
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;
    private TeamRepository teamRepository;

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
    public List<Department> findByHead(Employee employee) {
        return departmentRepository.findByHead(employee);
    }

    @Override
    public Department findById(int id) {
        Optional<Department> result = departmentRepository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Department addDepartment(String departmentName) {
        Department department = new Department(departmentName, null);

        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(int id) {
        departmentRepository.delete(departmentRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void assignEmployees(int departmentId, int[] employeeIds) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalArgumentException::new);

        for(int id : employeeIds){
            Employee employee = employeeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            employee.setDepartment(department);
        }
    }

    @Override
    public void deleteEmployees(int departmentId, int[] employeeIds) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalArgumentException::new);

        for(int id : employeeIds){
            Employee employee = employeeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            if(!employee.getDepartment().equals(department))
                throw new IllegalAccessException();

            employeeRepository.delete(employee);
        }
    }

    @Override
    public void addTeam(int departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalArgumentException::new);
        Team team = new Team(department, null);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(int departmentId, int teamId) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalArgumentException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(IllegalArgumentException::new);

        if(!team.getDepartment().equals(department))
            throw new IllegalAccessException();

        teamRepository.delete(team);
    }
}
