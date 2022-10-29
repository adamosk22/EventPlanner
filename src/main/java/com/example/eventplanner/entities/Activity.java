package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "activities")
public class Activity extends BaseEntity{
    @NotNull
    String name;

    @NotNull
    LocalDateTime time;

    @NotNull
    @ManyToOne
    @JoinColumn(name="calendar_id")
    Calendar calendar;
}
