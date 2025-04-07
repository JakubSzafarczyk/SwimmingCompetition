package com.polsl.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "competitions")
public class Competition {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id", nullable = false)
	private Long competitionId;
	
	@NotNull
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
	
<<<<<<< HEAD
	@ManyToMany(mappedBy = "competitions", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
=======
	@OneToMany(mappedBy = "competition", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
>>>>>>> 448c9ecd339c36cef4fc4c2cca62661deed0f751
    private Set<Race> races = new HashSet<Race>();
    
    @ManyToMany(mappedBy = "competitions", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Competitor> competitors = new HashSet<Competitor>();
}
