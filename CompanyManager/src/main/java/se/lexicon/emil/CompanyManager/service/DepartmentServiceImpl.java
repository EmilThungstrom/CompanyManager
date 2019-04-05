package se.lexicon.emil.CompanyManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(department -> departments.add(department));
        return departments;
    }

    @Override
    public List<Department> findByNameContaining(String name) {
        return departmentRepository.findByNameContaining(name);
    }

    @Override
    public Department findById(int id) {
        return departmentRepository.findById(id).get();
    }
}
