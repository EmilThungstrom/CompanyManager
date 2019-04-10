package se.lexicon.emil.CompanyManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JsonIgnoreProperties({"employees", "teams", "head"})
    private Department department;

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JsonIgnoreProperties({"team", "department"})
    private Employee leader;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY,
            mappedBy = "team"
    )
    @JsonIgnoreProperties({"team", "department"})
    private List<Employee> members = new LinkedList<>();

    public Team(Department department, Employee leader) {
        this.department = department;
        this.leader = leader;
    }

    protected Team(){

    }

    public int getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public List<Employee> getMembers() { return members; }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(department, team.department) &&
                Objects.equals(leader, team.leader) &&
                members.equals(team.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, leader, members);
    }
}
