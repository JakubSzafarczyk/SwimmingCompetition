package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.TeamDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Competitor;
import com.polsl.entity.Team;
import com.polsl.repository.TeamRepository;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    
    @GetMapping("/{id}/coaches")
    public @ResponseBody Iterable<Coach> getCoachesForTeam(@PathVariable Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    return team.getCoaches();
    }
    
    @GetMapping("/{id}/competitors")
    public @ResponseBody Iterable<Competitor> getCompetitorsForTeam(@PathVariable Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    return team.getCompetitors();
    }
    
    @GetMapping("/{id}")
    public @ResponseBody TeamDTO getTeam(@PathVariable Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    return new TeamDTO(team);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<TeamDTO> getAllTeams() {
    List<TeamDTO> teamsDTO =
    StreamSupport.stream(teamRepository.findAll().spliterator(), false)
    .map(TeamDTO::new).collect(Collectors.toList());
    return CollectionModel.of(teamsDTO);
    }
    
    @PostMapping
    public Team create(@RequestBody Team team) {
        return teamRepository.save(team);
    }
    
    @PutMapping("/{id}")
    public Team update(@PathVariable Long id, @RequestBody Team teamDetails) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            team.setName(teamDetails.getName());
            team.setHeadquarters(teamDetails.getHeadquarters());
            return teamRepository.save(team);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamRepository.deleteById(id);
    }
}
