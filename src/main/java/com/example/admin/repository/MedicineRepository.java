package com.example.admin.repository;

import com.example.admin.domain.Medicine;
import com.example.admin.domain.Parents;

import java.util.List;

public interface MedicineRepository {

    
    // 약 추가하기
    int addMedicine (Medicine medicine);

    
    // 부모-약 전체 가져오기
    List<Medicine> getMedicine(Parents parents);

    int delMedicine (Long medicineId);

}
