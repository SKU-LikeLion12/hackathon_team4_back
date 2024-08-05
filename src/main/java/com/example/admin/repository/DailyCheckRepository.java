package com.example.admin.repository;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;

import java.util.Date;
import java.util.List;

public interface DailyCheckRepository {

    DailyCheck create(DailyCheck dailyCheck);

    List<DailyCheck> findAll();

    DailyCheck findById(Long id);

    boolean deleteDailyCheck(Date date, PersonChild child);

    List<DailyCheck> findByUserAll(PersonChild child);

    DailyCheck findByDate(Date date, PersonChild child);
}
