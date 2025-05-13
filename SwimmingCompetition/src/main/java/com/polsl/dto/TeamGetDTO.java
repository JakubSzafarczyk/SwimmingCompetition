package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.TeamController;
import com.polsl.entity.Team;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class TeamGetDTO extends RepresentationModel<TeamGetDTO>{
public TeamGetDTO(Team team){
	super();
	this.teamId = team.getTeamId();
	this.name = team.getName();
	this.headquarters = team.getHeadquarters();
	
	this.add(linkTo(methodOn(TeamController.class)
			.getCoachesForTeam(team.getTeamId())).withRel("coaches"));
	this.add(linkTo(methodOn(TeamController.class)
			.getCompetitorsForTeam(team.getTeamId())).withRel("competitors"));
}
private Long teamId;
private String name;
private String headquarters;
}
