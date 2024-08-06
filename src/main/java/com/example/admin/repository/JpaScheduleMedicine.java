package com.example.admin.repository;


import com.example.admin.domain.PersonChild;
import com.example.admin.domain.ScheduleMedicine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaScheduleMedicine implements ScheduleMedicineRepository {
    private final EntityManager em;

    @Override
    public void addSchedule(ScheduleMedicine scheduleMedicine) {
        em.persist(scheduleMedicine);
    }

    @Override
    public List<ScheduleMedicine> getSchedule(PersonChild personChild) {
        try {
            return em.createQuery( "select sm from ScheduleMedicine sm where sm.personChild = :pc " , ScheduleMedicine.class)
                    .setParameter("pc",personChild)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ScheduleMedicine findById(Long id) {
        return em.find(ScheduleMedicine.class, id);
    }

    @Override
    public Optional<ScheduleMedicine> findByIdOp(Long id) {
        ScheduleMedicine scheduleMedicine = em.find(ScheduleMedicine.class, id);
        return Optional.ofNullable(scheduleMedicine);
    }
}
