package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.CompetitorController;
import com.polsl.entity.Competitor;
import com.polsl.model.Gender;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Date;

@Getter
@Setter
public class CompetitorDTO extends RepresentationModel<CompetitorDTO>{
public CompetitorDTO(Competitor competitor){
	super();
	this.competitorId = competitor.getCompetitorId();
	this.firstName = competitor.getFirstName();
	this.secondName = competitor.getSecondName();
	this.lastName = competitor.getLastName();
	this.dateOfBirth = competitor.getDateOfBirth();
	this.gender = competitor.getGender();
	this.nationality = competitor.getNationality();
	
	this.add(linkTo(methodOn(CompetitorController.class)
			.getTeamForCompetitor(competitor.getCompetitorId())).withRel("team"));
	this.add(linkTo(methodOn(CompetitorController.class)
			.getResultsForCompetitor(competitor.getCompetitorId())).withRel("result"));
	this.add(linkTo(methodOn(CompetitorController.class)
			.getCompetitionsForCompetitor(competitor.getCompetitorId())).withRel("competitions"));
	
}
private Long competitorId;
private String firstName;
private String secondName;
private String lastName;
private Date dateOfBirth;
private Gender gender;
private String nationality;
}

