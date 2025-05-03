package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionDTO;
import com.polsl.dto.CompetitorDTO;
import com.polsl.dto.ResultDTO;
import com.polsl.dto.TeamDTO;
import com.polsl.entity.Competitor;
import com.polsl.repository.CompetitorRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/competitors")
public class CompetitorController {

    @Autowired
    private CompetitorRepository competitorRepository;

    @GetMapping("/{id}/team")
    public @ResponseBody TeamDTO getTeamForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new TeamDTO(competitor.getTeam());
    }
    
    @GetMapping("/{id}/results")
    public @ResponseBody CollectionModel<ResultDTO> getResultsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<ResultDTO> results = competitor.getResults().stream()
    			.map(ResultDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(results);
    }
    
    @GetMapping("/{id}/competitions")
    public @ResponseBody CollectionModel<CompetitionDTO> getCompetitionsForCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	List<CompetitionDTO> competitions = competitor.getCompetitions().stream()
    			.map(CompetitionDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitions);
    }
 
    @GetMapping("/{id}")
    public @ResponseBody CompetitorDTO getCompetitor(@PathVariable Long id) {
    	Competitor competitor = competitorRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
    	return new CompetitorDTO(competitor);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CompetitorDTO> getAllCompetitors() {
    	List<CompetitorDTO> competitorsDTO =
    			StreamSupport.stream(competitorRepository.findAll().spliterator(), false)
    			.map(CompetitorDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitorsDTO);
    }
    
    @PostMapping
    public Competitor create(@RequestBody Competitor competitor) {
        return competitorRepository.save(competitor);
    }
    
    @PutMapping("/{id}")
    public Competitor update(@PathVariable Long id, @RequestBody Competitor competitorDetails) {
        Competitor competitor = competitorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id " + id));
        competitor.setFirstName(competitorDetails.getFirstName());
        competitor.setSecondName(competitorDetails.getSecondName());
        competitor.setLastName(competitorDetails.getLastName());
        competitor.setDateOfBirth(competitorDetails.getDateOfBirth());
        competitor.setGender(competitorDetails.getGender());
        competitor.setNationality(competitorDetails.getNationality());
        competitor.setTeam(competitorDetails.getTeam());
        return competitorRepository.save(competitor);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitorRepository.existsById(id)) {
            throw new EntityNotFoundException("Competitor not found with id " + id);
        }
        competitorRepository.deleteById(id);
    }
}
