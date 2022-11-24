package com.example.eventplanner.services;

import com.example.eventplanner.dtos.ActivityDto;
import com.example.eventplanner.entities.Activity;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.repositories.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.eventplanner.entities.Calendar;

@RequiredArgsConstructor
@Transactional()
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public Calendar create(User user){
        Calendar calendar = new Calendar();
        calendar.setUser(user);
        return calendarRepository.save(calendar);
    }


}
