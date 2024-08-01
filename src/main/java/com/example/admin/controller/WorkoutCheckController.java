package com.example.admin.controller;

import com.example.admin.DTO.WorkoutCheckDTO.*;
import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import com.example.admin.repository.PersonChildRepository;
import com.example.admin.service.WorkoutCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkoutCheckController {
    private final WorkoutCheckService workoutCheckService;
    private final PersonChildRepository personChildRepository;

    @PostMapping("/workoutcheck/add")
    public ResponseWorkoutCheck addWorkoutCheck(@RequestBody WorkoutCheckRequest request) {
        PersonChild personChild = personChildRepository.findByUniqueKey(request.getUniqueKey());
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.create(request.getUniqueKey(), request.getCheckedDay(), request.getWorkoutType(), request.getWorkoutName());
        return new ResponseWorkoutCheck(workoutCheck);
    }

    @PutMapping("/workoutcheck/update")
    public ResponseWorkoutCheck updateWorkoutCheck(@RequestBody WorkoutCheckRequest request) {
        PersonChild personChild = personChildRepository.findByUniqueKey(request.getUniqueKey());
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.update(request.getUniqueKey(), request.getCheckedDay(), request.getWorkoutType(), request.getWorkoutName());
        return new ResponseWorkoutCheck(workoutCheck);
    }

    @DeleteMapping("/workoutcheck")
    public String deleteWorkoutCheck(@RequestBody WorkoutCheckRequest request) {
        PersonChild personChild = personChildRepository.findByUniqueKey(request.getUniqueKey());
        if(personChild == null) return null;
        workoutCheckService.delete(request.getUniqueKey(), request.getCheckedDay(), request.getWorkoutType(), request.getWorkoutName());
        return "success";
    }

    @GetMapping("/workoutcheck/{uniqueKey}")
    public List<ResponseWorkoutCheck> getWorkoutCheckByKey(@PathVariable("uniqueKey") String uniqueKey) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByChild(uniqueKey)){
            response.add(new ResponseWorkoutCheck(workoutCheck));
        }
        return response;
    }

    @GetMapping("/workoutcheck/{uniqueKey}/{date}")
    public List<ResponseWorkoutCheck> getWorkoutCheckByDate(@PathVariable("uniqueKey") String uniqueKey, @PathVariable("date") Date date) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        List<ResponseWorkoutCheck> response = new ArrayList<>();
        for(WorkoutCheck workoutCheck : workoutCheckService.findByDate(uniqueKey, date)){
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

    @GetMapping("/workoutcheck/{uniqueKey}/{date}/{type}/{name}")
    public ResponseWorkoutCheck getWorkoutCheck(@PathVariable("uniqueKey") String uniqueKey,
                                                            @PathVariable("date") Date date,
                                                            @PathVariable("type") String type,
                                                            @PathVariable("name") String name) {
        PersonChild personChild = personChildRepository.findByUniqueKey(uniqueKey);
        if(personChild == null) return null;
        WorkoutCheck workoutCheck = workoutCheckService.find(uniqueKey, date, type, name);
        return new ResponseWorkoutCheck(workoutCheck);
    }
}
