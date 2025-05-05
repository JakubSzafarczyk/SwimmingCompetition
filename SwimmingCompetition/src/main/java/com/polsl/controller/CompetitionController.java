package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.LocationRequestDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.entity.Competition;
import com.polsl.repository.CompetitionRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;

    @GetMapping("/{id}/location")
    public @ResponseBody LocationRequestDTO getLocationForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	return new LocationRequestDTO(competition.getLocation());
    }
    
    @GetMapping("/{id}/races")
    public @ResponseBody CollectionModel<RaceRequestDTO> getRacesForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	List<RaceRequestDTO> races = competition.getRaces().stream()
    			.map(RaceRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(races);
    }
    
    @GetMapping("/{id}/competitors")
    public @ResponseBody CollectionModel<CompetitorRequestDTO> getCompetitorsForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	List<CompetitorRequestDTO> competitor = competition.getCompetitors().stream()
    			.map(CompetitorRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitor);
    }
    
    @GetMapping("/{id}")
    public @ResponseBody CompetitionRequestDTO getCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	return new CompetitionRequestDTO(competition);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<CompetitionRequestDTO> getAllCompetitions() {
    	List<CompetitionRequestDTO> competitionsDTO =
    			StreamSupport.stream(competitionRepository.findAll().spliterator(), false)
    			.map(CompetitionRequestDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitionsDTO);
    }
    
    @PostMapping
    public Competition create(@RequestBody Competition competition) {
        return competitionRepository.save(competition);
    }
    
    @PutMapping("/{id}")
    public Competition update(@PathVariable Long id, @RequestBody Competition competitionDetails) {
    	Competition competition = competitionRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
        competition.setName(competitionDetails.getName());
        competition.setStartDate(competitionDetails.getStartDate());
        competition.setEndDate(competitionDetails.getEndDate());
        competition.setDescription(competitionDetails.getDescription());
        competition.setLocation(competitionDetails.getLocation());
        return competitionRepository.save(competition);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitionRepository.existsById(id)) {
            throw new EntityNotFoundException("Competition not found with id " + id);
        }
        competitionRepository.deleteById(id);
    }
}
