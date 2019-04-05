package se.lexicon.emil.CompanyManager.entity;

import com.fasterxml.jackson.annotation.JsonView;
import se.lexicon.emil.CompanyManager.Filter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Filter.BaseData.class)
    private int id;

    @ManyToOne
    private Department department;

    @OneToOne
    private Employee leader;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY,
            mappedBy = "team"
    )
    private List<Employee> members = new LinkedList<>();

    @OneToOne
    private Project mainProject;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER
    )
    private List<Project> oldProjects = new LinkedList<>();

    public Team(Department department, Employee leader, Project mainProject) {
        this.department = department;
        this.leader = leader;
        this.mainProject = mainProject;
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

    public Project getMainProject() { return mainProject; }

    public void setMainProject(Project mainProject) {
        this.mainProject = mainProject;
    }

    public List<Project> getOldProjects() {
        return oldProjects;
    }

    public void setOldProjects(List<Project> oldProjects) {
        this.oldProjects = oldProjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return  Objects.equals(department, team.department) &&
                Objects.equals(leader, team.leader) &&
                members.equals(team.members) &&
                Objects.equals(mainProject, team.mainProject) &&
                oldProjects.equals(team.oldProjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash( department, leader, members, mainProject, oldProjects);
    }
}
