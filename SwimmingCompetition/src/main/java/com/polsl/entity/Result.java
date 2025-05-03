package com.polsl.entity;


import java.sql.Time;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "results")
public class Result {
	@Id
	@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
	private Long resultId;
	
	@Column(name = "place")
	private Long place;
	
	@NotNull
	@Column(name = "result_time", nullable = false)
	private Time time;
	
	@NotNull
	@ManyToOne()
    @JoinColumn(name = "competitor_id", nullable = false)
    private Competitor competitor;
    
	@NotNull
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;
}
