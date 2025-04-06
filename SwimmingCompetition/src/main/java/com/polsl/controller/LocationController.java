package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Location;
import com.polsl.repository.LocationRepository;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;
    
    @GetMapping
    public Iterable<Location> getAll() {
        return locationRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Location getById(@PathVariable Long id) {
        return locationRepository.findById(id).orElse(null);
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
