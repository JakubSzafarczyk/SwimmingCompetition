package com.polsl.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.polsl.model.RaceStyle;

@Getter
@Setter
@Entity
@Table(name = "races")
public class Race {
	@Id
	@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id", nullable = false)
    private Long raceId;

	@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false, length = 20)
    private RaceStyle style;

	@NotNull
    @Column(name = "distance", nullable = false)
    private int distance;
    
	@NotNull
    @Column(name = "race_date", nullable = false)
    private Timestamp date;
    
    @OneToMany(mappedBy = "race", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Result> results = new HashSet<Result>();
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "competition_id")
    private Competition competition;
}