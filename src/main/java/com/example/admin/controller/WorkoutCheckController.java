package com.example.admin.controller;

import com.example.admin.DTO.WorkoutCheckDTO;
import com.example.admin.DTO.WorkoutCheckDTO.*;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.service.PersonChildService;
import com.example.admin.service.WorkoutCheckService;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkoutCheckController {
    private final WorkoutCheckService workoutCheckService;
    private final PersonChildRepository personChildRepository;
    private final PersonChildService personChildService;

    @PostMapping("/workoutcheck/add")
    public ResponseWorkoutCheck addWorkoutCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.create(
                token,
                request.getCheckedDay(),
                request.getWorkoutType(),
                request.getWorkoutName());
        if(workoutCheck == null) return null;
        return new ResponseWorkoutCheck(workoutCheck);
    }

    @PutMapping("/workoutcheck/update")
    public ResponseWorkoutCheck updateWorkoutCheck( // 안씀
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckUpdateRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.update(
                token,
                request.getWorkoutId(),
                request.getCheckedDay(),
                request.getWorkoutType(),
                request.getWorkoutName());
        if(workoutCheck == null) return null;
        return new ResponseWorkoutCheck(workoutCheck);
    }

    @DeleteMapping("/workoutcheck")
    public boolean deleteWorkoutCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckUpdateRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return false;
        return workoutCheckService.delete(
                token,
                request.getCheckedDay(),
                request.getWorkoutType(),
                request.getWorkoutName());
    }

    @GetMapping("/workoutcheck")
    public List<ResponseWorkoutCheck> getWorkoutCheckByKey(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByChild(token)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
    }

    @GetMapping("/workoutcheck-checkedDay")
    public List<ResponseWorkoutCheck> getWorkoutCheckByDateList(
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Authorization token: " + token);
        System.out.println("Date parameter: " + date);
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByDates(token, date)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
    }



    //부모 토큰으로 아이 운동 찾기
    @GetMapping("/child-workoutcheck-checkedDay")
    public ResponseWorkoutCheck getWorkoutCheckByDate(
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        WorkoutCheck response = workoutCheckService.findByDate(token, date);

        return new ResponseWorkoutCheck(response);
    }

    @GetMapping("/workoutcheck/{type}/{name}")
    public ResponseWorkoutCheck getWorkoutCheck(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date,
                                                @PathVariable("type") String type,
                                                @PathVariable("name") String name) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.find(token, date, type, name);
        return new ResponseWorkoutCheck(workoutCheck);
    }
}
