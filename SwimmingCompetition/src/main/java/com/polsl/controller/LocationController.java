package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.LocationDTO;
import com.polsl.dto.LocationRequestDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Location;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.LocationRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private CompetitionRepository competitionRepository;

    @GetMapping("/{id}/competitions")
    public @ResponseBody CollectionModel<CompetitionRequestDTO> getCompetitionsForLocation(@PathVariable Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
        List<CompetitionRequestDTO> competitions = location.getCompetitions().stream()
                .map(CompetitionRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(competitions);
    }

    @GetMapping("/{id}")
    public @ResponseBody LocationRequestDTO getLocation(@PathVariable Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
        return new LocationRequestDTO(location);
    }

    @GetMapping
    public @ResponseBody CollectionModel<LocationRequestDTO> getAllLocations() {
        List<LocationRequestDTO> locationsDTO =
                StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                        .map(LocationRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(locationsDTO);
    }

    @PostMapping
    public LocationRequestDTO create(@RequestBody LocationDTO dto) {
    	Location location = new Location();
    	location.setName(dto.getName());
    	location.setAddress(dto.getAddress());
    	location.setPoolLength(dto.getPoolLength());
    	location.setCapacity(dto.getCapacity());

        if (dto.getCompetitionId() != null) {
            Set<Competition> competitions = dto.getCompetitionId().stream()
                    .map(id -> competitionRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id)))
                    .collect(Collectors.toSet());
            location.setCompetitions(competitions);
        }

        Location saved = locationRepository.save(location);
        return new LocationRequestDTO(saved);
    }

    @PutMapping("/{id}")
    public LocationRequestDTO update(@PathVariable Long id, @RequestBody LocationDTO dto) {
    	Location location = locationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + id));

    	Set<Competition> competitions = dto.getCompetitionId().stream()
                .map(competitionId -> competitionRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id)))
                .collect(Collectors.toSet());

    	location.setName(dto.getName());
        location.setAddress(dto.getAddress());
        location.setPoolLength(dto.getPoolLength());
        location.setCapacity(dto.getCapacity());
        location.setCompetitions(competitions);

        Location updated = locationRepository.save(location);
        return new LocationRequestDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location not found with id " + id);
        }
        locationRepository.deleteById(id);
    }
}
