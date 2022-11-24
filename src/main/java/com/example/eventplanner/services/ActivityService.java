package com.example.eventplanner.services;

import com.example.eventplanner.dtos.ActivityDto;
import com.example.eventplanner.entities.Activity;
import com.example.eventplanner.entities.Calendar;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.repositories.ActivityRepository;
import com.example.eventplanner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional()
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserService userService;

    @Transactional
    public Activity create(ActivityDto dto){
        User user = userService.findByEmail(dto.getUserEmail());
        Calendar calendar = user.getCalendar();
        Activity activity = new Activity();
        activity.setCalendar(calendar);
        activity.setName(dto.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDateTime(), formatter);
        activity.setStartTime(startDateTime);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDateTime(), formatter);
        activity.setStartTime(endDateTime);
        return activityRepository.save(activity);
    }

    @Transactional
    public void remove(Long id){
        activityRepository.delete(findById(id));
    }

    public Activity findById(Long id){
        return activityRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<ActivityDto> findByEmail(String email, @PageableDefault Pageable pageable){
        User user = userService.findByEmail(email);
        Calendar calendar = user.getCalendar();
        final Page<Activity> activities = activityRepository.findByCalendar(calendar, pageable);
        return activities.map(activity -> new ActivityDto(
                activity.getStartTime().toString(),
                activity.getEndTime().toString(),
                activity.getName(),
                email,
                activity.getId()
        ));
    }

    @Transactional
    public Activity modify(ActivityDto dto){
        Activity searchedActivity = findById(dto.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDateTime(), formatter);
        searchedActivity.setStartTime(startDateTime);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDateTime(), formatter);
        searchedActivity.setEndTime(endDateTime);
        return searchedActivity;
    }
}
