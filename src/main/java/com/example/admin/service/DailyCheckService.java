package com.example.admin.service;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;
import com.example.admin.repository.DailyCheckRepository;
import com.example.admin.repository.PersonChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyCheckService {
    private final DailyCheckRepository dailyCheckRepository;
    private final PersonChildRepository personChildRepository;

    @Transactional
    public DailyCheck create(Date date, String uniqueKey) {
        DailyCheck dailyCheck = findByDate(date, uniqueKey);
        if (dailyCheck != null) return null;
        return dailyCheckRepository.create(new DailyCheck(date, personChildRepository.findByUniqueKey(uniqueKey)));
    }

    @Transactional
    public List<DailyCheck> findByUserAll(String uniqueKey) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return dailyCheckRepository.findByUserAll(personChild);
    }

    @Transactional
    public DailyCheck findByDate(Date date, String uniqueKey) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        return dailyCheckRepository.findByDate(date, personChild);
    }

    @Transactional
    public DailyCheck update(Date date, String uniqueKey, boolean niceSleepDay, boolean hardWorkout, boolean takingMedicine, boolean niceDailyMood) {
        DailyCheck dailyCheck = findByDate(date, uniqueKey);
        if (dailyCheck == null) return null;
        dailyCheck.updateNiceDailyMood(niceSleepDay);
        dailyCheck.updateHardWorkout(hardWorkout);
        dailyCheck.updateTakingMedicine(takingMedicine);
        dailyCheck.updateNiceDailyMood(niceDailyMood);
        return dailyCheck;
    }

    @Transactional
    public void delete(Date date, String uniqueKey) {
        DailyCheck dailyCheck = findByDate(date, uniqueKey);
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if (dailyCheck == null || personChild == null) return;
        dailyCheckRepository.deleteDailyCheck(date, personChild);
    }
}
