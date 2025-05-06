package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachRequestDTO;
import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.TeamDTO;
import com.polsl.dto.TeamRequestDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Competitor;
import com.polsl.entity.Team;
import com.polsl.repository.CoachRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private CoachRepository coachRepository;
    
    @Autowired
    private CompetitorRepository competitorRepository;

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
    public TeamRequestDTO create(@RequestBody TeamDTO dto) {
    	Team team = new Team();
    	team.setName(dto.getName());
    	team.setHeadquarters(dto.getHeadquarters());

        if (dto.getCoachId() != null) {
            Set<Coach> coaches = dto.getCoachId().stream()
                    .map(coachIds -> coachRepository.findById(coachIds)
                            .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + coachIds)))
                    .collect(Collectors.toSet());
            team.setCoaches(coaches);
        }
        
        if (dto.getCompetitorId() != null) {
            Set<Competitor> competitors = dto.getCompetitorId().stream()
                    .map(competitorIds -> competitorRepository.findById(competitorIds)
                            .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + competitorIds)))
                    .collect(Collectors.toSet());
            team.setCompetitors(competitors);
        }

        Team saved = teamRepository.save(team);
        return new TeamRequestDTO(saved);
    }

    @PutMapping("/{id}")
    public TeamRequestDTO update(@PathVariable Long id, @RequestBody TeamDTO dto) {
    	Team team = teamRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));

    	Set<Coach> coaches = dto.getCoachId().stream()
                .map(coachIds -> coachRepository.findById(coachIds)
                        .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + coachIds)))
                .collect(Collectors.toSet());
    	
    	Set<Competitor> competitors = dto.getCompetitorId().stream()
                .map(competitorIds -> competitorRepository.findById(competitorIds)
                        .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + competitorIds)))
                .collect(Collectors.toSet());
    	 
    	team.setName(dto.getName());
    	team.setHeadquarters(dto.getHeadquarters());
    	team.setCoaches(coaches);
    	team.setCompetitors(competitors);

    	Team updated = teamRepository.save(team);
        return new TeamRequestDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found with id " + id);
        }
        teamRepository.deleteById(id);
    }
}
