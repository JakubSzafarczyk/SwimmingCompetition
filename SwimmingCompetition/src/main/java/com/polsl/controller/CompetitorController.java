package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.CompetitorDTO;
import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.ResultRequestDTO;
import com.polsl.dto.TeamRequestDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Competitor;
import com.polsl.entity.Result;
import com.polsl.entity.Team;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.ResultRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;

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
    public @ResponseBody TeamRequestDTO getTeamForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new TeamRequestDTO(competitor.getTeam());
    }
    
    @GetMapping("/{id}/results")
    public @ResponseBody CollectionModel<ResultRequestDTO> getResultsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<ResultRequestDTO> results = competitor.getResults().stream()
    			.map(ResultRequestDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(results);
    }
    
    @GetMapping("/{id}/competitions")
    public @ResponseBody CollectionModel<CompetitionRequestDTO> getCompetitionsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<CompetitionRequestDTO> competitions = competitor.getCompetitions().stream()
    			.map(CompetitionRequestDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitions);
    }
 
    @GetMapping("/{id}")
    public @ResponseBody CompetitorRequestDTO getCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new CompetitorRequestDTO(competitor);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CompetitorRequestDTO> getAllCompetitors() {
    	List<CompetitorRequestDTO> competitorsDTO =
    			StreamSupport.stream(competitorRepository.findAll().spliterator(), false)
    			.map(CompetitorRequestDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitorsDTO);
    }
    
    @PostMapping
    public CompetitorRequestDTO create(@RequestBody CompetitorDTO dto) {
        Competitor competitor = new Competitor();
        competitor.setFirstName(dto.getFirstName());
        competitor.setSecondName(dto.getSecondName());
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
                    .map(id -> resultRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id)))
                    .collect(Collectors.toSet());
            competitor.setResults(results);
        }

        if (dto.getCompetitionIds() != null) {
            Set<Competition> competitions = dto.getCompetitionIds().stream()
                    .map(id -> competitionRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id)))
                    .collect(Collectors.toSet());
            competitor.setCompetitions(competitions);
        }

        Competitor saved = competitorRepository.save(competitor);
        return new CompetitorRequestDTO(saved);
    }

    
    @PutMapping("/{id}")
    public CompetitorRequestDTO update(@PathVariable Long id, @RequestBody CompetitorDTO dto) {
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
        return new CompetitorRequestDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitorRepository.existsById(id)) {
            throw new EntityNotFoundException("Competitor not found with id " + id);
        }
        competitorRepository.deleteById(id);
    }
}
