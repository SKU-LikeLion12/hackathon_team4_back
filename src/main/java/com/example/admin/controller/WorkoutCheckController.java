package com.example.admin.controller;

import com.example.admin.DTO.WorkoutCheckDTO.*;
import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.service.ParentsService;
import com.example.admin.service.PersonChildService;
import com.example.admin.service.WorkoutCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkoutCheckController {
    private final WorkoutCheckService workoutCheckService;
    private final PersonChildRepository personChildRepository;
    private final PersonChildService personChildService;
    private final ParentsService parentsService;

    @PostMapping("/workoutcheck/add")
    public ResponseWorkoutCheck addWorkoutCheck(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody WorkoutCheckUpdateRequest request) {
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
    public List<ResponseWorkoutCheck> getWorkoutCheckByDate(
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkedDay,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Authorization token: " + token);
        System.out.println("Date parameter: " + checkedDay);
        PersonChild personChild = personChildService.tokenToChild(token);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByDate(personChild, checkedDay)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
    }

    @GetMapping("/workoutcheck-parents")
    public List<ResponseWorkoutCheck> getWorkoutCheckByParents(
            @RequestParam("checkedDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkedDay,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Authorization token: " + token);
        System.out.println("Date parameter: " + checkedDay);
        Parents parents = parentsService.tokenToParents(token);
        if(parents == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        PersonChild personChild = personChildService.findChildByParent(parents);
        if(personChild == null) return null;
        for(WorkoutCheck workoutCheck : workoutCheckService.findByDate(personChild, checkedDay)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
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
