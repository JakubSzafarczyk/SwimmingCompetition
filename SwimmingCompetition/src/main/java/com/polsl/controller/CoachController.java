package com.polsl.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import com.polsl.dto.CoachDTO;
import com.polsl.dto.CoachRequestDTO;
import com.polsl.dto.TeamRequestDTO;
import com.polsl.entity.Coach;
import com.polsl.entity.Team;
import com.polsl.repository.CoachRepository;
import com.polsl.repository.TeamRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
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
    public CoachRequestDTO create(@RequestBody CoachDTO dto) {
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
        return new CoachRequestDTO(saved);
    }
    
    @PutMapping("/{id}")
    public CoachRequestDTO update(@PathVariable Long id, @RequestBody CoachDTO dto) {
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
        return new CoachRequestDTO(updated);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	if (!coachRepository.existsById(id)) {
            throw new EntityNotFoundException("Coach not found with id " + id);
        }
        coachRepository.deleteById(id);
    }
}