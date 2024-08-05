package com.example.admin.service;

import com.example.admin.DTO.MedicineCheckDTO.*;
import com.example.admin.domain.MedicineCheck;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.ScheduleMedicine;
import com.example.admin.repository.MedicineCheckRepository;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.repository.ScheduleMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineCheckService {

    private final MedicineCheckRepository medicineCheckRepository;
    private final ScheduleMedicineRepository scheduleMedicineRepository;
    private final PersonChildRepository personChildRepository;
    private final PersonChildService personChildService;

    public ResponseMedicineCheck getMedicineCheck(Long id) {
        MedicineCheck medicineCheck = medicineCheckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine Check not found"));
        return new ResponseMedicineCheck(medicineCheck);
    }

    @Transactional
    public ResponseMedicineCheck createOrUpdateMedicineCheck(RequestMedicineCheck request) {
        ScheduleMedicine scheduleMedicine = scheduleMedicineRepository.findById(request.getScheduleMedicineId());

        MedicineCheck medicineCheck = medicineCheckRepository.findByScheduleMedicine(scheduleMedicine)
                .orElse(new MedicineCheck(scheduleMedicine));

        medicineCheck.setMorningTaken(request.isMorningTaken());
        medicineCheck.setLunchTaken(request.isLunchTaken());
        medicineCheck.setDinnerTaken(request.isDinnerTaken());

        medicineCheck = medicineCheckRepository.save(medicineCheck);
        return new ResponseMedicineCheck(medicineCheck);
    }

    @Transactional
    public void toggleMedicineCheck(RequestMedicineCheck request) {
        ScheduleMedicine scheduleMedicine = scheduleMedicineRepository.findById(request.getScheduleMedicineId());

        MedicineCheck medicineCheck = medicineCheckRepository.findByScheduleMedicine(scheduleMedicine)
                .orElseThrow(() -> new RuntimeException("Medicine Check not found"));

        switch (request.getTimeOfDay().toLowerCase()) {
            case "morning":
                medicineCheck.setMorningTaken(!medicineCheck.isMorningTaken());
                break;
            case "lunch":
                medicineCheck.setLunchTaken(!medicineCheck.isLunchTaken());
                break;
            case "dinner":
                medicineCheck.setDinnerTaken(!medicineCheck.isDinnerTaken());
                break;
            default:
                throw new IllegalArgumentException("Invalid time of day: " + request.getTimeOfDay());
        }

        medicineCheckRepository.save(medicineCheck);
    }



    public IntakeRate calculateIntakeRateForChild(String token) {
        PersonChild child = personChildService.tokenToChild(token);


        List<ScheduleMedicine> scheduleMedicines = scheduleMedicineRepository.getSchedule(child);

        int totalDoses = 0;
        int takenDoses = 0;

        for (ScheduleMedicine scheduleMedicine : scheduleMedicines) {
            MedicineCheck medicineCheck = medicineCheckRepository.findByScheduleMedicine(scheduleMedicine)
                    .orElseThrow(() -> new RuntimeException("Medicine Check not found"));

            if (scheduleMedicine.getMorning()) {
                totalDoses++;
                if (medicineCheck.isMorningTaken()) {
                    takenDoses++;
                }
            }
            if (scheduleMedicine.getLunch()) {
                totalDoses++;
                if (medicineCheck.isLunchTaken()) {
                    takenDoses++;
                }
            }
            if (scheduleMedicine.getDinner()) {
                totalDoses++;
                if (medicineCheck.isDinnerTaken()) {
                    takenDoses++;
                }
            }
        }

        double intakeRate = totalDoses == 0 ? 0 : ((double) takenDoses / totalDoses) * 100;

        return new IntakeRate(intakeRate);
    }


}
