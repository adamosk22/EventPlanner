package com.example.eventplanner.services;

import com.example.eventplanner.dtos.GroupDto;
import com.example.eventplanner.entities.Activity;
import com.example.eventplanner.entities.Calendar;
import com.example.eventplanner.entities.Group;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.repositories.GroupRepository;
import com.example.eventplanner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional()
@Service
public class GroupService {
    private final UserService userService;
    private final ActivityService activityService;
    private final GroupRepository groupRepository;

    @Transactional
    public Group create(GroupDto dto){
        User user = userService.findByEmail(dto.getUserEmail());
        Group group = new Group();
        group.setCreator(user);
        user.getGroups().add(group);
        Set<User> users = new HashSet<>();
        users.add(user);
        group.setUsers(users);
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setCode(generateGroupCode());
        return groupRepository.save(group);
    }

    public GroupDto joinGroup(GroupDto dto){
        User user = userService.findByEmail(dto.getUserEmail());
        Group group = groupRepository.findByCode(dto.getCode());
        if(group!=null) {
            group.getUsers().add(user);
            user.getGroups().add(group);
            return new GroupDto(
                    group.getName(),
                    group.getDescription(),
                    group.getCode(),
                    user.getEmail());
        }
        else{
            return null;
        }
    }

    public List<GroupDto> findByEmail(String email){
        final List<Group> groups =  groupRepository.findByUsers_email(email);
        return groups.stream().map(group -> new GroupDto(
                group.getName(),
                group.getDescription(),
                group.getCode(),
                group.getCreator().getEmail()
        )).collect(Collectors.toList());

    }

    public String generateGroupCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
