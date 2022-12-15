package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "calendars")
public class Calendar extends BaseEntity{
    @OneToOne(mappedBy = "calendar")
    User user;

    @OneToMany(mappedBy = "calendar")
    Set<Activity> activities = new LinkedHashSet<>();
}
