package com.example.admin.repository;

import com.example.admin.domain.Parents;

import java.util.List;

public interface ParentsRepository {
    Parents save(Parents member);

    Parents findById(Long id);

    Parents findByUserId(String userid);

    List<Parents> findAll();

    void deleteParents(Parents member);

    List<Parents> findByName(String name);
}
