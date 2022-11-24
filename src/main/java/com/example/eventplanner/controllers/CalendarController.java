package com.example.eventplanner.controllers;

import com.example.eventplanner.services.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CalendarController {
    CalendarService calendarService;


}
