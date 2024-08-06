package com.example.admin.service;

import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.repository.WorkoutCheckReopsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutCheckService {
    private final WorkoutCheckReopsitory workoutCheckReopsitory;
    private final PersonChildRepository personChildRepository;
    private final PersonChildService personChildService;

    @Transactional
    public WorkoutCheck create(String token, LocalDate date, String type, String name) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if (personChild == null) return null;
        WorkoutCheck workoutCheck = find(token, date, type, name);
        if(workoutCheck != null) return null;
        return workoutCheckReopsitory.create(new WorkoutCheck(personChild, date, type, name));
    }

    @Transactional
    public WorkoutCheck update(String token, Long id, LocalDate date, String type, String name) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if (personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckReopsitory.findById(personChild , id);
        if(workoutCheck == null) return null;
        workoutCheck.setCheckedDay(date);
        workoutCheck.setWorkoutType(type);
        workoutCheck.setWorkoutName(name);
        return workoutCheck;
    }

    @Transactional
    public boolean delete(String token, LocalDate date, String type, String name) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return false;
        workoutCheckReopsitory.delete(personChild, date, type, name);
        return true;
    }

    @Transactional
    public List<WorkoutCheck> findByChild(String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByChild(personChild);
    }

    @Transactional
    public List<WorkoutCheck> findByDate(String token, LocalDate date) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByDate(personChild, date);
    }

    @Transactional
    public List<WorkoutCheck> findByType(String token, String type) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByType(personChild, type);
    }

    @Transactional
    public List<WorkoutCheck> findByName(String token, String name) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByChild(personChild);
    }

    @Transactional
    public WorkoutCheck find(String token, LocalDate date, String type, String name) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return workoutCheckReopsitory.find(personChild, date, type, name);
    }
}
