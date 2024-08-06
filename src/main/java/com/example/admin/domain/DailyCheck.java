package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Data;
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
@Data
public class DailyCheck {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PersonChild child;

    private LocalDate checkedDay;
    private boolean niceSleepDay;
    private boolean hardWorkout;
    private boolean takingMedicine;
    private boolean niceDailyMood;

    public DailyCheck(LocalDate checkedDay, PersonChild personChild) {
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
