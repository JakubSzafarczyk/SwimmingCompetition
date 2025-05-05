package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.dto.ResultRequestDTO;
import com.polsl.entity.Race;
import com.polsl.repository.RaceRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @GetMapping("/{id}/results")
    public @ResponseBody CollectionModel<ResultRequestDTO> getResultsForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        List<ResultRequestDTO> results = race.getResults().stream()
                .map(ResultRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(results);
    }

    @GetMapping("/{id}/competition")
    public @ResponseBody CompetitionRequestDTO getCompetitionForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        return new CompetitionRequestDTO(race.getCompetition());
    }

    @GetMapping("/{id}")
    public @ResponseBody RaceRequestDTO getRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        return new RaceRequestDTO(race);
    }

    @GetMapping
    public @ResponseBody CollectionModel<RaceRequestDTO> getAllRaces() {
        List<RaceRequestDTO> racesDTO =
                StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                        .map(RaceRequestDTO::new).collect(Collectors.toList());
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
