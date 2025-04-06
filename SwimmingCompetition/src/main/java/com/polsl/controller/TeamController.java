package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Team;
import com.polsl.repository.TeamRepository;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    
    @GetMapping
    public Iterable<Team> getAll() {
        return teamRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Team getById(@PathVariable Long id) {
        return teamRepository.findById(id).orElse(null);
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
