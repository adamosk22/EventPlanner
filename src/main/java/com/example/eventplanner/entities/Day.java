package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.DayOfWeek;

@Getter
@Setter
@Entity(name = "days")
public class Day extends BaseEntity{
    DayOfWeek dayOfWeek;
}
