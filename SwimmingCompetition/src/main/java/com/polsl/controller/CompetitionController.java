package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionPostDTO;
import com.polsl.dto.CompetitionPutDTO;
import com.polsl.dto.CompetitionGetDTO;
import com.polsl.dto.CompetitorGetDTO;
import com.polsl.dto.LocationGetDTO;
import com.polsl.dto.RaceGetDTO;
import com.polsl.entity.Location;
import com.polsl.entity.Race;
import com.polsl.entity.Competitor;
import com.polsl.entity.Competition;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.LocationRepository;
import com.polsl.repository.RaceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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
    public @ResponseBody LocationGetDTO getLocationForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	return new LocationGetDTO(competition.getLocation());
    }
    
    @GetMapping("/{id}/races")
    public @ResponseBody CollectionModel<RaceGetDTO> getRacesForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	List<RaceGetDTO> races = competition.getRaces().stream()
    			.map(RaceGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(races);
    }
    
    @GetMapping("/{id}/competitors")
    public @ResponseBody CollectionModel<CompetitorGetDTO> getCompetitorsForCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	List<CompetitorGetDTO> competitor = competition.getCompetitors().stream()
    			.map(CompetitorGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitor);
    }
    
    @GetMapping("/{id}")
    public @ResponseBody CompetitionGetDTO getCompetition(@PathVariable Long id) {
    	Competition competition = competitionRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Competition not found with id " + id));
    	return new CompetitionGetDTO(competition);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<CompetitionGetDTO> getAllCompetitions() {
    	List<CompetitionGetDTO> competitionsDTO =
    			StreamSupport.stream(competitionRepository.findAll().spliterator(), false)
    			.map(CompetitionGetDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(competitionsDTO);
    }
    
    @PostMapping
    public CompetitionGetDTO create(@Valid @RequestBody CompetitionPostDTO dto) {
        Competition competition = new Competition();
        competition.setName(dto.getName());
        
        if (dto.getStartDate() != null) {
        	competition.setStartDate(dto.getStartDate());
        }
        
        if (dto.getEndDate() != null) {
        	competition.setEndDate(dto.getEndDate());
        }
        
        if (dto.getDescription() != null) {
        	competition.setDescription(dto.getDescription());
        }
        
        if (dto.getLocationId() != null) {
        	Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        	competition.setLocation(location);
        }
        
        if (dto.getRaceIds() != null) {
        	Set<Race> races = dto.getRaceIds().stream()
            	    .map(raceIds -> raceRepository.findById(raceIds)
            	        .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + raceIds)))
            	    .collect(Collectors.toSet());
        	competition.setRaces(races);
        }
        
        if (dto.getCompetitorIds() != null) {
        	Set<Competitor> competitors = dto.getCompetitorIds().stream()
            	    .map(competitorIds -> competitorRepository.findById(competitorIds)
            	        .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + competitorIds)))
            	    .collect(Collectors.toSet());
        	competition.setCompetitors(competitors);
        }

        Competition saved = competitionRepository.save(competition);
        return new CompetitionGetDTO(saved);
    }
    
    @PutMapping("/{id}")
    public CompetitionGetDTO update(@PathVariable Long id, @Valid @RequestBody CompetitionPutDTO dto) {
    	Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found"));
        
    	Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        
    	Set<Race> races = dto.getRaceIds().stream()
        	    .map(raceIds -> raceRepository.findById(raceIds)
        	        .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + raceIds)))
        	    .collect(Collectors.toSet());

    	Set<Competitor> competitors = dto.getCompetitorIds().stream()
    	    .map(competitorIds -> competitorRepository.findById(competitorIds)
    	        .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + competitorIds)))
    	    .collect(Collectors.toSet());
        	
    	competition.setName(dto.getName());
        competition.setStartDate(dto.getStartDate());
        competition.setEndDate(dto.getEndDate());
        competition.setDescription(dto.getDescription());
        competition.setLocation(location);
        competition.setRaces(races);
        competition.setCompetitors(competitors);

        Competition updated = competitionRepository.save(competition);
        return new CompetitionGetDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!competitionRepository.existsById(id)) {
            throw new EntityNotFoundException("Competition not found with id " + id);
        }
        competitionRepository.deleteById(id);
    }
}
