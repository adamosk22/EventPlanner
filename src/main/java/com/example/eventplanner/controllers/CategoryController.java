package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.CategoryDto;
import com.example.eventplanner.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create() { categoryService.create(); }

    @PatchMapping("/event")
    @ResponseStatus(HttpStatus.OK)
    public void addToEvent(@RequestBody CategoryDto categoryDto) { categoryService.addToEvent(categoryDto); }

    @PatchMapping("/group")
    @ResponseStatus(HttpStatus.OK)
    public void addToGroup(@RequestBody CategoryDto categoryDto) { categoryService.addToGroup(categoryDto); }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll() { return categoryService.getAll(); }

    @GetMapping("/group/{groupName}")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getByGroup(@PathVariable String groupName) { return categoryService.getByGroup(groupName); }
}
