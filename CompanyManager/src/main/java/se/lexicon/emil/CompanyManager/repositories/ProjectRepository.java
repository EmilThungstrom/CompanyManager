package se.lexicon.emil.CompanyManager.repositories;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.emil.CompanyManager.entity.Project;
import se.lexicon.emil.CompanyManager.entity.Team;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    List<Project> findByName(String name);
    List<Project> findByTeam(Team team);

    List<Project> findByCreationDate(LocalDate date);
    List<Project> findByCreationDateAfter(LocalDate date);
    List<Project> findByCreationDateBefore(LocalDate date);

    List<Project> findByFinnishDate(LocalDate date);
    List<Project> findByFinnishDateAfter(LocalDate date);
    List<Project> findByFinnishDateBefore(LocalDate date);
}
