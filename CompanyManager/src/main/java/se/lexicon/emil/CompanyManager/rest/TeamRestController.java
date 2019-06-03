package se.lexicon.emil.CompanyManager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.emil.CompanyManager.entities.Team;
import se.lexicon.emil.CompanyManager.forms.TeamForm;
import se.lexicon.emil.CompanyManager.service.TeamService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/team")
public class TeamRestController {

    private TeamService teamService;

    @Autowired
    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> findAll() {
        List<Team> teams = teamService.findAll();

        if (teams.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(teams);
    }

    @GetMapping(params = "teamId")
    public ResponseEntity<Team> findById(@RequestParam int teamId) {
        return ResponseEntity.ok(teamService.findById(teamId));
    }

    @GetMapping(params = "leaderId")
    public ResponseEntity<List<Team>> findByLeader(@RequestParam int leaderId) {
        return ResponseEntity.ok(teamService.findByLeader(leaderId));
    }

    @GetMapping(params = "departmentId")
    public ResponseEntity<List<Team>> findByDepartment(@RequestParam int departmentId) {
        return ResponseEntity.ok(teamService.findByDepartment(departmentId));
    }

    @PostMapping("/change")
    public ResponseEntity changeDepartment(@RequestBody TeamForm teamForm) {
        teamService.changeDepartment(teamForm.departmentId, teamForm.teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity assignEmployees(@RequestBody TeamForm teamForm) throws IllegalAccessException {
        teamService.assignEmployees(teamForm.teamId, teamForm.employeeIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unassign")
    public ResponseEntity unassignEmployees(@RequestBody TeamForm teamForm) throws IllegalAccessException {
        teamService.unassignEmployees(teamForm.teamId, teamForm.employeeIds);
        return ResponseEntity.ok().build();
    }
}
