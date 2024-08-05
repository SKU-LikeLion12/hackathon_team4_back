package com.example.admin.repository;

import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonChildRepository extends JpaRepository<PersonChild, Long> {

    PersonChild findByUniqueKey(String uniqueKey);

    List<PersonChild> findPersonChildByParent(Parents parents);
}
