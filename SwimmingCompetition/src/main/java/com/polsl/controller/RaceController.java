package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionRequestDTO;
import com.polsl.dto.RaceDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.dto.ResultRequestDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.RaceRepository;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;
    
    @Autowired
    private ResultRepository reslutRepository;
    
    @Autowired
    private CompetitionRepository competitionRepository;

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
    public RaceRequestDTO create(@RequestBody RaceDTO dto) {
    	Race race = new Race();
    	race.setStyle(dto.getStyle());
    	race.setDistance(dto.getDistance());
    	race.setDate(dto.getDate());

        if (dto.getResultIds() != null) {
            Set<Result> results = dto.getResultIds().stream()
                    .map(id -> reslutRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id)))
                    .collect(Collectors.toSet());
            race.setResults(results);
        }
        
        if (dto.getCompetitionId() != null) {
            Competition competition = competitionRepository.findById(dto.getCompetitionId())
                    .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + dto.getCompetitionId()));
            race.setCompetition(competition);
        }

        Race saved = raceRepository.save(race);
        return new RaceRequestDTO(saved);
    }

    @PutMapping("/{id}")
    public RaceRequestDTO update(@PathVariable Long id, @RequestBody RaceDTO dto) {
    	Race race = raceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + id));

    	Set<Result> results = dto.getResultIds().stream()
                .map(resultId -> reslutRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id)))
                .collect(Collectors.toSet());

    	Competition competition = competitionRepository.findById(dto.getCompetitionId())
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + dto.getCompetitionId()));
        
    	race.setStyle(dto.getStyle());
    	race.setDistance(dto.getDistance());
    	race.setDate(dto.getDate());
    	race.setResults(results);
    	race.setCompetition(competition);

    	Race updated = raceRepository.save(race);
        return new RaceRequestDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!raceRepository.existsById(id)) {
            throw new EntityNotFoundException("Race not found with id " + id);
        }
        raceRepository.deleteById(id);
    }
}
