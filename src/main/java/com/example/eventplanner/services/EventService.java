package com.example.eventplanner.services;

import com.example.eventplanner.dtos.EventDto;
import com.example.eventplanner.entities.Event;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional()
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;

    @Transactional
    public Event create(EventDto dto){
        User user = userService.findByEmail(dto.getUserEmail());
        Event event = new Event();
        event.setUser(user);
        event.setName(dto.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDateTime(), formatter);
        event.setStartTime(startDateTime);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDateTime(), formatter);
        event.setEndTime(endDateTime);
        event.setLocation(dto.getLocation());
        event.setDescription(dto.getDescription());
        return eventRepository.save(event);
    }

    public Event findById(Long id){
        return eventRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<EventDto> findAll(){
        final List<Event> events = eventRepository.findAll();
        events.removeIf(event -> (event.getStartTime().isAfter(LocalDateTime.now())));
        Collections.sort(events);
        return events.stream().map(event -> new EventDto(
                event.getStartTime().toString(),
                event.getEndTime().toString(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getUser().getEmail(),
                event.getUser().getCompany(),
                event.getId()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void remove(Long id){
        eventRepository.delete(findById(id));
    }

    @Transactional
    public Event modify(EventDto dto){
        Event searchedEvent = findById(dto.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDateTime(), formatter);
        searchedEvent.setStartTime(startDateTime);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDateTime(), formatter);
        searchedEvent.setEndTime(endDateTime);
        return searchedEvent;
    }
}
