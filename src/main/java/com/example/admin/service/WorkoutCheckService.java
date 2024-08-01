package com.example.admin.service;

import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.repository.WorkoutCheckReopsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutCheckService {
    private final WorkoutCheckReopsitory workoutCheckReopsitory;
    private final PersonChildRepository personChildRepository;

    @Transactional
    public WorkoutCheck create(String uniqueKey, Date date, String type, String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if (personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckReopsitory.find(personChild, date, type, name);
        if(workoutCheck != null) return null;
        return workoutCheckReopsitory.create(new WorkoutCheck(personChild, date, type, name));
    }

    @Transactional
    public WorkoutCheck update(String uniqueKey, Date date, String type, String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if (personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckReopsitory.find(personChild, date, type, name);
        if(workoutCheck == null) return null;
        workoutCheck.setCheckedDay(date);
        workoutCheck.setWorkoutType(type);
        workoutCheck.setWorkoutName(name);
        return workoutCheck;
    }

    @Transactional
    public void delete(String uniqueKey, Date date, String type, String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return;
        workoutCheckReopsitory.delete(personChild, date, type, name);
    }

    @Transactional
    public List<WorkoutCheck> findByChild(String uniqueKey) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByChild(personChild);
    }

    @Transactional
    public List<WorkoutCheck> findByDate(String uniqueKey, Date date) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByDate(personChild, date);
    }

    @Transactional
    public List<WorkoutCheck> findByType(String uniqueKey, String type) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByType(personChild, type);
    }

    @Transactional
    public List<WorkoutCheck> findByName(String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(name);
        if(personChild == null) return null;
        return workoutCheckReopsitory.findByChild(personChild);
    }

    @Transactional
    public WorkoutCheck find(String uniqueKey, Date date, String type, String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return workoutCheckReopsitory.find(personChild, date, type, name);
    }
}
