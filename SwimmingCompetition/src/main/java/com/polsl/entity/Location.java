package com.polsl.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
	private Long locationId;
	
	@NotNull
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@NotNull
	@Column(name = "address", nullable = false, length = 200)
	private String address;
	
	@NotNull
	@Column(name = "pool_length", nullable = false)
	private Integer poolLength;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Competition> competitions = new HashSet<Competition>();
}
