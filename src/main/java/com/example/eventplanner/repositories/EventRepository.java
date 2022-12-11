package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event,Long> {
    public List<Event> findAll();
}
