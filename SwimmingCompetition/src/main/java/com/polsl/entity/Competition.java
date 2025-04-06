package com.polsl.entity;

import java.sql.Timestamp;
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
	private long competitionId;
	
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
}
