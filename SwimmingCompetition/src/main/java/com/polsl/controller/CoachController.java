package com.polsl.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Team;
import com.polsl.repository.CoachRepository;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;
    
    //@GetMapping
   // public Iterable<Coach> getAll() {
   //     return coachRepository.findAll();
   // }
    
    //@GetMapping("/{id}")
    //public Coach getById(@PathVariable Long id) {
    //    return coachRepository.findById(id).orElse(null);
    //}
    
    @GetMapping("/{id}/team")
    public @ResponseBody Team getTeamForCoach(@PathVariable Long id) {
    Coach coach = coachRepository.findById(id).orElse(null);
    return coach.getTeam();
    }
    
    @GetMapping("/{id}")
    public @ResponseBody CoachDTO getCoach(@PathVariable Long id) {
    Coach coach = coachRepository.findById(id).orElse(null);
    return new CoachDTO(coach);
    }
    @GetMapping
    public @ResponseBody CollectionModel<CoachDTO> getAllCoaches() {
    List<CoachDTO> coachsDTO =
    StreamSupport.stream(coachRepository.findAll().spliterator(), false)
    .map(CoachDTO::new).collect(Collectors.toList());
    return CollectionModel.of(coachsDTO);
    }
    
    @PostMapping
    public Coach create(@RequestBody Coach coach) {
        return coachRepository.save(coach);
    }
    
    @PutMapping("/{id}")
    public Coach update(@PathVariable Long id, @RequestBody Coach coachDetails) {
        Optional<Coach> optionalCoach = coachRepository.findById(id);
        if (optionalCoach.isPresent()) {
            Coach coach = optionalCoach.get();
            coach.setFirstName(coachDetails.getFirstName());
            coach.setSecondName(coachDetails.getSecondName());
            coach.setLastName(coachDetails.getLastName());
            coach.setDateOfBirth(coachDetails.getDateOfBirth());
            coach.setGender(coachDetails.getGender());
            coach.setNationality(coachDetails.getNationality());
            coach.setTeam(coachDetails.getTeam());
            return coachRepository.save(coach);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coachRepository.deleteById(id);
    }
}