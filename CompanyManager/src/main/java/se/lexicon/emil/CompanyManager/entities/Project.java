package se.lexicon.emil.CompanyManager.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER
    )
    private Set<Client> clients = new TreeSet<>();

    @ManyToOne
    private Team team;

    private String name;
    private LocalDate creationDate;
    private LocalDate finnishDate;
    private boolean active;

    public Project(String name, Team team, LocalDate creationDate, LocalDate finnishDate, boolean active) {
        this.name = name;
        this.team = team;
        this.creationDate = creationDate;
        this.finnishDate = finnishDate;
        this.active = active;
    }

    protected Project(){

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

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getFinnishDate() {
        return finnishDate;
    }

    public void setFinnishDate(LocalDate finnishDate) {
        this.finnishDate = finnishDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return active == project.active &&
                clients.equals(project.clients) &&
                Objects.equals(team, project.team) &&
                Objects.equals(name, project.name) &&
                Objects.equals(creationDate, project.creationDate) &&
                Objects.equals(finnishDate, project.finnishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients, team, name, creationDate, finnishDate, active);
    }
}
