package com.polsl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.polsl.entity.Competitor;
import com.polsl.repository.CompetitorRepository;

@RestController
@RequestMapping("/competitors")
public class CompetitorController {

    @Autowired
    private CompetitorRepository competitorRepository;
    
    @GetMapping
    public Iterable<Competitor> getAll() {
        return competitorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Competitor getById(@PathVariable Long id) {
        return competitorRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Competitor create(@RequestBody Competitor competitor) {
        return competitorRepository.save(competitor);
    }
    
    @PutMapping("/{id}")
    public Competitor update(@PathVariable Long id, @RequestBody Competitor competitorDetails) {
        Optional<Competitor> optionalCompetitor = competitorRepository.findById(id);
        if (optionalCompetitor.isPresent()) {
            Competitor competitor = optionalCompetitor.get();
            competitor.setFirstName(competitorDetails.getFirstName());
            competitor.setSecondName(competitorDetails.getSecondName());
            competitor.setLastName(competitorDetails.getLastName());
            competitor.setDateOfBirth(competitorDetails.getDateOfBirth());
            competitor.setGender(competitorDetails.getGender());
            competitor.setNationality(competitorDetails.getNationality());
            competitor.setTeam(competitorDetails.getTeam());
            return competitorRepository.save(competitor);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        competitorRepository.deleteById(id);
    }
}
