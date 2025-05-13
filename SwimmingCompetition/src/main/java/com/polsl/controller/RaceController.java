package com.polsl.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionGetDTO;
import com.polsl.dto.RacePostDTO;
import com.polsl.dto.RacePutDTO;
import com.polsl.dto.RaceGetDTO;
import com.polsl.dto.ResultGetDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.CompetitionRepository;
import com.polsl.repository.RaceRepository;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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
    public @ResponseBody CollectionModel<ResultGetDTO> getResultsForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        List<ResultGetDTO> results = race.getResults().stream()
                .map(ResultGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(results);
    }

    @GetMapping("/{id}/competition")
    public @ResponseBody CompetitionGetDTO getCompetitionForRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        return new CompetitionGetDTO(race.getCompetition());
    }

    @GetMapping("/{id}")
    public @ResponseBody RaceGetDTO getRace(@PathVariable Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id " + id));
        return new RaceGetDTO(race);
    }

    @GetMapping
    public @ResponseBody CollectionModel<RaceGetDTO> getAllRaces() {
        List<RaceGetDTO> racesDTO =
                StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                        .map(RaceGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(racesDTO);
    }

    @PostMapping
    public RaceGetDTO create(@Valid @RequestBody RacePostDTO dto) {
    	Race race = new Race();
    	race.setStyle(dto.getStyle());
    	race.setDistance(dto.getDistance());
    	race.setDate(dto.getDate());

        if (dto.getResultIds() != null) {
            Set<Result> results = dto.getResultIds().stream()
                    .map(resultId -> reslutRepository.findById(resultId)
                            .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + resultId)))
                    .collect(Collectors.toSet());
            race.setResults(results);
        }
        
        if (dto.getCompetitionId() != null) {
            Competition competition = competitionRepository.findById(dto.getCompetitionId())
                    .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + dto.getCompetitionId()));
            race.setCompetition(competition);
        }

        Race saved = raceRepository.save(race);
        return new RaceGetDTO(saved);
    }

    @PutMapping("/{id}")
    public RaceGetDTO update(@PathVariable Long id,@Valid @RequestBody RacePutDTO dto) {
    	Race race = raceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + id));

    	Set<Result> results = dto.getResultIds().stream()
                .map(resultId -> reslutRepository.findById(resultId)
                        .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + resultId)))
                .collect(Collectors.toSet());

    	Competition competition = competitionRepository.findById(dto.getCompetitionId())
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + dto.getCompetitionId()));
        
    	race.setStyle(dto.getStyle());
    	race.setDistance(dto.getDistance());
    	race.setDate(dto.getDate());
    	race.setResults(results);
    	race.setCompetition(competition);

    	Race updated = raceRepository.save(race);
        return new RaceGetDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!raceRepository.existsById(id)) {
            throw new EntityNotFoundException("Race not found with id " + id);
        }
        raceRepository.deleteById(id);
    }
}
