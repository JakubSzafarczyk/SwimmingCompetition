package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.LocationRequestDTO;
import com.polsl.entity.Location;
import com.polsl.repository.LocationRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

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
    public Location create(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody Location locationDetails) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
        location.setName(locationDetails.getName());
        location.setAddress(locationDetails.getAddress());
        location.setPoolLength(locationDetails.getPoolLength());
        location.setCapacity(locationDetails.getCapacity());
        return locationRepository.save(location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location not found with id " + id);
        }
        locationRepository.deleteById(id);
    }
}
