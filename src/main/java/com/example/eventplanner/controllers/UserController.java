package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.UserDto;
import com.example.eventplanner.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto userDTO){
        userService.create(userDTO);
    }

    @GetMapping(value = "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable String email){ return userService.findUser(email); }

    @GetMapping(value = "/group/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getByGroup(@PathVariable String name){ return userService.findByGroup(name); }

}
