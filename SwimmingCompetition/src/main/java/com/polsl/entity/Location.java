package com.polsl.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
	private long locationId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "address", nullable = false, length = 200)
	private String address;
	
	@Column(name = "pool_length", nullable = false)
	private int poolLength;
	
	@Column(name = "capacity")
	private int capacity;
	
	@OneToMany(mappedBy = "location")
    private Set<Competition> competitions = new HashSet<Competition>();
}
