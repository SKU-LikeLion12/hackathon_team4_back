package com.example.admin.repository;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaDailyCheckRepository implements DailyCheckRepository {
    private final EntityManager em;
    private final PersonChildRepository personChildRepository;

    @Override
    public DailyCheck create(DailyCheck dailyCheck) {
        em.persist(dailyCheck);
        return dailyCheck;
    }

    @Override
    public List<DailyCheck> findAll() {
        return em.createQuery("select d from DailyCheck d", DailyCheck.class).getResultList();
    }

    @Override
    public DailyCheck findById(Long id) {
        return em.find(DailyCheck.class, id);
    }

    @Override
    public boolean deleteDailyCheck(LocalDate date, PersonChild child) {
        DailyCheck dailyCheck = findByDate(date, child);
        if (dailyCheck == null) return false;
        em.remove(dailyCheck);
        return true;
    }

    @Override
    public List<DailyCheck> findByUserAll(PersonChild child) {
        return em.createQuery("select d from DailyCheck d where d.child = :child", DailyCheck.class)
                .setParameter("child", child)
                .getResultList();
    }

    //    @Override
//    public DailyCheck findByDate(Date date, PersonChild child){
//        try {
//            return em.createQuery("select d from DailyCheck d where d.checkedDay = :checkedDay and d.child = :child", DailyCheck.class)
//                    .setParameter("checkedDay", date)
//                    .setParameter("child", child)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//
    @Override
    public DailyCheck findByDate(LocalDate date, PersonChild child) {
        try {
            return em.createQuery("select d from DailyCheck d where d.checkedDay = :checkedDay and d.child = :child", DailyCheck.class)
                    .setParameter("checkedDay", date)
                    .setParameter("child", child)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
