package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachRequestDTO;
import com.polsl.dto.TeamRequestDTO;
import com.polsl.entity.Coach;
import com.polsl.repository.CoachRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;
    
    @GetMapping("/{id}/team")
    public @ResponseBody TeamRequestDTO getTeamForCoach(@PathVariable Long id) {
    Coach coach = coachRepository.findById(id)
    		.orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + id));
    return new TeamRequestDTO(coach.getTeam());
    }
    
    @GetMapping("/{id}")
    public @ResponseBody CoachRequestDTO getCoach(@PathVariable Long id) {
    Coach coach = coachRepository.findById(id)
    		.orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + id));
    return new CoachRequestDTO(coach);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<CoachRequestDTO> getAllCoaches() {
    List<CoachRequestDTO> coachsDTO =
    StreamSupport.stream(coachRepository.findAll().spliterator(), false)
    .map(CoachRequestDTO::new).collect(Collectors.toList());
    return CollectionModel.of(coachsDTO);
    }
    
    @PostMapping
    public Coach create(@RequestBody Coach coach) {
        return coachRepository.save(coach);
    }
    
    @PutMapping("/{id}")
    public Coach update(@PathVariable Long id, @RequestBody Coach coachDetails) {
    	Coach coach = coachRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + id));
        coach.setFirstName(coachDetails.getFirstName());
        coach.setSecondName(coachDetails.getSecondName());
        coach.setLastName(coachDetails.getLastName());
        coach.setDateOfBirth(coachDetails.getDateOfBirth());
        coach.setGender(coachDetails.getGender());
        coach.setNationality(coachDetails.getNationality());
        coach.setTeam(coachDetails.getTeam());
        return coachRepository.save(coach); 
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	if (!coachRepository.existsById(id)) {
            throw new EntityNotFoundException("Coach not found with id " + id);
        }
        coachRepository.deleteById(id);
    }
}