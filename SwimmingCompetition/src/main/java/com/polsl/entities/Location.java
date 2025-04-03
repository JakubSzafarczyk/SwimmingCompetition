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
    @Column(name = "locationId", nullable = false)
	private long locationId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "address", nullable = false, length = 200)
	private String adderss;
	
	@Column(name = "poolLength", nullable = false)
	private int poolLength;
	
	@Column(name = "capacity")
	private int capacity;
}
