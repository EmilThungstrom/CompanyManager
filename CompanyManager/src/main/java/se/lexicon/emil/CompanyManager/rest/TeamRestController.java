package se.lexicon.emil.CompanyManager.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.emil.CompanyManager.Filter;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamRestController {

    TeamService teamService;

    @Autowired
    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @JsonView(Filter.TeamData.class)
    public ResponseEntity<List<Team>> findAll(){
        List<Team> teams = teamService.findAll();

        if(teams.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(teams);
    }

    @GetMapping(params = "teamId")
    @JsonView(Filter.TeamData.class)
    public ResponseEntity<Team> findById(@RequestParam int teamId){
        Team team = null;
        try{
            team = teamService.findById(teamId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(team);
    }

    @GetMapping(params = "leaderId")
    @JsonView(Filter.TeamData.class)
    public ResponseEntity<List<Team>> findByLeader(@RequestParam int leaderId){
        List<Team> team = null;
        try{
            team = teamService.findByLeader(leaderId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(team);
    }

    @GetMapping(params = "departmentId")
    @JsonView(Filter.TeamData.class)
    public ResponseEntity<List<Team>> findByDepartment(@RequestParam int departmentId){
        List<Team> team = null;
        try{
            team = teamService.findByDepartment(departmentId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(team);
    }
}
