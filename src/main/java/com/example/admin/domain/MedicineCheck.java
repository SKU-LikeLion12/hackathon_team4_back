package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MedicineCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_medicine_id")
    private ScheduleMedicine scheduleMedicine;

    private boolean morningTaken;
    private boolean lunchTaken;
    private boolean dinnerTaken;

    public MedicineCheck (ScheduleMedicine scheduleMedicine) {
        this.scheduleMedicine = scheduleMedicine;
        this.morningTaken = false;
        this.lunchTaken = false;
        this.dinnerTaken = false;
    }

    public void resetMedicineIntake() {
        this.morningTaken = false;
        this.lunchTaken = false;
        this.dinnerTaken = false;
    }
}
