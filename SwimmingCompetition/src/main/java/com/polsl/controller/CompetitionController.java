package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionDTO;
import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.LocationRequestDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.entity.Location;
import com.polsl.entity.Race;
import com.polsl.entity.Competitor;
import com.polsl.entity.Competition;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.LocationRepository;
import com.polsl.repository.RaceRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;
    
    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private RaceRepository raceRepository;
    
    @Autowired
    private CompetitorRepository competitorRepository;

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
    public CompetitionRequestDTO create(@RequestBody CompetitionDTO dto) {
        Competition competition = new Competition();
        competition.setName(dto.getName());
        competition.setStartDate(dto.getStartDate());
        competition.setEndDate(dto.getEndDate());
        competition.setDescription(dto.getDescription());
        
        if (dto.getLocationId() != null) {
        	Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        	competition.setLocation(location);
        }
        
        if (dto.getRaceIds() != null) {
        	Set<Race> races = dto.getRaceIds().stream()
            	    .map(id -> raceRepository.findById(id)
            	        .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + id)))
            	    .collect(Collectors.toSet());
        	competition.setRaces(races);
        }
        
        if (dto.getCompetitorIds() != null) {
        	Set<Competitor> competitors = dto.getCompetitorIds().stream()
            	    .map(id -> competitorRepository.findById(id)
            	        .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + id)))
            	    .collect(Collectors.toSet());
        	competition.setCompetitors(competitors);
        }

        Competition saved = competitionRepository.save(competition);
        return new CompetitionRequestDTO(saved);
    }
    
    @PutMapping("/{id}")
    public CompetitionRequestDTO update(@PathVariable Long id, @RequestBody CompetitionDTO dto) {
    	Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found"));
        
    	Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        
    	Set<Race> races = dto.getRaceIds().stream()
        	    .map(ids -> raceRepository.findById(id)
        	        .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + id)))
        	    .collect(Collectors.toSet());

    	Set<Competitor> competitors = dto.getCompetitorIds().stream()
    	    .map(ids -> competitorRepository.findById(id)
    	        .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + id)))
    	    .collect(Collectors.toSet());
        	
    	competition.setName(dto.getName());
        competition.setStartDate(dto.getStartDate());
        competition.setEndDate(dto.getEndDate());
        competition.setDescription(dto.getDescription());
        competition.setLocation(location);
        competition.setRaces(races);
        competition.setCompetitors(competitors);

        Competition updated = competitionRepository.save(competition);
        return new CompetitionRequestDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitionRepository.existsById(id)) {
            throw new EntityNotFoundException("Competition not found with id " + id);
        }
        competitionRepository.deleteById(id);
    }
}
