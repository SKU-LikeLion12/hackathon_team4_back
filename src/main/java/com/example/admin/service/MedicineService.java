package com.example.admin.service;


import com.example.admin.DTO.MedicineDTO;
import com.example.admin.domain.Medicine;
import com.example.admin.domain.Parents;
import com.example.admin.repository.MedicineRepository;
import com.example.admin.repository.ParentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final ParentsService parentsService;


    @Transactional
    public int addMedicine(List<MedicineDTO.RequestMedicine>  request, String token) {

        try{
            for (MedicineDTO.RequestMedicine medicineDTO : request) {
                Parents parents = parentsService.tokenToParents(token);
                medicineRepository.addMedicine(  new Medicine(medicineDTO.getName(),parents)  ) ;

            }
            return 1;
        }catch (Exception e){
            return 0;
        }


    }


    public List<Medicine> getMedicine(MedicineDTO.RequestGetMedicine request) {
        Parents parents = parentsService.tokenToParents(request.getToken());
        return medicineRepository.getMedicine(parents);

    }

    @Transactional
    public int delMedicine(MedicineDTO.RequestDelMedicine request) {

        return medicineRepository.delMedicine(request.getId());

    }



}
