package com.example.admin.service;

import com.example.admin.DTO.MedicineCheckDTO.*;
import com.example.admin.domain.MedicineCheck;
import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.ScheduleMedicine;
import com.example.admin.repository.MedicineCheckRepository;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.repository.ScheduleMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
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
    private final ParentsService parentsService;


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


    public List<MedicineCheckResponseDTO> getChildMedicineCheck(String parentToken) {
        // 1. 토큰으로부터 부모 객체를 가져옴
        Parents parent = parentsService.tokenToParents(parentToken);

        // 2. 부모의 자녀를 가져옴 (1:1 관계)
        PersonChild child = personChildRepository.findByParent(parent);

        if(child == null) return null;

        // 3. 자녀의 ScheduleMedicine 및 MedicineCheck를 비교하여 복용 여부를 확인
        List<MedicineCheckResponseDTO> responseList = new ArrayList<>();
        for (ScheduleMedicine scheduleMedicine : scheduleMedicineRepository.getSchedule(child)) {
            Optional<MedicineCheck> checkOpt = medicineCheckRepository.findByScheduleMedicine(scheduleMedicine);

            if (checkOpt.isPresent()) {
                MedicineCheck check = checkOpt.get();

                MedicineCheckResponseDTO responseDTO = new MedicineCheckResponseDTO();
                responseDTO.setId(scheduleMedicine.getId());
                responseDTO.setName(scheduleMedicine.getMedicineName());

                // 각 시간대별로 매칭 여부 확인 및 결과에 추가
                if (scheduleMedicine.getMorning() && check.isMorningTaken()) {
                    responseDTO.setMorning(true);
                }
                if (scheduleMedicine.getLunch() && check.isLunchTaken()) {
                    responseDTO.setLunch(true);
                }
                if (scheduleMedicine.getDinner() && check.isDinnerTaken()) {
                    responseDTO.setDinner(true);
                }

                // DTO에 값이 하나라도 있으면 리스트에 추가
                if (responseDTO.getMorning() != null || responseDTO.getLunch() != null || responseDTO.getDinner() != null) {
                    responseList.add(responseDTO);
                }
            }
        }
        return responseList;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void resetAllMedicineIntakes() {
        List<MedicineCheck> allMedicineChecks = medicineCheckRepository.findAll();

        for (MedicineCheck medicineCheck : allMedicineChecks) {
            medicineCheck.resetMedicineIntake();
        }

        // 저장하여 변경 사항을 적용
        medicineCheckRepository.saveAll(allMedicineChecks);
    }
}
