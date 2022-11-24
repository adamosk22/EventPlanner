package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.ActivityDto;
import com.example.eventplanner.dtos.UserDto;
import com.example.eventplanner.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/activities")
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ActivityDto activityDto){
        activityService.create(activityDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){ activityService.remove(id); }

    @GetMapping(value = "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityDto> findByEmail(@PathVariable String email, @PageableDefault Pageable pageable) { return activityService.findByEmail(email, pageable); }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void modify(@RequestBody ActivityDto activityDto) { activityService.modify(activityDto); }


}
