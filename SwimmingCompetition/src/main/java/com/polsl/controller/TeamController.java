package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachGetDTO;
import com.polsl.dto.CompetitorGetDTO;
import com.polsl.dto.TeamPostDTO;
import com.polsl.dto.TeamPutDTO;
import com.polsl.dto.TeamGetDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Competitor;
import com.polsl.entity.Team;
import com.polsl.repository.CoachRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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
    public @ResponseBody CollectionModel<CoachGetDTO> getCoachesForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        List<CoachGetDTO> coaches = team.getCoaches().stream()
                .map(CoachGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(coaches);
    }

    @GetMapping("/{id}/competitors")
    public @ResponseBody CollectionModel<CompetitorGetDTO> getCompetitorsForTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        List<CompetitorGetDTO> competitors = team.getCompetitors().stream()
                .map(CompetitorGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitors);
    }

    @GetMapping("/{id}")
    public @ResponseBody TeamGetDTO getTeam(@PathVariable Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        return new TeamGetDTO(team);
    }

    @GetMapping
    public @ResponseBody CollectionModel<TeamGetDTO> getAllTeams() {
        List<TeamGetDTO> teamsDTO =
                StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                        .map(TeamGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(teamsDTO);
    }

    @PostMapping
    public TeamGetDTO create(@Valid @RequestBody TeamPostDTO dto) {
    	Team team = new Team();
    	team.setName(dto.getName());
    	
    	if (dto.getHeadquarters() != null) {
    		team.setHeadquarters(dto.getHeadquarters());
    	}

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
        return new TeamGetDTO(saved);
    }

    @PutMapping("/{id}")
    public TeamGetDTO update(@PathVariable Long id, @Valid @RequestBody TeamPutDTO dto) {
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
        return new TeamGetDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found with id " + id);
        }
        teamRepository.deleteById(id);
    }
}
