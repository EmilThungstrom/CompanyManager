package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        List<Employee> departments = new ArrayList<>();
        employeeRepository.findAll().forEach(department -> departments.add(department));
        return departments;
    }

    @Override
    public List<Employee> findByFirstName(String name) {
        return null;
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id).get();
    }
}
