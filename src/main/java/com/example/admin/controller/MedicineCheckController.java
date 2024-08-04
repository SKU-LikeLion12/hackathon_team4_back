package com.example.admin.controller;

import com.example.admin.DTO.MedicineCheckDTO.*;
import com.example.admin.DTO.PersonChildDTO.*;
import com.example.admin.service.MedicineCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/medicine-check")
public class MedicineCheckController {

    private final MedicineCheckService medicineCheckService;



    @GetMapping("/{id}")
    public ResponseMedicineCheck getMedicineCheck(@PathVariable Long id) {
        return medicineCheckService.getMedicineCheck(id);
    }

    @PostMapping
    public ResponseMedicineCheck createOrUpdateMedicineCheck(@RequestBody RequestMedicineCheck request) {
        return medicineCheckService.createOrUpdateMedicineCheck(request);
    }

    //먹었어요 버튼
    @PostMapping("/toggle")
    public void toggleMedicineCheck(@RequestBody RequestMedicineCheck request) {
        medicineCheckService.toggleMedicineCheck(request);
    }


    // 복용량
    @GetMapping("/intake-rate")
    public IntakeRate getIntakeRate(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authorizationHeader.replace("Bearer ", "");
        return medicineCheckService.calculateIntakeRateForChild(token);
    }
}