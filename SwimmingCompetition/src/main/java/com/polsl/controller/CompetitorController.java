package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionGetDTO;
import com.polsl.dto.CompetitorPostDTO;
import com.polsl.dto.CompetitorPutDTO;
import com.polsl.dto.CompetitorGetDTO;
import com.polsl.dto.ResultGetDTO;
import com.polsl.dto.TeamGetDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Competitor;
import com.polsl.entity.Result;
import com.polsl.entity.Team;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.ResultRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/competitors")
public class CompetitorController {

    @Autowired
    private CompetitorRepository competitorRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private ResultRepository resultRepository;
    
    @Autowired
    private CompetitionRepository competitionRepository;

    @GetMapping("/{id}/team")
    public @ResponseBody TeamGetDTO getTeamForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new TeamGetDTO(competitor.getTeam());
    }
    
    @GetMapping("/{id}/results")
    public @ResponseBody CollectionModel<ResultGetDTO> getResultsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<ResultGetDTO> results = competitor.getResults().stream()
    			.map(ResultGetDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(results);
    }
    
    @GetMapping("/{id}/competitions")
    public @ResponseBody CollectionModel<CompetitionGetDTO> getCompetitionsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<CompetitionGetDTO> competitions = competitor.getCompetitions().stream()
    			.map(CompetitionGetDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitions);
    }
 
    @GetMapping("/{id}")
    public @ResponseBody CompetitorGetDTO getCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new CompetitorGetDTO(competitor);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CompetitorGetDTO> getAllCompetitors() {
    	List<CompetitorGetDTO> competitorsDTO =
    			StreamSupport.stream(competitorRepository.findAll().spliterator(), false)
    			.map(CompetitorGetDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitorsDTO);
    }
    
    @PostMapping
    public CompetitorGetDTO create(@Valid @RequestBody CompetitorPostDTO dto) {
        Competitor competitor = new Competitor();
        competitor.setFirstName(dto.getFirstName());
        
        if (dto.getSecondName() != null) {
        	competitor.setSecondName(dto.getSecondName());
        }
        
        competitor.setLastName(dto.getLastName());
        competitor.setDateOfBirth(dto.getDateOfBirth());
        competitor.setGender(dto.getGender());
        competitor.setNationality(dto.getNationality());

        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + dto.getTeamId()));
            competitor.setTeam(team);
        }

        if (dto.getResultIds() != null) {
            Set<Result> results = dto.getResultIds().stream()
                    .map(resultId -> resultRepository.findById(resultId)
                            .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + resultId)))
                    .collect(Collectors.toSet());
            competitor.setResults(results);
        }

        if (dto.getCompetitionIds() != null) {
            Set<Competition> competitions = dto.getCompetitionIds().stream()
                    .map(competitionId -> competitionRepository.findById(competitionId)
                            .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + competitionId)))
                    .collect(Collectors.toSet());
            competitor.setCompetitions(competitions);
        }

        Competitor saved = competitorRepository.save(competitor);
        return new CompetitorGetDTO(saved);
    }

    
    @PutMapping("/{id}")
    public CompetitorGetDTO update(@PathVariable Long id, @Valid @RequestBody CompetitorPutDTO dto) {
        Competitor competitor = competitorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + id));

        Team team = teamRepository.findById(dto.getTeamId())
            .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + dto.getTeamId()));

        Set<Result> results = dto.getResultIds().stream()
            .map(resultId -> resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + resultId)))
            .collect(Collectors.toSet());

        Set<Competition> competitions = dto.getCompetitionIds().stream()
            .map(competitionId -> competitionRepository.findById(competitionId)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + competitionId)))
            .collect(Collectors.toSet());

        competitor.setFirstName(dto.getFirstName());
        competitor.setSecondName(dto.getSecondName());
        competitor.setLastName(dto.getLastName());
        competitor.setDateOfBirth(dto.getDateOfBirth());
        competitor.setGender(dto.getGender());
        competitor.setNationality(dto.getNationality());
        competitor.setTeam(team);
        competitor.setResults(results);
        competitor.setCompetitions(competitions);

        Competitor updated = competitorRepository.save(competitor);
        return new CompetitorGetDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitorRepository.existsById(id)) {
            throw new EntityNotFoundException("Competitor not found with id " + id);
        }
        competitorRepository.deleteById(id);
    }
}
