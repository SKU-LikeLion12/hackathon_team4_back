package com.example.admin.repository;

import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonChildRepository extends JpaRepository<PersonChild, Long> {

    PersonChild findByUniqueKey(String uniqueKey);
}
