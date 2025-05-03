package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.RaceDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.RaceRepository;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;
    
    @GetMapping("/{id}/results")
    public @ResponseBody Iterable<Result> getResultsForRace(@PathVariable Long id) {
    Race race = raceRepository.findById(id).orElse(null);
    return race.getResults();
    }
    @GetMapping("/{id}/competition")
    public @ResponseBody Competition getCompetitionForRace(@PathVariable Long id) {
    Race race = raceRepository.findById(id).orElse(null);
    return race.getCompetition();
    }
    
    @GetMapping("/{id}")
    public @ResponseBody RaceDTO getRace(@PathVariable Long id) {
    Race race = raceRepository.findById(id).orElse(null);
    return new RaceDTO(race);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<RaceDTO> getAllRaces() {
    List<RaceDTO> racesDTO =
    StreamSupport.stream(raceRepository.findAll().spliterator(), false)
    .map(RaceDTO::new).collect(Collectors.toList());
    return CollectionModel.of(racesDTO);
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
