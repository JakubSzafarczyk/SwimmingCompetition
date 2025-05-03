package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.CoachController;
import com.polsl.entity.Coach;
import com.polsl.model.Gender;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Date;
@Getter
@Setter
public class CoachDTO extends RepresentationModel<CoachDTO>{
public CoachDTO(Coach coach){
	super();
	this.coachId = coach.getCoachId();
	this.firstName = coach.getFirstName();
	this.secondName = coach.getSecondName();
	this.lastName = coach.getLastName();
	this.dateOfBirth = coach.getDateOfBirth();
	this.gender = coach.getGender();
	this.nationality = coach.getNationality();
	
	this.add(linkTo(methodOn(CoachController.class)
			.getTeamForCoach(coach.getCoachId())).withRel("team"));
}
private Long coachId;
private String firstName;
private String secondName;
private String lastName;
private Date dateOfBirth;
private Gender gender;
private String nationality;
}
