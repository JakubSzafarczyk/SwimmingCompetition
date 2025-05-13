package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionGetDTO;
import com.polsl.dto.LocationPostDTO;
import com.polsl.dto.LocationPutDTO;
import com.polsl.dto.LocationGetDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Location;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.LocationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private CompetitionRepository competitionRepository;

    @GetMapping("/{id}/competitions")
    public @ResponseBody CollectionModel<CompetitionGetDTO> getCompetitionsForLocation(@PathVariable Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
        List<CompetitionGetDTO> competitions = location.getCompetitions().stream()
                .map(CompetitionGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitions);
    }

    @GetMapping("/{id}")
    public @ResponseBody LocationGetDTO getLocation(@PathVariable Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
        return new LocationGetDTO(location);
    }

    @GetMapping
    public @ResponseBody CollectionModel<LocationGetDTO> getAllLocations() {
        List<LocationGetDTO> locationsDTO =
                StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                        .map(LocationGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(locationsDTO);
    }

    @PostMapping
    public LocationGetDTO create(@Valid @RequestBody LocationPostDTO dto) {
    	Location location = new Location();
    	location.setName(dto.getName());
    	location.setAddress(dto.getAddress());
    	location.setPoolLength(dto.getPoolLength());
    	
    	if (dto.getCapacity() != null) {
    	location.setCapacity(dto.getCapacity());
    	}

        if (dto.getCompetitionId() != null) {
            Set<Competition> competitions = dto.getCompetitionId().stream()
                    .map(competitionId -> competitionRepository.findById(competitionId)
                            .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + competitionId)))
                    .collect(Collectors.toSet());
            location.setCompetitions(competitions);
        }

        Location saved = locationRepository.save(location);
        return new LocationGetDTO(saved);
    }

    @PutMapping("/{id}")
    public LocationGetDTO update(@PathVariable Long id,@Valid @RequestBody LocationPutDTO dto) {
    	Location location = locationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + id));

    	Set<Competition> competitions = dto.getCompetitionId().stream()
                .map(competitionId -> competitionRepository.findById(competitionId)
                        .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + competitionId)))
                .collect(Collectors.toSet());

    	location.setName(dto.getName());
        location.setAddress(dto.getAddress());
        location.setPoolLength(dto.getPoolLength());
        location.setCapacity(dto.getCapacity());
        location.setCompetitions(competitions);

        Location updated = locationRepository.save(location);
        return new LocationGetDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location not found with id " + id);
        }
        locationRepository.deleteById(id);
    }
}
