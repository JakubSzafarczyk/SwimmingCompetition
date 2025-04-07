package com.polsl.entity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;
import lombok.*;
import com.polsl.model.RaceStyle;

@Getter
@Setter
@Entity
@Table(name = "races")
public class Race {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id", nullable = false)
    private Long raceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false, length = 20)
    private RaceStyle style;

    @Column(name = "distance", nullable = false)
    private int distance;
    
    @Column(name = "race_date", nullable = false)
    private Timestamp date;
    
    @OneToMany(mappedBy = "race", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Result> results = new HashSet<Result>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "race_competition",
        joinColumns = @JoinColumn(name = "race_id"),
        inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions = new HashSet<Competition>();
}