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
@Entity(name = "events")
public class Event extends BaseEntity implements Comparable<Event>{
    @NotNull
    String name;

    @NotNull
    LocalDateTime startTime;

    @NotNull
    LocalDateTime endTime;

    String description;

    String location;

    @NotNull
    @ManyToOne
    @JoinColumn(name="calendar_id")
    Calendar calendar;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @Override
    public int compareTo(Event e) {
        if (getStartTime() == null || e.getStartTime() == null) {
            return 0;
        }
        return getStartTime().compareTo(e.getStartTime());
    }
}
