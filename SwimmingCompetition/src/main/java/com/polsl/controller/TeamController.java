package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachRequestDTO;
import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.TeamRequestDTO;
import com.polsl.entity.Team;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/{id}/coaches")
    public @ResponseBody CollectionModel<CoachRequestDTO> getCoachesForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        List<CoachRequestDTO> coaches = team.getCoaches().stream()
                .map(CoachRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(coaches);
    }

    @GetMapping("/{id}/competitors")
    public @ResponseBody CollectionModel<CompetitorRequestDTO> getCompetitorsForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        List<CompetitorRequestDTO> competitors = team.getCompetitors().stream()
                .map(CompetitorRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitors);
    }

    @GetMapping("/{id}")
    public @ResponseBody TeamRequestDTO getTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        return new TeamRequestDTO(team);
    }

    @GetMapping
    public @ResponseBody CollectionModel<TeamRequestDTO> getAllTeams() {
        List<TeamRequestDTO> teamsDTO =
                StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                        .map(TeamRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(teamsDTO);
    }

    @PostMapping
    public Team create(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @PutMapping("/{id}")
    public Team update(@PathVariable Long id, @RequestBody Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        team.setName(teamDetails.getName());
        team.setHeadquarters(teamDetails.getHeadquarters());
        return teamRepository.save(team);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found with id " + id);
        }
        teamRepository.deleteById(id);
    }
}
