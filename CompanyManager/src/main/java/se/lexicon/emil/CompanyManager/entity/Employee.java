package se.lexicon.emil.CompanyManager.entity;

import com.fasterxml.jackson.annotation.JsonView;
import se.lexicon.emil.CompanyManager.Filter;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Filter.BaseData.class)
    private int id;

    @JsonView(Filter.BaseData.class)
    private String firstName;
    @JsonView(Filter.BaseData.class)
    private String lastName;
    @JsonView(Filter.BaseData.class)
    private String address;
    @JsonView(Filter.BaseData.class)
    private String email;

    @ManyToOne
    @JsonView(Filter.EmployeeData.class)
    private Team team;

    @ManyToOne
    @JsonView(Filter.EmployeeData.class)
    private Department department;

    public Employee(String firstName, String lastName, String address, String email, Team team, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.team = team;
        this.department = department;
    }

    public Employee(){

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(team, employee.team) &&
                Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, email, team, department);
    }
}
