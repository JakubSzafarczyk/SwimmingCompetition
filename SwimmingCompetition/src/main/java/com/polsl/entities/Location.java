package com.polsl.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId")
	private long locationId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "address", length = 200)
	private String adderss;
	
	@Column(name = "poolLength")
	private int poolLength;
	
	@Column(name = "capacity")
	private int capacity;
}
