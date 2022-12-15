package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User,Long> {
    Optional<User> findByEmail(String email);

    List<User> findByGroups_name(String name);
}
