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
    @Column(name = "raceId")
    private Long raceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "style", length = 20)
    private RaceStyle style;

    @Column(name = "distance")
    private int distance;
    
    @Column(name = "raceDate")
    private Timestamp date;
}