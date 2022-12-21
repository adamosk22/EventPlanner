package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.GroupDto;
import com.example.eventplanner.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody GroupDto groupDto){ groupService.create(groupDto); }

    @PatchMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public GroupDto joinGroup(@RequestBody GroupDto groupDto){ return groupService.joinGroup(groupDto); }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupDto> getByEmail(@PathVariable String email) { return groupService.findByEmail(email); }
}
