package com.evertix.masterregister.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime timeIn;

    private LocalTime timeOut;

    public Schedule(LocalTime timeIn, LocalTime timeOut) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }
}
