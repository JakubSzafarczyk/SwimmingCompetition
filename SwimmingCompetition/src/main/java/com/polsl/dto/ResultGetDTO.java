package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.ResultController;
import com.polsl.entity.Result;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Time;

@Getter
@Setter
public class ResultGetDTO extends RepresentationModel<ResultGetDTO>{
public ResultGetDTO(Result result){
	super();
	this.resultId = result.getResultId();
	this.place = result.getPlace();
	this.time = result.getTime();
	
	this.add(linkTo(methodOn(ResultController.class)
			.getCompetitorForResult(result.getResultId())).withRel("competitor"));
	this.add(linkTo(methodOn(ResultController.class)
			.getRaceForResult(result.getResultId())).withRel("race"));
}
private Long resultId;
private Long place;
private Time time;
}
