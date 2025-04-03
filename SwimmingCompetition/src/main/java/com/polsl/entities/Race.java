package com.polsl.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;
import com.polsl.model.RaceStyle;

@Getter
@Setter
@Entity
public class Race {
    @Id
    @GeneratedValue
    private Long raceId;

    @Enumerated(EnumType.STRING)
    private RaceStyle style;

    private int distance;
    private Timestamp date;
}