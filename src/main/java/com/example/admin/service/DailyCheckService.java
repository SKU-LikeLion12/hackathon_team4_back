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
    private final PersonChildService personChildService;

    @Transactional
    public DailyCheck create(Date date, String token) {
        DailyCheck dailyCheck = findByDate(date, token);
        if (dailyCheck != null) return null;
        return dailyCheckRepository.create(new DailyCheck(date, personChildService.tokenToChild(token)));
    }

    @Transactional
    public List<DailyCheck> findByUserAll(String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return dailyCheckRepository.findByUserAll(personChild);
    }

    @Transactional
    public DailyCheck findByDate(Date date, String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return dailyCheckRepository.findByDate(date, personChild);
    }

    @Transactional
    public DailyCheck findById(Long id, String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return dailyCheckRepository.findById(id);
    }

    @Transactional
    public DailyCheck update(Date date, String token, boolean niceSleepDay, boolean hardWorkout, boolean takingMedicine, boolean niceDailyMood) {
        DailyCheck dailyCheck = findByDate(date, token);
        if (dailyCheck == null) return null;
        dailyCheck.updateSleep(niceSleepDay);
        dailyCheck.updateHardWorkout(hardWorkout);
        dailyCheck.updateTakingMedicine(takingMedicine);
        dailyCheck.updateNiceDailyMood(niceDailyMood);
        return dailyCheck;
    }

    @Transactional
    public boolean delete(Date date, String token) {
        DailyCheck dailyCheck = findByDate(date, token);
        PersonChild personChild = personChildService.tokenToChild(token);
        if (dailyCheck == null || personChild == null) return false;
        return dailyCheckRepository.deleteDailyCheck(date, personChild);
    }
}
