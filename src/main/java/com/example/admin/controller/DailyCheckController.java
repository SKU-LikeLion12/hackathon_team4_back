package com.example.admin.controller;

import com.example.admin.DTO.DailyCheckDTO.*;
import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;
import com.example.admin.service.DailyCheckService;
import com.example.admin.service.PersonChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyCheckController {
    private final DailyCheckService dailyCheckService;
    private final PersonChildService personChildService;

    @PostMapping("/dailycheck/add")
    public ResponseDailyCheck addDailyCheck(@RequestBody DailyCheckRequest request) {
        PersonChild personChild = personChildService.findChildByUniqueKey(request.getUniqueKey());
        if(personChild == null) return null;
        DailyCheck dailyCheck = dailyCheckService.create(request.getCheckedDay(), request.getUniqueKey());
        return new ResponseDailyCheck(dailyCheck);
    }

    @PutMapping("/dailycheck/update")
    public ResponseDailyCheck updateDailyCheck(@RequestBody DailyCheckUpdateRequest request) {
        PersonChild personChild = personChildService.findChildByUniqueKey(request.getUniqueKey());
        if(personChild == null) return null;
        DailyCheck dailyCheck = dailyCheckService.update(request.getCheckedDay(), request.getUniqueKey(), request.isNiceSleepDay(), request.isHardWorkout(), request.isTakingMedicine(), request.isNiceDailyMood());
        return new ResponseDailyCheck(dailyCheck);
    }

    @GetMapping("/dailycheck/{uniqueKey}/{date}")
    public ResponseDailyCheck getDailyCheckByDate(@PathVariable("uniqueKey") String uniqueKey, @PathVariable("date") Date date) {
        DailyCheck dailyCheck = dailyCheckService.findByDate(date, uniqueKey);
        if(dailyCheck == null) return null;
        return new ResponseDailyCheck(dailyCheck);
    }

    @GetMapping("/dailycheck/{uniqueKey}")
    public List<ResponseDailyCheck> getAllDailyCheck(@PathVariable("uniqueKey") String uniqueKey) {
        List<ResponseDailyCheck> response = new ArrayList<>();
        for(DailyCheck dailyCheck : dailyCheckService.findByUserAll(uniqueKey)){
            response.add(new ResponseDailyCheck(dailyCheck));
        }
        return response;
    }

    @DeleteMapping("/dailycheck")
    public void deleteDailyCheck(@RequestBody DailyCheckRequest request){
        dailyCheckService.delete(request.getCheckedDay(), request.getUniqueKey());
    }
}
