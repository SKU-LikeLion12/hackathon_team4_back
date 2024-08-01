package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
public class DailyCheck {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uniqueKey")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonChild child;

    private Date checkedDay;
    private boolean niceSleepDay;
    private boolean hardWorkout;
    private boolean takingMedicine;
    private boolean niceDailyMood;

    public DailyCheck(Date checkedDay, PersonChild personChild) {
        this.checkedDay = checkedDay;
        this.niceSleepDay = false;
        this.hardWorkout = false;
        this.takingMedicine = false;
        this.niceDailyMood = false;
        this.child = personChild;
    }

    public void updateSleep(boolean input){
        this.niceSleepDay = input;
    }

    public void updateHardWorkout(boolean input){
        this.hardWorkout = input;
    }
    public void updateTakingMedicine(boolean input){
        this.takingMedicine = input;
    }
    public void updateNiceDailyMood(boolean input){
        this.niceDailyMood = input;
    }
}
