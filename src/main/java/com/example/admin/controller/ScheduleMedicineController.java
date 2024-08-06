package com.example.admin.controller;


import com.example.admin.DTO.MedicineDTO;
import com.example.admin.DTO.ScheduleMedicineDTO;
import com.example.admin.domain.Medicine;
import com.example.admin.domain.ScheduleMedicine;
import com.example.admin.service.PersonChildService;
import com.example.admin.service.ScheduleMedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleMedicineController {

    private final ScheduleMedicineService scheduleMedicineService;
    private final PersonChildService personChildService;


    @PostMapping("/schedule/add")
    public ResponseEntity<String> addSchedule(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody List<ScheduleMedicineDTO.RequestSchedule> request) {

        // "Bearer " 접두사 제거
        String token = authorizationHeader.replace("Bearer ", "");
        if (personChildService.tokenToChild(token) == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (scheduleMedicineService.addSchedule(token, request) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        return ResponseEntity.ok("Fail");
    }


    @GetMapping("/schedule/get")
    public ResponseEntity<List<ScheduleMedicineDTO.ResponseSchedule>> getScheduleMedicine(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        if(personChildService.tokenToChild(token) == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<ScheduleMedicineDTO.ResponseSchedule> response = new ArrayList<>();
        for( ScheduleMedicine sm : scheduleMedicineService.getSchedule(token) ){
            response.add(new ScheduleMedicineDTO.ResponseSchedule(sm));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
