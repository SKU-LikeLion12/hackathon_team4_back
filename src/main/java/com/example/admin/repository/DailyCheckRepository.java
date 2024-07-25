package com.example.admin.repository;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;

import java.util.Date;
import java.util.List;

public interface DailyCheckRepository {

    List<DailyCheck> findAll();

    DailyCheck findById(Long id);

    void deleteDailyCheck(DailyCheck dailyCheck);

    List<DailyCheck> findByUserAll(PersonChild child);

    DailyCheck findByDate(Date date, PersonChild child);
}
