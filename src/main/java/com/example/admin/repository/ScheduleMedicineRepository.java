package com.example.admin.repository;


import com.example.admin.domain.PersonChild;
import com.example.admin.domain.ScheduleMedicine;

import java.util.List;
import java.util.Optional;

public interface ScheduleMedicineRepository {

    // 약 스케쥴 추가
    void addSchedule(ScheduleMedicine scheduleMedicine);


    List<ScheduleMedicine> getSchedule(PersonChild personChild);

    ScheduleMedicine findById(Long id);

    Optional<ScheduleMedicine> findByIdOp(Long id);
}
