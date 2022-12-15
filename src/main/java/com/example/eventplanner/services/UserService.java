package com.example.eventplanner.services;

import com.example.eventplanner.dtos.UserDto;
import com.example.eventplanner.entities.Calendar;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.enums.Role;
import com.example.eventplanner.exceptions.UserAlreadyExistException;
import com.example.eventplanner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional()
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final CalendarService calendarService;

    public void register(UserDto user) throws UserAlreadyExistException {
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        userRepository.save(userEntity);
    }


    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) !=null ? true : false;
    }

    private void encodePassword(User userEntity, UserDto user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Transactional
    public User create(UserDto userDTO){
        final User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        if(user.getRole()=="ORGANIZER")
            user.setCompany(userDTO.getCompany());
        Calendar calendar = calendarService.create(user);
        user.setCalendar(calendar);
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

    }

    public UserDto findUser(String email){
        User user = findByEmail(email);
        return new UserDto(user.getFirstName(),
                user.getLastName(),
                null,
                user.getEmail(),
                user.getRole(),
                user.getCompany());
    }

    public List<UserDto> findByGroup(String groupName){
        final List<User> users = userRepository.findByGroups_name(groupName);
        return users.stream().map(user -> new UserDto(user.getFirstName(),
                user.getLastName(),
                null,
                user.getEmail(),
                user.getRole(),
                user.getCompany())).collect(Collectors.toList());
    }
}
