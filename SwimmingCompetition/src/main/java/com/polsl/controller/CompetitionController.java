package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CompetitionDTO;
import com.polsl.entity.Competition;
import com.polsl.entity.Competitor;
import com.polsl.entity.Location;
import com.polsl.entity.Race;
import com.polsl.repository.CompetitionRepository;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;
    
    //@GetMapping
    //public Iterable<Competition> getAll() {
    //    return competitionRepository.findAll();
    //}
    
    //@GetMapping("/{id}")
    //public Competition getById(@PathVariable Long id) {
    //    return competitionRepository.findById(id).orElse(null);
    //}
    
    @GetMapping("/{id}/race")
    public @ResponseBody Iterable<Race> getRaceForCompetition(@PathVariable Long id) {
    Competition competition = competitionRepository.findById(id).orElse(null);
    return competition.getRaces();
    }
    @GetMapping("/{id}/competitor")
    public @ResponseBody Iterable<Competitor> getCompetitorForCompetition(@PathVariable Long id) {
    Competition competition = competitionRepository.findById(id).orElse(null);
    return competition.getCompetitors();
    }
    @GetMapping("/{id}/location")
    public @ResponseBody Location getLocationForCompetition(@PathVariable Long id) {
    Competition competition = competitionRepository.findById(id).orElse(null);
    return competition.getLocation();
    }
    @GetMapping("/{id}")
    public @ResponseBody CompetitionDTO getCompetition(@PathVariable Long id) {
    Competition competition = competitionRepository.findById(id).orElse(null);
    return new CompetitionDTO(competition);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CompetitionDTO> getAllCompetitions() {
    List<CompetitionDTO> competitionsDTO =
    StreamSupport.stream(competitionRepository.findAll().spliterator(), false)
    .map(CompetitionDTO::new).collect(Collectors.toList());
    return CollectionModel.of(competitionsDTO);
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
