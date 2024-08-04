package com.example.admin.controller;

import com.example.admin.DTO.WorkoutCheckDTO.*;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.service.PersonChildService;
import com.example.admin.service.WorkoutCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
    public ResponseWorkoutCheck updateWorkoutCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.update(
                token,
                request.getCheckedDay(),
                request.getWorkoutType(),
                request.getWorkoutName());
        return new ResponseWorkoutCheck(workoutCheck);
    }

    @DeleteMapping("/workoutcheck")
    public boolean deleteWorkoutCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckRequest request) {
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

    @GetMapping("/workoutcheck/{date}")
    public List<ResponseWorkoutCheck> getWorkoutCheckByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Authorization token: " + token);
        System.out.println("Date parameter: " + date);
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByDate(token, date)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
    }

    //@GetMapping("/workoutcheck/{uniqueKey}/{type}")
    //public List<ResponseWorkoutCheck> getWorkoutCheckByType(@PathVariable("uniqueKey") String uniqueKey, @PathVariable("type") String type) {
    //    PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
    //    if(personChild == null) return null;
    //    List<ResponseWorkoutCheck> response = new ArrayList<>();
    //    for(WorkoutCheck workoutCheck : workoutCheckService.findByType(uniqueKey, type)){
    //        response.add(new ResponseWorkoutCheck(workoutCheck));
    //    }
    //    return response;
    //}

    //@GetMapping("/workoutcheck/{uniqueKey}/{name}")
    //public List<ResponseWorkoutCheck> getWorkoutCheckByName(@PathVariable("uniqueKey") String uniqueKey, @PathVariable("name") String name) {
    //    PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
    //    if(personChild == null) return null;
    //    List<ResponseWorkoutCheck> response = new ArrayList<>();
    //    for(WorkoutCheck workoutCheck : workoutCheckService.findByName(uniqueKey, name)){
    //        response.add(new ResponseWorkoutCheck(workoutCheck));
    //    }
    //    return response;
    //}

    @GetMapping("/workoutcheck/{date}/{type}/{name}")
    public ResponseWorkoutCheck getWorkoutCheck(@RequestHeader("Authorization") String authorizationHeader,
                                                            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                            @PathVariable("type") String type,
                                                            @PathVariable("name") String name) {
        String token = authorizationHeader.replace("Bearer ", "");
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.find(token, date, type, name);
        return new ResponseWorkoutCheck(workoutCheck);
    }
}
