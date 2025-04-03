package com.polsl.entities;

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
    @Column(name = "competitionId", nullable = false)
	private long competitionId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "startDate")
	private Timestamp startDate;
	
	@Column(name = "endDate")
	private Timestamp endDate;
	
	@Column(name = "description", length = 500)
	private String description;
}
