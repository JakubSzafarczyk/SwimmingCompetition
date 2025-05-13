package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitorGetDTO;
import com.polsl.dto.RaceGetDTO;
import com.polsl.dto.ResultPostDTO;
import com.polsl.dto.ResultPutDTO;
import com.polsl.dto.ResultGetDTO;
import com.polsl.entity.Competitor;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.CompetitorRepository;
import com.polsl.repository.RaceRepository;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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
    public @ResponseBody CompetitorGetDTO getCompetitorForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new CompetitorGetDTO(result.getCompetitor());
    }

    @GetMapping("/{id}/race")
    public @ResponseBody RaceGetDTO getRaceForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new RaceGetDTO(result.getRace());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResultGetDTO getResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new ResultGetDTO(result);
    }

    @GetMapping
    public @ResponseBody CollectionModel<ResultGetDTO> getAllResults() {
        List<ResultGetDTO> resultsDTO =
                StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                        .map(ResultGetDTO::new).collect(Collectors.toList());
        return CollectionModel.of(resultsDTO);
    }

    @PostMapping
    public ResultGetDTO create(@Valid @RequestBody ResultPostDTO dto) {
    	Result result = new Result();
    	
    	if (dto.getPlace() != null) {
    		result.setPlace(dto.getPlace());
    	}
    	
    	result.setTime(dto.getTime());
        
    	Competitor competitor = competitorRepository.findById(dto.getCompetitorId())
                .orElseThrow(() -> new EntityNotFoundException("Competitor not found with id: " + dto.getCompetitorId()));
        result.setCompetitor(competitor);
        
    	Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() -> new EntityNotFoundException("Race not found with id: " + dto.getRaceId()));
        result.setRace(race);

        Result saved = resultRepository.save(result);
        return new ResultGetDTO(saved);
    }

    @PutMapping("/{id}")
    public ResultGetDTO update(@PathVariable Long id, @Valid @RequestBody ResultPutDTO dto) {
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
        return new ResultGetDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Result not found with id " + id);
        }
        resultRepository.deleteById(id);
    }
}
