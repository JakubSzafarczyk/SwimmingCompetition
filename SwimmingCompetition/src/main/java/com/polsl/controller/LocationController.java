package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.LocationDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Location;
import com.polsl.repository.LocationRepository;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;
    
    @GetMapping("/{id}/competitions")
    public @ResponseBody Iterable<Competition> getCompetitionsForLocation(@PathVariable Long id) {
    Location location = locationRepository.findById(id).orElse(null);
    return location.getCompetitions();
    }
    
    @GetMapping("/{id}")
    public @ResponseBody LocationDTO getLocation(@PathVariable Long id) {
    Location location = locationRepository.findById(id).orElse(null);
    return new LocationDTO(location);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<LocationDTO> getAllLocations() {
    List<LocationDTO> locationsDTO =
    StreamSupport.stream(locationRepository.findAll().spliterator(), false)
    .map(LocationDTO::new).collect(Collectors.toList());
    return CollectionModel.of(locationsDTO);
    }
    
    @PostMapping
    public Location create(@RequestBody Location location) {
        return locationRepository.save(location);
    }
    
    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @RequestBody Location locationDetails) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setName(locationDetails.getName());
            location.setAddress(locationDetails.getAddress());
            location.setPoolLength(locationDetails.getPoolLength());
            location.setCapacity(locationDetails.getCapacity());
            return locationRepository.save(location);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationRepository.deleteById(id);
    }
}
