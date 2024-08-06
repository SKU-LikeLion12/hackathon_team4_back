package com.example.admin.repository;

import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface WorkoutCheckReopsitory {
    WorkoutCheck create(WorkoutCheck workoutCheck);

    void delete(PersonChild child, LocalDate date, String type, String name);

    WorkoutCheck findById(PersonChild personChild, Long id);

    List<WorkoutCheck> findByChild(PersonChild personChild);

    WorkoutCheck findByDate(PersonChild child, LocalDate date);

    List<WorkoutCheck> findByDates(PersonChild child, LocalDate date);

    List<WorkoutCheck> findByType(PersonChild child, String type);

    List<WorkoutCheck> findByName(PersonChild child, String name);

    WorkoutCheck find(PersonChild child, LocalDate date, String type, String name);
}
