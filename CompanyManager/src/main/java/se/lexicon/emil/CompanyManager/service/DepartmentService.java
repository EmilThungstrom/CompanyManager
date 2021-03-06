package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    List<Department> findByNameContaining(String name);

    List<Department> findByHeadId(int employeeId);

    Department findById(int id);

    Department createDepartment(String departmentName);

    void deleteDepartment(int id);

    void assignEmployees(int departmentId, int[] employeeIds);

    void deleteEmployees(int departmentId, int[] employeeIds) throws IllegalAccessException;

    void addTeam(int departmentId);

    void deleteTeam(int departmentId, int teamId) throws IllegalAccessException;
}
