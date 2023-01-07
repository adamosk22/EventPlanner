package com.example.eventplanner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Comparable<EventDto>{
    @NotNull
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String startDateTime;

    @NotNull
    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String endDateTime;

    @NotNull
    @NotEmpty
    String name;

    String description;

    String location;

    String userEmail;

    String company;

    Long id;

    Long peopleInterested;

    String categories;

    @Override
    public int compareTo(EventDto e){
        return peopleInterested.compareTo(e.peopleInterested);
    }

}
