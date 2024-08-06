package com.example.admin.controller;


import com.example.admin.DTO.MedicineDTO;
import com.example.admin.domain.Medicine;
import com.example.admin.service.MedicineService;
import com.example.admin.service.ParentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MedicineController {
    private final MedicineService medicineService;
    private final ParentsService parentsService;

    @PostMapping("/medicine/add")
    public ResponseEntity<String> addMedicine(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody List<MedicineDTO.RequestMedicine> request) {

        String token = authorizationHeader.replace("Bearer ", "");

        if(medicineService.addMedicine(request, token) == 1){
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        return ResponseEntity.ok("Fail");
    }

    @GetMapping("/medicine/get")
    public ResponseEntity<List<MedicineDTO.ResponseMedicine>> getMedicine(@RequestBody MedicineDTO.RequestGetMedicine request) {
        List<MedicineDTO.ResponseMedicine> response = new ArrayList<>();
        if(parentsService.tokenToParents(request.getToken()) == null)  return null;
        for( Medicine m : medicineService.getMedicine(request) ){
            response.add(new MedicineDTO.ResponseMedicine(m));
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

        @DeleteMapping("/medicine/del")
    public ResponseEntity<String> delMedicine(@RequestBody MedicineDTO.RequestDelMedicine request) {

        if(medicineService.delMedicine(request) == 1 ){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }



}
