package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.Activity;
import com.example.eventplanner.entities.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long>, PagingAndSortingRepository<Activity,Long> {
    public List<Activity> findByCalendar(Calendar calendar);
}
