package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        //List<Employee> departments = new ArrayList<>();
        //employeeRepository.findAll().forEach(department -> departments.add(department));
        //return departments;

        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public List<Employee> findByFirstName(String name) {
        return null;
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }
}
