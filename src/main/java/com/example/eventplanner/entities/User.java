package com.example.eventplanner.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseEntity{
    @Email
    @Column(unique=true)
    private String email;

    @NotNull
    @Column(name="first_name")
    private String firstName;

    @NotNull
    @Column(name="last_name")
    private String lastName;

    private String password;

    @NotNull
    private String role;

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new LinkedHashSet<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @OneToMany(mappedBy = "user")
    private Set <Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "creator")
    Set<Group> createdGroups;



}
