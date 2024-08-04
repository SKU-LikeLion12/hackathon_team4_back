package com.example.admin.repository;

import com.example.admin.domain.Medicine;
import com.example.admin.domain.Parents;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaMedicineRepository implements MedicineRepository {
    private final EntityManager em;

    @Override
    public int addMedicine(Medicine medicine) {

        try{
            em.persist(medicine);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    @Override
    public List<Medicine> getMedicine(Parents parents) {
        return em.createQuery("select me from Medicine me where me.parents = :parents", Medicine.class)
                .setParameter("parents",parents )
                .getResultList();
    }

    @Override
    public int delMedicine(Long medicineId) {
        try{
            em.remove(medicineId);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
}
