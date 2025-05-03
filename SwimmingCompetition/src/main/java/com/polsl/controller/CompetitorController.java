package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitorDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Competitor;
import com.polsl.entity.Result;
import com.polsl.entity.Team;
import com.polsl.repository.CompetitorRepository;

@RestController
@RequestMapping("/competitors")
public class CompetitorController {

    @Autowired
    private CompetitorRepository competitorRepository;
    
    //@GetMapping
    //public Iterable<Competitor> getAll() {
    //    return competitorRepository.findAll();
    //}
    
    //@GetMapping("/{id}")
    //public Competitor getById(@PathVariable Long id) {
    //    return competitorRepository.findById(id).orElse(null);
    //}
    
    @GetMapping("/{id}/team")
    public @ResponseBody Team getTeamForCompetitor(@PathVariable Long id) {
    Competitor competitor = competitorRepository.findById(id).orElse(null);
    return competitor.getTeam();
    }
    @GetMapping("/{id}/results")
    public @ResponseBody Iterable<Result> getResultsForCompetitor(@PathVariable Long id) {
    Competitor competitor = competitorRepository.findById(id).orElse(null);
    return competitor.getResults();
    }
    @GetMapping("/{id}/competitions")
    public @ResponseBody Iterable<Competition> getCompetitionsForCompetitor(@PathVariable Long id) {
    Competitor competitor = competitorRepository.findById(id).orElse(null);
    return competitor.getCompetitions();
    }
 
    @GetMapping("/{id}")
    public @ResponseBody CompetitorDTO getCompetitor(@PathVariable Long id) {
    Competitor competitor = competitorRepository.findById(id).orElse(null);
    return new CompetitorDTO(competitor);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CompetitorDTO> getAllCompetitors() {
    List<CompetitorDTO> competitorsDTO =
    StreamSupport.stream(competitorRepository.findAll().spliterator(), false)
    .map(CompetitorDTO::new).collect(Collectors.toList());
    return CollectionModel.of(competitorsDTO);
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
