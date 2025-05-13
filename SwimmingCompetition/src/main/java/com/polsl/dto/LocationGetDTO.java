package com.polsl.dto;

import org.springframework.hateoas.RepresentationModel;

import com.polsl.controller.LocationController;
import com.polsl.entity.Location;

import lombok.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
public class LocationGetDTO extends RepresentationModel<LocationGetDTO>{
public LocationGetDTO(Location location){
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
private Integer poolLength;
private Integer capacity;
}
