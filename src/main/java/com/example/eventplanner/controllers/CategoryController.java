package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.CategoryDto;
import com.example.eventplanner.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create() { categoryService.create(); }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void addToEvent(@RequestBody CategoryDto categoryDto) { categoryService.addToEvent(categoryDto); }
}
