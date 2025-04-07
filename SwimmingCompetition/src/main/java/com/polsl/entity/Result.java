package com.polsl.entity;


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
    @Column(name = "result_id", nullable = false)
	private Long resultId;
	
	@Column(name = "place")
	private long place;
	
	@Column(name = "result_time", nullable = false)
	private Time time;
	
	@ManyToOne
    @JoinColumn(name = "competitor_id", nullable = false)
    private Competitor competitor;
    
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;
}
