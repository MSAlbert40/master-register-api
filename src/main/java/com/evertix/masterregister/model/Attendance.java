package com.evertix.masterregister.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
@NoArgsConstructor
@Getter
@Setter
public class Attendance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @Min(value = 0)
    private Integer attendance;

    @Min(value = 0)
    private Integer late;

    @Min(value = 0)
    private Integer absent;

    @Min(value = 0)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User employee;

    public Attendance(LocalDate date, LocalTime time, Integer attendance, Integer late,
                      Integer absent, Integer quantity) {
        this.date = date;
        this.time = time;
        this.attendance = attendance;
        this.late = late;
        this.absent = absent;
        this.quantity = quantity;
    }
}
