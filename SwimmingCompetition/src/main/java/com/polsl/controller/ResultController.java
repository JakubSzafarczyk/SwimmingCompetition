package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitorRequestDTO;
import com.polsl.dto.RaceRequestDTO;
import com.polsl.dto.ResultRequestDTO;
import com.polsl.entity.Result;
import com.polsl.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

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
