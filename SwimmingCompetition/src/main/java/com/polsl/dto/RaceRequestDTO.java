package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.RaceController;
import com.polsl.entity.Race;
import com.polsl.model.RaceStyle;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Timestamp;

@Getter
@Setter
public class RaceRequestDTO extends RepresentationModel<RaceRequestDTO>{
public RaceRequestDTO(Race race){
	super();
	this.raceId = race.getRaceId();
	this.style = race.getStyle();
	this.distance = race.getDistance();
	this.date = race.getDate();
	
	this.add(linkTo(methodOn(RaceController.class)
			.getResultsForRace(race.getRaceId())).withRel("results"));
	this.add(linkTo(methodOn(RaceController.class)
			.getCompetitionForRace(race.getRaceId())).withRel("competition"));
}
private Long raceId;
private RaceStyle style;
private int distance;
private Timestamp date;
}
