package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachPostDTO;
import com.polsl.dto.CoachPutDTO;
import com.polsl.dto.CoachGetDTO;
import com.polsl.dto.TeamGetDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Team;
import com.polsl.repository.CoachRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @GetMapping("/{id}/team")
    public @ResponseBody TeamGetDTO getTeamForCoach(@PathVariable Long id) {
	    Coach coach = coachRepository.findById(id)
	    		.orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + id));
	    return new TeamGetDTO(coach.getTeam());
    }
    
    @GetMapping("/{id}")
    public @ResponseBody CoachGetDTO getCoach(@PathVariable Long id) {
    	Coach coach = coachRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + id));
    	return new CoachGetDTO(coach);
    }
    
    @GetMapping
    public @ResponseBody CollectionModel<CoachGetDTO> getAllCoaches() {
    	List<CoachGetDTO> coachsDTO =
    			StreamSupport.stream(coachRepository.findAll().spliterator(), false)
    			.map(CoachGetDTO::new).collect(Collectors.toList());
    	return CollectionModel.of(coachsDTO);
    }
    
    @PostMapping
    public CoachGetDTO create(@Valid @RequestBody CoachPostDTO dto) {
        Coach coach = new Coach();
        coach.setFirstName(dto.getFirstName());
        coach.setSecondName(dto.getSecondName());
        coach.setLastName(dto.getLastName());
        coach.setDateOfBirth(dto.getDateOfBirth());
        coach.setGender(dto.getGender());
        coach.setNationality(dto.getNationality());
        
        if (dto.getTeamId() != null) {
        	Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        	coach.setTeam(team);
        }

        Coach saved = coachRepository.save(coach);
        return new CoachGetDTO(saved);
    }
    
    @PutMapping("/{id}")
    public CoachGetDTO update(@PathVariable Long id, @Valid @RequestBody CoachPutDTO dto) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coach not found"));

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        coach.setFirstName(dto.getFirstName());
        coach.setSecondName(dto.getSecondName());
        coach.setLastName(dto.getLastName());
        coach.setDateOfBirth(dto.getDateOfBirth());
        coach.setGender(dto.getGender());
        coach.setNationality(dto.getNationality());
        coach.setTeam(team);

        Coach updated = coachRepository.save(coach);
        return new CoachGetDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	if (!coachRepository.existsById(id)) {
            throw new EntityNotFoundException("Coach not found with id " + id);
        }
        coachRepository.deleteById(id);
    }
}