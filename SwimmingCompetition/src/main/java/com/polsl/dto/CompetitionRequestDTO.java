package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.CompetitionController;
import com.polsl.entity.Competition;
import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Timestamp;

@Getter
@Setter
public class CompetitionRequestDTO extends RepresentationModel<CompetitionRequestDTO>{
public CompetitionRequestDTO(Competition competition){
	super();
	this.competitionId = competition.getCompetitionId();
	this.name = competition.getName();
	this.startDate = competition.getStartDate();
	this.endDate = competition.getEndDate();
	this.description = competition.getDescription();
	
	this.add(linkTo(methodOn(CompetitionController.class)
			.getLocationForCompetition(competition.getCompetitionId())).withRel("location"));
	this.add(linkTo(methodOn(CompetitionController.class)
			.getRacesForCompetition(competition.getCompetitionId())).withRel("races"));
	this.add(linkTo(methodOn(CompetitionController.class)
			.getCompetitorsForCompetition(competition.getCompetitionId())).withRel("competitors"));
}
private Long competitionId;
private String name;
private Timestamp startDate;
private Timestamp endDate;
private String description;
}
