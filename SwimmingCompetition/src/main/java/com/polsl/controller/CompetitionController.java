package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Competition;
import com.polsl.repository.CompetitionRepository;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;
    
    @GetMapping
    public Iterable<Competition> getAll() {
        return competitionRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Competition getById(@PathVariable Long id) {
        return competitionRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Competition create(@RequestBody Competition competition) {
        return competitionRepository.save(competition);
    }
    
    @PutMapping("/{id}")
    public Competition update(@PathVariable Long id, @RequestBody Competition competitionDetails) {
        Optional<Competition> optionalCompetition = competitionRepository.findById(id);
        if (optionalCompetition.isPresent()) {
            Competition competition = optionalCompetition.get();
            competition.setName(competitionDetails.getName());
            competition.setStartDate(competitionDetails.getStartDate());
            competition.setEndDate(competitionDetails.getEndDate());
            competition.setDescription(competitionDetails.getDescription());
            competition.setLocation(competitionDetails.getLocation());
            return competitionRepository.save(competition);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        competitionRepository.deleteById(id);
    }
}
