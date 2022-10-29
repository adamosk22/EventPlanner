package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity(name = "routines")
public class Routine extends BaseEntity{
    @NotNull
    String name;

    @NotNull
    LocalDateTime start;

    @NotNull
    LocalDateTime stop;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.REMOVE)
    Set<Day> recurrence;

    @NotNull
    @ManyToOne
    @JoinColumn(name="calendar_id")
    Calendar calendar;
}
