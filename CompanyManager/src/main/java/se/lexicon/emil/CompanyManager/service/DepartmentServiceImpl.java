package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
}
