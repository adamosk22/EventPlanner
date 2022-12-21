package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.Group;
import com.example.eventplanner.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface GroupRepository extends CrudRepository<Group, Long>, PagingAndSortingRepository<Group,Long> {
    Group findByCode(String code);

    List<Group> findByUsers_email(String email);

    Group findByName(String name);
}
