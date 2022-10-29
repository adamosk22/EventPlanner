package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.DayOfWeek;

@Getter
@Setter
@Entity(name = "days")
public class Day extends BaseEntity{
    DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name="routine_id")
    private Routine routine;


}
