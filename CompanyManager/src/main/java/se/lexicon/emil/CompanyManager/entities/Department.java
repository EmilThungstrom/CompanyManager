package se.lexicon.emil.CompanyManager.entities;

import com.fasterxml.jackson.annotation.JsonView;
import se.lexicon.emil.CompanyManager.Filter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Filter.BaseData.class)
    private int id;

    @JsonView(Filter.BaseData.class)
    private String name;

    @OneToOne
    @JsonView(Filter.DepartmentData.class)
    private Employee head;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "department",
            orphanRemoval = true
    )
    @JsonView(Filter.DepartmentData.class)
    private List<Employee> employees = new LinkedList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "department",
            orphanRemoval = true
    )
    @JsonView(Filter.DepartmentData.class)
    private List<Team> teams = new LinkedList<>();

    public Department(String name, Employee head) {
        this.name = name;
        this.head = head;
    }

    protected Department(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(Employee head) {
        this.head = head;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(head, that.head) &&
                employees.equals(that.employees) &&
                teams.equals(that.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, head, employees, teams);
    }
}
