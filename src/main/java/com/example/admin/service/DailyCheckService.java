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
}
