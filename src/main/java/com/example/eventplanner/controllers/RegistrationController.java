package com.example.eventplanner.controllers;

import com.example.eventplanner.dtos.UserDto;
import com.example.eventplanner.exceptions.UserAlreadyExistException;
import com.example.eventplanner.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/register")
    public String register(final Model model){
        model.addAttribute("userData", new UserDto());
        return "account/register";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public String userRegistration(final @Valid UserDto userDto, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userDto);
            return "account/register";
        }
        try {
            userService.register(userDto);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userDto);
            return "account/register";
        }
        return "http://localhost:4200/starter";
    }
}
