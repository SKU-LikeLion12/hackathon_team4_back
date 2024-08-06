package com.example.admin.service;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import com.example.admin.repository.DailyCheckRepository;
import com.example.admin.repository.PersonChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyCheckService {
    private final DailyCheckRepository dailyCheckRepository;
    private final PersonChildRepository personChildRepository;
    private final PersonChildService personChildService;
    private final ParentsService parentsService;

//    @Transactional
//    public DailyCheck create(Date date, String token) {
//        DailyCheck dailyCheck = findByDate(date, token);
//        if (dailyCheck != null) return null;
//        return dailyCheckRepository.create(new DailyCheck(date, personChildService.tokenToChild(token)));
//    }

    @Transactional
    public List<DailyCheck> findByUserAll(String token) {
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        return dailyCheckRepository.findByUserAll(personChild);
    }

    @Transactional
    public DailyCheck findByDate(LocalDate date, String token) {
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
    public DailyCheck update(LocalDate date, String token, boolean niceSleepDay, boolean hardWorkout, boolean takingMedicine, boolean niceDailyMood) {
        DailyCheck dailyCheck = findByDate(date, token);
        if (dailyCheck == null) return null;
        dailyCheck.updateSleep(niceSleepDay);
        dailyCheck.updateHardWorkout(hardWorkout);
        dailyCheck.updateTakingMedicine(takingMedicine);
        dailyCheck.updateNiceDailyMood(niceDailyMood);
        return dailyCheck;
    }


    @Transactional
    public DailyCheck create(LocalDate checkedDay, String token, boolean niceSleepDay, boolean hardWorkout, boolean takingMedicine, boolean niceDailyMood) {
        // 주어진 토큰으로 PersonChild 객체를 찾습니다.
        PersonChild child = personChildService.tokenToChild(token);

        // 새로운 DailyCheck를 생성하고 값 설정
        DailyCheck dailyCheck = new DailyCheck();
        dailyCheck.setChild(child);
        dailyCheck.setCheckedDay(checkedDay);
        dailyCheck.setNiceSleepDay(niceSleepDay);
        dailyCheck.setHardWorkout(hardWorkout);
        dailyCheck.setTakingMedicine(takingMedicine);
        dailyCheck.setNiceDailyMood(niceDailyMood);

        // DailyCheck를 저장하고 반환
        dailyCheckRepository.create(dailyCheck);
        return dailyCheck;
    }



    public boolean delete(LocalDate date, String token) {
        DailyCheck dailyCheck = findByDate(date, token);
        PersonChild personChild = personChildService.tokenToChild(token);
        if (dailyCheck == null || personChild == null) return false;
        return dailyCheckRepository.deleteDailyCheck(date, personChild);
    }

    public DailyCheck getChildDailyCheck(String parentToken, LocalDate checkedDay) {
        Parents parent = parentsService.tokenToParents(parentToken);

        PersonChild child = personChildService.findChildByParent(parent);

        DailyCheck dailyCheck = dailyCheckRepository.findByDate(checkedDay, child);

        if (dailyCheck == null) {
            System.out.println("DailyCheck not found for date: " + checkedDay + " and child: " + child.getId());
        }

        return dailyCheck;

//        return dailyCheckRepository.findByDate(checkedDay, child);
    }
}
