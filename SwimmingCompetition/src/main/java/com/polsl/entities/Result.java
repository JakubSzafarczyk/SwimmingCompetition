package com.polsl.entities;


import java.sql.Time;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "results")
public class Result {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultId", nullable = false)
	private long resultId;
	
	@Column(name = "place")
	private long place;
	
	@Column(name = "resultTime", nullable = false)
	private Time time;
}
