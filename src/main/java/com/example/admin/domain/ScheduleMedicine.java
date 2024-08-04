package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class ScheduleMedicine {


    @Id @GeneratedValue
    private  Long id;

    private String medicineName;
    private boolean morning;
    private boolean lunch;
    private boolean dinner;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId")
    private PersonChild personChild;

    public ScheduleMedicine(   PersonChild personChild,   String medicineName, boolean morning, boolean lunch, boolean dinner ) {
        this.medicineName = medicineName;
        this.personChild = personChild;
        this.morning = morning;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public boolean getMorning() {
        return this.morning;
    }
    public boolean getLunch() {
        return this.lunch;
    }
    public boolean getDinner() {
        return this.dinner;
    }
}
