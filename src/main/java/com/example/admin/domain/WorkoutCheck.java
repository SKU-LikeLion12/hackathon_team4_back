package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class WorkoutCheck {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonChild child;

    private LocalDate checkedDay;
    private String workoutType;
    private String workoutName;

    public WorkoutCheck(PersonChild child, LocalDate checkedDay, String workoutType, String workoutName){
        this.child = child;
        this.checkedDay = checkedDay;
        this.workoutType = workoutType;
        this.workoutName = workoutName;
    }
}
