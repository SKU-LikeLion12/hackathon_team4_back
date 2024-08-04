package com.example.admin.service;


import com.example.admin.DTO.MedicineDTO;
import com.example.admin.DTO.ScheduleMedicineDTO;
import com.example.admin.domain.*;
import com.example.admin.repository.MedicineCheckRepository;
import com.example.admin.repository.ScheduleMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScheduleMedicineService {

    private final ScheduleMedicineRepository scheduleMedicineRepository;
    private final PersonChildService personChildService;
    private final MedicineCheckRepository medicineCheckRepository;

    @Transactional
    public int addSchedule(String token, List<ScheduleMedicineDTO.RequestSchedule> request) {
        try{

            PersonChild personChild = personChildService.tokenToChild(token);


            for (ScheduleMedicineDTO.RequestSchedule sm : request) {
                    ScheduleMedicine scheduleMedicine = new ScheduleMedicine(
                            personChild,
                            sm.getTitle(),
                            sm.getMorning(),
                            sm.getLunch(),
                            sm.getDinner()
                    );
                    scheduleMedicineRepository.addSchedule(scheduleMedicine);

                    // ScheduleMedicine 저장 후 MedicineCheck 생성 및 저장
                    MedicineCheck medicineCheck = new MedicineCheck(scheduleMedicine);
                    medicineCheckRepository.save(medicineCheck);
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public List<ScheduleMedicine> getSchedule(String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        return scheduleMedicineRepository.getSchedule(personChild);
    }
}
