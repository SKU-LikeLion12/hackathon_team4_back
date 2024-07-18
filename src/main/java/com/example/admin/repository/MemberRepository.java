package com.example.admin.repository;

import com.example.admin.domain.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    Member findById(Long id);

    Member findByUserId(String userid);

    List<Member> findAll();

    void deleteMember(Member member);

    List<Member> findByName(String name);
}
