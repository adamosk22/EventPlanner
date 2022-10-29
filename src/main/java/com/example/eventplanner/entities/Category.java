package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseEntity{
    @NotNull
    String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name="event_id")
    Event event;

    @NotNull
    @ManyToOne
    @JoinColumn(name="group_id")
    Group group;

}
