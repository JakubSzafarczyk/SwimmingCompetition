package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitorDTO;
import com.polsl.dto.RaceDTO;
import com.polsl.dto.ResultDTO;
import com.polsl.entity.Result;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/{id}/competitor")
    public @ResponseBody CompetitorDTO getCompetitorForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new CompetitorDTO(result.getCompetitor());
    }

    @GetMapping("/{id}/race")
    public @ResponseBody RaceDTO getRaceForResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new RaceDTO(result.getRace());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResultDTO getResult(@PathVariable Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        return new ResultDTO(result);
    }

    @GetMapping
    public @ResponseBody CollectionModel<ResultDTO> getAllResults() {
        List<ResultDTO> resultsDTO =
                StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                        .map(ResultDTO::new).collect(Collectors.toList());
        return CollectionModel.of(resultsDTO);
    }

    @PostMapping
    public Result create(@RequestBody Result result) {
        return resultRepository.save(result);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Result resultDetails) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id " + id));
        result.setPlace(resultDetails.getPlace());
        result.setTime(resultDetails.getTime());
        result.setCompetitor(resultDetails.getCompetitor());
        result.setRace(resultDetails.getRace());
        return resultRepository.save(result);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Result not found with id " + id);
        }
        resultRepository.deleteById(id);
    }
}
