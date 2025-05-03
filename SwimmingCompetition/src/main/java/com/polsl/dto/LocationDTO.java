package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.LocationController;
import com.polsl.entity.Location;
import com.polsl.model.Gender;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Date;
@Getter
@Setter
public class LocationDTO extends RepresentationModel<LocationDTO>{
public LocationDTO(Location location){
	super();
	this.locationId = location.getLocationId();
	this.name = location.getName();
	this.address = location.getAddress();
	this.poolLength = location.getPoolLength();
	this.capacity = location.getCapacity();

	
	this.add(linkTo(methodOn(LocationController.class)
			.getCompetitionsForLocation(location.getLocationId())).withRel("competitions"));
}
private Long locationId;
private String name;
private String address;
private int poolLength;
private int capacity;
}
