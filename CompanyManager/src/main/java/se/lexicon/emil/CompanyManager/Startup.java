package se.lexicon.emil.CompanyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.emil.CompanyManager.entity.Department;
import se.lexicon.emil.CompanyManager.entity.Employee;
import se.lexicon.emil.CompanyManager.entity.Team;
import se.lexicon.emil.CompanyManager.repositories.DepartmentRepository;
import se.lexicon.emil.CompanyManager.repositories.EmployeeRepository;
import se.lexicon.emil.CompanyManager.repositories.TeamRepository;
import se.lexicon.emil.CompanyManager.service.DepartmentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private List<String> names;
    private List<String> surnames;
    private List<String> places;

    public Startup() throws IOException {
        names = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/names.txt").getFile())).collect(Collectors.toList());
        surnames = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/surnames.txt").getFile())).collect(Collectors.toList());
        places = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/places.txt").getFile())).collect(Collectors.toList());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doAfterStartup(){
        Department hr = departmentRepository.save(new Department("Human Resources", null));
        Department dev = departmentRepository.save(new Department("Development", null));
        Department res = departmentRepository.save(new Department("Research", null));
        Department mark = departmentRepository.save(new Department("Marketing", null));
        Department maint = departmentRepository.save(new Department("Maintenance", null));

        Employee headHR = createEmployee(hr, null);
        Employee headDev = createEmployee(dev, null);
        Employee headRes = createEmployee(res, null);
        Employee headMark = createEmployee(mark, null);
        Employee headMaint = createEmployee(maint, null);

        hr.setHead(headHR);
        dev.setHead(headDev);
        res.setHead(headRes);
        mark.setHead(headMark);
        maint.setHead(headMaint);

        int[] maxRandomEmployees = {20, 25, 10, 15, 20};
        int[] minEmployees = {5, 50, 10, 5, 10};
        Department department = null;
        Team team = null;
        for(int i = 0; i < 5; i++){
            switch(i){
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

            for(int j = 0, k = 0; j < minEmployees[i] + random.nextInt(maxRandomEmployees[i]); j++, k++){
                Employee employee = createEmployee(department, team);
                if(k > 5 && random.nextBoolean()) {
                    team = createTeam(department);
                    k = 0;
                }
            }
        }
    }

    private Employee createEmployee(Department department, Team team){
        String name = names.get(random.nextInt(names.size()));
        String surname = surnames.get(random.nextInt(surnames.size()));
        String address = places.get(random.nextInt(surnames.size())).concat(" " + random.nextInt(100));

        name = name.replaceFirst(name.substring(0,1), name.substring(0,1).toUpperCase());
        surname = surname.replaceFirst(surname.substring(0,1), surname.substring(0,1).toUpperCase());

        return employeeRepository.save(
                new Employee(name, surname, address
                        , name + "." + surname + "@" + department.getName().replace(" ", "") + ".net"
                        , team, department));
    }

    private Team createTeam(Department department){
        Team team = teamRepository.save(new Team(department, null, null));
        Employee employee = createEmployee(department, team);
        team.setLeader(employee);
        return team;
    }
}
