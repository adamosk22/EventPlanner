package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity(name = "events")
public class Event extends BaseEntity implements Comparable<Event>{
    @NotNull
    @Column(unique=true)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_categories",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    Set<Category> categories;

    @ManyToMany(mappedBy = "events")
    Set<Group> groups;

    @Override
    public int compareTo(Event e) {
        if (getStartTime() == null || e.getStartTime() == null) {
            return 0;
        }
        return getStartTime().compareTo(e.getStartTime());
    }
}
