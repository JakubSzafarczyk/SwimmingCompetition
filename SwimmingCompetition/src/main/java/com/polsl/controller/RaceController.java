package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionDTO;
import com.polsl.dto.RaceDTO;
import com.polsl.dto.ResultDTO;
import com.polsl.entity.Race;
import com.polsl.repository.RaceRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @GetMapping("/{id}/results")
    public @ResponseBody CollectionModel<ResultDTO> getResultsForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        List<ResultDTO> results = race.getResults().stream()
                .map(ResultDTO::new).collect(Collectors.toList());
        return CollectionModel.of(results);
    }

    @GetMapping("/{id}/competition")
    public @ResponseBody CompetitionDTO getCompetitionForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        return new CompetitionDTO(race.getCompetition());
    }

    @GetMapping("/{id}")
    public @ResponseBody RaceDTO getRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
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
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        race.setStyle(raceDetails.getStyle());
        race.setDistance(raceDetails.getDistance());
        race.setDate(raceDetails.getDate());
        return raceRepository.save(race);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!raceRepository.existsById(id)) {
            throw new EntityNotFoundException("Race not found with id " + id);
        }
        raceRepository.deleteById(id);
    }
}
