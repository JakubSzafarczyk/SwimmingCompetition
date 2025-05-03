package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.ResultDTO;
import com.polsl.entity.Competitor;
import com.polsl.entity.Race;
import com.polsl.entity.Result;
import com.polsl.repository.ResultRepository;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;
    
    @GetMapping("/{id}/competitor")
    public @ResponseBody Competitor getCompetitorForResult(@PathVariable Long id) {
    Result result = resultRepository.findById(id).orElse(null);
    return result.getCompetitor();
    }
    
    @GetMapping("/{id}/race")
    public @ResponseBody Race getRaceForResult(@PathVariable Long id) {
    Result result = resultRepository.findById(id).orElse(null);
    return result.getRace();
    }
    
    @GetMapping("/{id}")
    public @ResponseBody ResultDTO getResult(@PathVariable Long id) {
    Result result = resultRepository.findById(id).orElse(null);
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
        Optional<Result> optionalResult = resultRepository.findById(id);
        if (optionalResult.isPresent()) {
            Result result = optionalResult.get();
            result.setPlace(resultDetails.getPlace());
            result.setTime(resultDetails.getTime());
            result.setCompetitor(resultDetails.getCompetitor());
            result.setRace(resultDetails.getRace());
            return resultRepository.save(result);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resultRepository.deleteById(id);
    }
}
