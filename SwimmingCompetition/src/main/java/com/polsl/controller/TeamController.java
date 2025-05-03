package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachDTO;
import com.polsl.dto.CompetitorDTO;
import com.polsl.dto.TeamDTO;
import com.polsl.entity.Team;
import com.polsl.repository.TeamRepository;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    
    @GetMapping("/{id}/coaches")
    public @ResponseBody CollectionModel<CoachDTO> getCoachesForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        List<CoachDTO> coaches = team.getCoaches().stream()
            .map(CoachDTO::new).collect(Collectors.toList());
        return CollectionModel.of(coaches);
    }
    
    
    @GetMapping("/{id}/competitors")
    public @ResponseBody CollectionModel<CompetitorDTO> getCompetitorsForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id).orElse(null);
        List<CompetitorDTO> competitors = team.getCompetitors().stream()
            .map(CompetitorDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitors);
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
