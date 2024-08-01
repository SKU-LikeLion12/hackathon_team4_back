package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class WorkoutCheck {
    @Id
    @GeneratedValue
    private Long id;

    private String uniqueKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonChild child;

    private Date checkedDay;
    private String workoutType;
    private String workoutName;

    public WorkoutCheck(PersonChild child, Date checkedDay, String workoutType, String workoutName, String uniqueKey){
        this.child = child;
        this.checkedDay = checkedDay;
        this.workoutType = workoutType;
        this.workoutName = workoutName;
        this.uniqueKey = uniqueKey;
    }
}
