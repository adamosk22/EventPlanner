package com.example.eventplanner.services;

import com.example.eventplanner.dtos.CategoryDto;
import com.example.eventplanner.entities.Category;
import com.example.eventplanner.entities.Event;
import com.example.eventplanner.repositories.CategoryRepository;
import com.example.eventplanner.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional()
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Transactional
    public void create(){
        List<String> categories = Arrays.asList(new String[]{"Music", "Theatre", "Sport", "Comedy", "Education", "Cinema", "Discussion", "Others"});
        categories.forEach(categoryName -> {
            Category category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
        });
    }

    public void addToEvent(CategoryDto dto){
        Event event = eventRepository.findByName(dto.getEventName());
        Category category = categoryRepository.findByName(dto.getName());
        event.getCategories().add(category);
        category.getEvents().add(event);
    }

    public List<CategoryDto> getAll(){
        final List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> (new CategoryDto(category.getName(), null, null)))
                .collect(Collectors.toList());
    }
}
