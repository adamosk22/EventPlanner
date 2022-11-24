package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CalendarRepository extends CrudRepository<Calendar, Long>, PagingAndSortingRepository<Calendar,Long> {
}
