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
    
    @OneToMany(mappedBy = "race")
    private Set<Result> results = new HashSet<Result>();
    
    @ManyToMany
    @JoinTable(
        name = "race_competition",
        joinColumns = @JoinColumn(name = "race_id"),
        inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<Competition>();
}