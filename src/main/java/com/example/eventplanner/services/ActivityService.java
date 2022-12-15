package com.example.eventplanner.services;

import com.example.eventplanner.dtos.ActivityDto;
import com.example.eventplanner.entities.Activity;
import com.example.eventplanner.entities.Calendar;
import com.example.eventplanner.entities.User;
import com.example.eventplanner.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
        activity.setEndTime(endDateTime);
        activity.setRecurring(dto.getRecurring());
        activity.setIsEvent(dto.getIsEvent());
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

    public List<ActivityDto> findByEmail(String email){
        final List<Activity> activities = findActivities(email);
        return activities.stream().map(activity -> new ActivityDto(
                activity.getStartTime().toString(),
                activity.getEndTime().toString(),
                activity.getName(),
                email,
                activity.getRecurring(),
                activity.getIsEvent(),
                activity.getId()
        )).collect(Collectors.toList());
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

    public List<Activity> findActivities(String email){
        User user = userService.findByEmail(email);
        Calendar calendar = user.getCalendar();
        return activityRepository.findByCalendar(calendar);
    }
}
