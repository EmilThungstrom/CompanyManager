package se.lexicon.emil.CompanyManager.service;

import se.lexicon.emil.CompanyManager.entities.Team;

import java.util.List;

public interface TeamService {

    List<Team> findAll();
    List<Team> findByDepartment(int departmentId);
    List<Team> findByLeader(int leaderId);

    Team findById(int id);
}
