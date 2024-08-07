package com.example.admin.controller;

import com.example.admin.DTO.DailyCheckDTO.*;
import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;
import com.example.admin.service.DailyCheckService;
import com.example.admin.service.PersonChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyCheckController {
    private final DailyCheckService dailyCheckService;
    private final PersonChildService personChildService;

    @PostMapping("/dailycheck/add")
    public ResponseDailyCheck addDailyCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DailyCheckRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        DailyCheck dailyCheck = dailyCheckService.create(
                request.getCheckedDay(),
                token,
                request.isNiceSleepDay(),
                request.isHardWorkout(),
                request.isTakingMedicine(),
                request.isNiceDailyMood()
        );
        if(dailyCheck == null) return null;
        return new ResponseDailyCheck(dailyCheck);
    }

    @PutMapping("/dailycheck/update")
    public ResponseDailyCheck updateDailyCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DailyCheckRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        DailyCheck dailyCheck = dailyCheckService.update(
                request.getCheckedDay(),
                token,
                request.isNiceSleepDay(),
                request.isHardWorkout(),
                request.isTakingMedicine(),
                request.isNiceDailyMood());
        return new ResponseDailyCheck(dailyCheck);
    }

    @GetMapping("/dailycheck-checkday")
    public ResponseDailyCheck getDailyCheckByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate checkedDay) {
        String token = authorizationHeader.replace("Bearer ", "");
        DailyCheck dailyCheck = dailyCheckService.findByDate(checkedDay, token);
        if(dailyCheck == null) return null;
        return new ResponseDailyCheck(dailyCheck);
    }

    @GetMapping("/dailycheck")
    public List<ResponseDailyCheck> getAllDailyCheck(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        List<ResponseDailyCheck> response = new ArrayList<>();
        for(DailyCheck dailyCheck : dailyCheckService.findByUserAll(token)){
            response.add(new ResponseDailyCheck(dailyCheck));
        }
        return response;
    }

    @DeleteMapping("/dailycheck")
    public boolean deleteDailyCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DailyCheckRequest request){
        String token = authorizationHeader.replace("Bearer ", "");
        return dailyCheckService.delete(request.getCheckedDay(), token);
    }

    @GetMapping("/child-daily-check")
    public ResponseDailyCheck getChildDailyCheck(
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkedDay,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        DailyCheck dailyCheck = dailyCheckService.getChildDailyCheck(token, checkedDay);

        if (dailyCheck == null) {
//            throw new RuntimeException("Daily Check not found for the given date");
            return null;
        }

        return new ResponseDailyCheck(dailyCheck);
    }
}