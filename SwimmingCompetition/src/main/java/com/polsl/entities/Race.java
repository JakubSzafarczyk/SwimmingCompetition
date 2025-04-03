package com.polsl.entities;

import java.sql.Timestamp;

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
    @Column(name = "raceId", nullable = false)
    private Long raceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "style", nullable = false, length = 20)
    private RaceStyle style;

    @Column(name = "distance", nullable = false)
    private int distance;
    
    @Column(name = "raceDate", nullable = false)
    private Timestamp date;
}