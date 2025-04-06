package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Race;
import com.polsl.repository.RaceRepository;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;
    
    @GetMapping
    public Iterable<Race> getAll() {
        return raceRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Race getById(@PathVariable Long id) {
        return raceRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Race create(@RequestBody Race race) {
        return raceRepository.save(race);
    }
    
    @PutMapping("/{id}")
    public Race update(@PathVariable Long id, @RequestBody Race raceDetails) {
        Optional<Race> optionalRace = raceRepository.findById(id);
        if (optionalRace.isPresent()) {
            Race race = optionalRace.get();
            race.setStyle(raceDetails.getStyle());
            race.setDistance(raceDetails.getDistance());
            race.setDate(raceDetails.getDate());
            return raceRepository.save(race);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        raceRepository.deleteById(id);
    }
}
