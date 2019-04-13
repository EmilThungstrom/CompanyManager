package se.lexicon.emil.CompanyManager.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;

import java.io.IOException;
import java.util.Random;

@Component
@Transactional
public class Startup {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Random random = new Random();
    private EntityGeneration entityGeneration = new EntityGeneration();

    public Startup() throws IOException {
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doAfterStartup() {
        Department hr = departmentRepository.save(new Department("Human Resources", null));
        Department dev = departmentRepository.save(new Department("Development", null));
        Department res = departmentRepository.save(new Department("Research", null));
        Department mark = departmentRepository.save(new Department("Marketing", null));
        Department maint = departmentRepository.save(new Department("Maintenance", null));

        Employee headHR = employeeRepository.save(entityGeneration.createEmployee(hr, null));
        Employee headDev = employeeRepository.save(entityGeneration.createEmployee(dev, null));
        Employee headRes = employeeRepository.save(entityGeneration.createEmployee(res, null));
        Employee headMark = employeeRepository.save(entityGeneration.createEmployee(mark, null));
        Employee headMaint = employeeRepository.save(entityGeneration.createEmployee(maint, null));

        hr.setHead(headHR);
        dev.setHead(headDev);
        res.setHead(headRes);
        mark.setHead(headMark);
        maint.setHead(headMaint);

        int[] maxRandomEmployees = {1, 25, 10, 15, 20};
        int[] minEmployees = {1, 50, 10, 5, 10};
        Department department = null;
        Team team = null;
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    department = hr;
                    break;
                case 1:
                    department = dev;
                    break;
                case 2:
                    department = res;
                    break;
                case 3:
                    department = mark;
                    break;
                case 4:
                    department = maint;
                    break;
            }
            team = createTeam(department);

            for (int j = 0, k = 0; j < minEmployees[i] + random.nextInt(maxRandomEmployees[i]); j++, k++) {
                Employee employee = employeeRepository.save(entityGeneration.createEmployee(department, team));
                if (k > 5 && random.nextBoolean()) {
                    team = createTeam(department);
                    k = 0;
                }
            }
        }
    }

    private Team createTeam(Department department) {
        Team team = teamRepository.save(new Team(department, null));
        Employee employee = employeeRepository.save(entityGeneration.createEmployee(department, team));
        team.setLeader(employee);
        return team;
    }
}
