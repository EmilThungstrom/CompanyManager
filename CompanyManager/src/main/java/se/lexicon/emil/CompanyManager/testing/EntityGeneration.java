package se.lexicon.emil.CompanyManager.testing;

import se.lexicon.emil.CompanyManager.entities.Department;
import se.lexicon.emil.CompanyManager.entities.Employee;
import se.lexicon.emil.CompanyManager.entities.Team;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EntityGeneration {

    private Random random = new Random();
    private List<String> names;
    private List<String> surnames;
    private List<String> places;

    public EntityGeneration() throws IOException {
        names = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/names.txt").getFile())).collect(Collectors.toList());
        surnames = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/surnames.txt").getFile())).collect(Collectors.toList());
        places = Files.lines(Paths.get(getClass().getClassLoader().getResource("data/places.txt").getFile())).collect(Collectors.toList());
    }

    public Department createDepartment(String name){
        Department department = new Department(name, null);
        Employee head = createEmployee(department, null);
        department.setHead(head);

        List<Team> teams = new LinkedList<>();
        List<Employee> employees = new LinkedList<>();
        for(int i = 0; i < random.nextInt(4)+1; i++){
            teams.add(createTeam(department, random.nextInt(9)+1));

            for(Employee employee : teams.get(i).getMembers()){
                employees.add(employee);
            }
        }
        department.setTeams(teams);
        department.setEmployees(employees);

        return department;
    }

    public Team createTeam(Department department, int size){
        Employee leader = createEmployee(department, null);
        Team team = new Team(department, leader);
        leader.setTeam(team);

        List<Employee> employees = new ArrayList<>();
        for(int i = 0; i < size; i++){
            employees.add(createEmployee(department, team));
        }
        team.setMembers(employees);

        return team;
    }

    public Employee createEmployee(Department department, Team team) {
        String name = names.get(random.nextInt(names.size()));
        String surname = surnames.get(random.nextInt(surnames.size()));
        String address = places.get(random.nextInt(surnames.size())).concat(" " + random.nextInt(100));

        name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
        surname = surname.replaceFirst(surname.substring(0, 1), surname.substring(0, 1).toUpperCase());
        address = address.replaceFirst(address.substring(0, 1), address.substring(0, 1).toUpperCase());

        return new Employee(name, surname, address
                , name + "." + surname.replaceAll(" ", "") + "@" + department.getName().replaceAll(" ", "") + ".net"
                , team, department);
    }
}
