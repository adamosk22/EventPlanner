package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.EventDto;
import com.example.eventplanner.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody EventDto eventDto){ eventService.create(eventDto); }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAll(){ return eventService.findAll(); }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){ eventService.remove(id); }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void modify(@RequestBody EventDto eventDto) { eventService.modify(eventDto); }

    @GetMapping(value="/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getByEmail(@PathVariable String email){ return eventService.findByEmail(email); }

    @GetMapping(value="/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getByCategory(@PathVariable String category){ return eventService.findByCategory(category); }

    @GetMapping(value="/group/{code}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getByGroup(@PathVariable String code){ return eventService.findByGroup(code); }
}
