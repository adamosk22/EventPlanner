package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseEntity{
    @NotNull
    @Column(unique = true)
    String name;

    @NotNull
    @ManyToMany(mappedBy = "categories")
    Set<Event> events;

    @NotNull
    @ManyToMany(mappedBy = "categories")
    Set<Group> group;

}
