package com.example.eventplanner.repositories;

import com.example.eventplanner.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>, PagingAndSortingRepository<Category,Long> {
    Category findByName(String name);

    List<Category> findAll();
}
