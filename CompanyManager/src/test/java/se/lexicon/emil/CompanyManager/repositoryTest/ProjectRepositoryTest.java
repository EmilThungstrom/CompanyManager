package se.lexicon.emil.CompanyManager.repositoryTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Project;
import se.lexicon.emil.CompanyManager.entity.Team;
import se.lexicon.emil.CompanyManager.repositories.ProjectRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TeamRepository teamRepository;

    private Project testProject;
    private List<Project> testProjectsBefore = new ArrayList<>();
    private List<Project> testProjectsAfter = new ArrayList<>();
    private Team testTeam;
    private LocalDate testCreationDate;
    private LocalDate testFinnishDate;

    @Before
    public void init(){

        Team team1 = new Team( null, null, null);
        Team team2 = new Team( null, null, null);
        Team team3 = new Team( null, null, null);

        testTeam = team1;

        team1 = teamRepository.save(team1);
        team2 = teamRepository.save(team2);
        team3 = teamRepository.save(team3);

        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = LocalDate.now().plusDays(1);
        LocalDate beforeLocalDate1 = LocalDate.now().minusDays(1);
        LocalDate beforeLocalDate2 = LocalDate.now().minusDays(2);
        LocalDate afterLocalDate1 = LocalDate.now().plusDays(2);
        LocalDate afterLocalDate2 = LocalDate.now().plusDays(3);
        LocalDate beforeLocalDate3 = LocalDate.now().minusDays(3);
        LocalDate beforeLocalDate4 = LocalDate.now().minusDays(4);
        LocalDate afterLocalDate3 = LocalDate.now().plusDays(4);
        LocalDate afterLocalDate4 = LocalDate.now().plusDays(5);

        testCreationDate = localDate1;
        testFinnishDate = localDate2;

        Project project1 = new Project("project1", team1, localDate1, localDate2, true);
        Project beforeProject1 = new Project("beforeProject1", team2, beforeLocalDate1, beforeLocalDate2, false);
        Project afterProject1 = new Project("afterProject1", team3, afterLocalDate1, afterLocalDate2, true);
        Project beforeProject2 = new Project("beforeProject2", team3, beforeLocalDate3, beforeLocalDate4, true);
        Project afterProject2 = new Project("afterProject2", team3, afterLocalDate3, afterLocalDate4, true);

        testProject = projectRepository.save(project1);
        testProjectsBefore.add(projectRepository.save(beforeProject1));
        testProjectsBefore.add(projectRepository.save(beforeProject2));
        testProjectsAfter.add(projectRepository.save(afterProject1));
        testProjectsAfter.add(projectRepository.save(afterProject2));
    }

    @Test
    public void test_GetByName(){
        List<Project> expected = Arrays.asList(testProject);
        List<Project> actual = projectRepository.findByName("project1");
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByTeam(){
        List<Project> expected = Arrays.asList(testProject);
        List<Project> actual = projectRepository.findByTeam(testTeam);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByCreationDate(){
        List<Project> expected = Arrays.asList(testProject);
        List<Project> actual = projectRepository.findByCreationDate(testCreationDate);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByCreationDateBefore(){
        List<Project> expected = testProjectsBefore;
        List<Project> actual = projectRepository.findByCreationDateBefore(testCreationDate);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByCreationDateAfter(){
        List<Project> expected = testProjectsAfter;
        List<Project> actual = projectRepository.findByCreationDateAfter(testCreationDate);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByFinnishDate(){
        List<Project> expected = Arrays.asList(testProject);
        List<Project> actual = projectRepository.findByFinnishDate(testFinnishDate);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByFinnishDateBefore(){
        List<Project> expected = testProjectsBefore;
        List<Project> actual = projectRepository.findByFinnishDateBefore(testCreationDate);
        assertEquals(expected, actual);
    }

    @Test
    public void test_GetByFinnishDateAfter(){
        List<Project> expected = testProjectsAfter;
        List<Project> actual = projectRepository.findByFinnishDateAfter(testFinnishDate);
        assertEquals(expected, actual);
    }
}
