package com.example.admin.repository;

import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;

import java.util.Date;
import java.util.List;

public interface WorkoutCheckReopsitory {
    WorkoutCheck create(WorkoutCheck workoutCheck);

    void delete(PersonChild child, Date date, String type, String name);

    WorkoutCheck findById(PersonChild personChild, Long id);

    List<WorkoutCheck> findByChild(PersonChild personChild);

    List<WorkoutCheck> findByDate(PersonChild child, Date date);

    List<WorkoutCheck> findByType(PersonChild child, String type);

    List<WorkoutCheck> findByName(PersonChild child, String name);

    WorkoutCheck find(PersonChild child, Date date, String type, String name);
}
