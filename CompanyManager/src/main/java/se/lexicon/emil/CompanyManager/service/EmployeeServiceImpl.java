package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.exceptions.EntityNotFoundException;
import se.lexicon.emil.CompanyManager.forms.EmployeeForm;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    TeamRepository teamRepository;
    DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, TeamRepository teamRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Employee> findAll() {
        //List<Employee> departments = new ArrayList<>();
        //employeeRepository.findAll().forEach(department -> departments.add(department));
        //return departments;

        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Employee findById(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("No employee with id: " + employeeId + " exist."));
    }

    @Override
    public List<Employee> findByForm(EmployeeForm employeeForm) {
        Stream<Employee> employeeStream = ((List<Employee>)employeeRepository.findAll()).stream();

        if(employeeForm.departmentId > 0)
            employeeStream = employeeStream.filter(employee -> employee.getDepartment().getId() == employeeForm.departmentId);
        if(employeeForm.teamId > 0)
            employeeStream = employeeStream.filter(employee -> employee.getTeam().getId() == employeeForm.teamId);
        if(employeeForm.getFirstName() != null && !employeeForm.getFirstName().isEmpty())
            employeeStream = employeeStream.filter(employee -> employee.getFirstName().equalsIgnoreCase(employeeForm.getFirstName()));
        if(employeeForm.getLastName() != null && !employeeForm.getLastName().isEmpty())
            employeeStream = employeeStream.filter(employee -> employee.getLastName().equalsIgnoreCase(employeeForm.getLastName()));
        if(employeeForm.getAddress() != null && !employeeForm.getAddress().isEmpty())
            employeeStream = employeeStream.filter(employee -> employee.getAddress().equalsIgnoreCase(employeeForm.getAddress()));
        if(employeeForm.getEmail() != null && !employeeForm.getEmail().isEmpty())
            employeeStream = employeeStream.filter(employee -> employee.getEmail().equalsIgnoreCase(employeeForm.getEmail()));

        return employeeStream.collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(EmployeeForm employeeForm) {

        Team team = null;
        if(employeeForm.teamId > 0)
            team = teamRepository.findById(employeeForm.teamId).orElseThrow(() -> new EntityNotFoundException("No team with id: " + employeeForm.teamId + " exist."));

        Department department = null;
        if(employeeForm.departmentId > 0)
            department = departmentRepository.findById(employeeForm.departmentId).orElseThrow(() -> new EntityNotFoundException("No department with id: " + employeeForm.departmentId + " exist."));

        Employee employee = new Employee(employeeForm.getFirstName(), employeeForm.getLastName(), employeeForm.getAddress(), employeeForm.getEmail(), team, department);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
