package com.polsl.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "competitions")
public class Competition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id", nullable = false)
	private Long competitionId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "start_date")
	private Timestamp startDate;
	
	@Column(name = "end_date")
	private Timestamp endDate;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
	
	@ManyToMany(mappedBy = "competitions", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Race> races = new HashSet<Race>();
    
    @ManyToMany(mappedBy = "competitions", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Competitor> competitors = new HashSet<Competitor>();
}
