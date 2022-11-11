package com.example.eventplanner.entities;

import com.example.eventplanner.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Email
    @NotNull
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

    @Override
    public Collection<?extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
