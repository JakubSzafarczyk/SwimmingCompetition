package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Result;
import com.polsl.repository.ResultRepository;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;
    
    @GetMapping
    public Iterable<Result> getAll() {
        return resultRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return resultRepository.findById(id).orElse(null);
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
