package com.example.eventplanner.services;

import com.example.eventplanner.dtos.EventDto;
import com.example.eventplanner.entities.*;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional()
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final ActivityService activityService;
    private final GroupRepository groupRepository;

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
                event.getId(),
                0L
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

    public List<EventDto> findByEmail(String email){
        List<Event> events = eventRepository.findAll();
        events = findForUser(email, events);
        Collections.sort(events);
        return events.stream().map(event -> new EventDto(
                event.getStartTime().toString(),
                event.getEndTime().toString(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getUser().getEmail(),
                event.getUser().getCompany(),
                event.getId(),
                0L
        )).collect(Collectors.toList());
    }

    public List<EventDto> findByCategory(String categoryName){
        List<Event> events = eventRepository.findByCategories_name(categoryName);
        return events.stream().map(event -> new EventDto(
                event.getStartTime().toString(),
                event.getEndTime().toString(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getUser().getEmail(),
                event.getUser().getCompany(),
                event.getId(),
                0L
        )).collect(Collectors.toList());
    }

    public List<EventDto> findByGroup(String code) {
        Group group = groupRepository.findByCode(code);
        List<Event> events = new ArrayList<>();
        Set<Category> categories = group.getCategories();
        for(Category category: categories){
            events.addAll(eventRepository.findByCategories_name(category.getName()));
        }
        events = events.stream()
                .distinct()
                .collect(Collectors.toList());
        List<EventDto> recommendedEventsDto = new ArrayList<>();
        Set<User> users = group.getUsers();
        for(User user: users){
            List<Event> userEvents = findForUser(user.getEmail(),events);
            for(Event event:userEvents){
                boolean notOnList = true;
                for(EventDto dto: recommendedEventsDto){
                    if(dto.getName()==event.getName()){
                        notOnList=false;
                        Long number = dto.getPeopleInterested();
                        dto.setPeopleInterested(number + 1);
                    }
                }
                if(notOnList){
                    recommendedEventsDto.add(new EventDto(
                            event.getStartTime().toString(),
                            event.getEndTime().toString(),
                            event.getName(),
                            event.getDescription(),
                            event.getLocation(),
                            event.getUser().getEmail(),
                            event.getUser().getCompany(),
                            event.getId(),
                            1L
                    ));
                }
            }
        }
        Collections.sort(recommendedEventsDto);
        return recommendedEventsDto;
    }

    private boolean checkRecurringOverlap(Event event, Activity activity){
        if(activity.getRecurring()){
            if(event.getStartTime().getDayOfWeek()==activity.getStartTime().getDayOfWeek()){
                if(event.getStartTime().getHour() <= activity.getEndTime().getHour() && event.getEndTime().getHour()>=activity.getStartTime().getHour()){
                    if(event.getStartTime().getMinute() <= activity.getEndTime().getMinute() && event.getEndTime().getMinute()>=activity.getStartTime().getMinute()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    List<Event> findForUser(String email, List<Event> events){
        List <Activity> activities = activityService.findActivities(email);
        events.removeIf(event -> (event.getStartTime().isBefore(LocalDateTime.now())));
        for(Activity activity : activities){
            events.removeIf(event -> (event.getStartTime().isBefore(activity.getEndTime()) && event.getEndTime().isAfter(activity.getStartTime())));
            events.removeIf(event -> (checkRecurringOverlap(event,activity)));
        }
        return events;
    }
}
