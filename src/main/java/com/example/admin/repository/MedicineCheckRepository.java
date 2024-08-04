package com.example.admin.repository;

import com.example.admin.domain.MedicineCheck;
import com.example.admin.domain.ScheduleMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineCheckRepository extends JpaRepository<MedicineCheck, Long> {
    Optional<MedicineCheck> findByScheduleMedicine(ScheduleMedicine scheduleMedicine);
}
