package com.example.admin.repository;

import com.example.admin.domain.Parents;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class JpaParentsRepository implements ParentsRepository {
    private final EntityManager em;

    @Override
    public Parents save(Parents parents) {
        em.persist(parents);
        return parents;
    }

    @Override
    public Parents findById(Long id) {
        return em.find(Parents.class, id);
    }

    @Override
    public Parents findByUserId(String userId) {
        try {
            return em.createQuery("select p from Parents p where p.user_id = :user_id", Parents.class)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Parents> findAll() {
        return em.createQuery("select p from Parents p", Parents.class).getResultList();
    }

    @Override
    public void deleteParents(Parents parents) { em.remove(parents);}

    @Override
    public List<Parents> findByName(String name) {
        return em.createQuery("select p from Parents p where p.nickname = :name", Parents.class)
                .setParameter("name", name)
                .getResultList();
    }
}
