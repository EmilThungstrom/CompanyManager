package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Team;

import java.util.List;

public interface TeamService {

    List<Team> findAll();

    List<Team> findByDepartment(int departmentId);

    List<Team> findByLeader(int leaderId);

    Team findById(int id);

    void changeDepartment(int departmentId, int teamId);

    void assignEmployees(int teamId, int[] employeesIds) throws IllegalAccessException;

    void unassignEmployees(int teamId, int[] employeesIds) throws IllegalAccessException;
}
