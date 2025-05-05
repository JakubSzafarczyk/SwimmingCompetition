package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.dto.ResultDTO;
import com.polsl.dto.ResultRequestDTO;
import com.polsl.entity.Competitor;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.RaceRepository;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;
    
    @Autowired
    private CompetitorRepository competitorRepository;
    
    @Autowired
    private RaceRepository raceRepository;

    @GetMapping("/{id}/competitor")
    public @ResponseBody CompetitorRequestDTO getCompetitorForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new CompetitorRequestDTO(result.getCompetitor());
    }

    @GetMapping("/{id}/race")
    public @ResponseBody RaceRequestDTO getRaceForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new RaceRequestDTO(result.getRace());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResultRequestDTO getResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new ResultRequestDTO(result);
    }

    @GetMapping
    public @ResponseBody CollectionModel<ResultRequestDTO> getAllResults() {
        List<ResultRequestDTO> resultsDTO =
                StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                        .map(ResultRequestDTO::new).collect(Collectors.toList());
        return CollectionModel.of(resultsDTO);
    }

    @PostMapping
    public ResultRequestDTO create(@RequestBody ResultDTO dto) {
    	Result result = new Result();
    	result.setPlace(dto.getPlace());
    	result.setTime(dto.getTime());
        
        if (dto.getCompetitorId() != null) {
        	Competitor competitor = competitorRepository.findById(dto.getCompetitorId())
                    .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + dto.getCompetitorId()));
            result.setCompetitor(competitor);
        }
        
        if (dto.getRaceId() != null) {
        	Race race = raceRepository.findById(dto.getRaceId())
                    .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + dto.getRaceId()));
            result.setRace(race);
        }

        Result saved = resultRepository.save(result);
        return new ResultRequestDTO(saved);
    }

    @PutMapping("/{id}")
    public ResultRequestDTO update(@PathVariable Long id, @RequestBody ResultDTO dto) {
    	Result result = resultRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

    	Competitor competitor = competitorRepository.findById(dto.getCompetitorId())
                .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + dto.getCompetitorId()));
        
    	Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + dto.getRaceId()));
        
    	result.setPlace(dto.getPlace());
    	result.setTime(dto.getTime());
    	result.setCompetitor(competitor);
    	result.setRace(race);

    	Result updated = resultRepository.save(result);
        return new ResultRequestDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Result not found with id " + id);
        }
        resultRepository.deleteById(id);
    }
}
