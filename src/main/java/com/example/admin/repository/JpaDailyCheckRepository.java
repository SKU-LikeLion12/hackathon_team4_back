package com.example.admin.repository;

import com.example.admin.domain.DailyCheck;
import com.example.admin.domain.PersonChild;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaDailyCheckRepository implements DailyCheckRepository {
    private final EntityManager em;
    private final PersonChildRepository personChildRepository;

    @Override
    public List<DailyCheck> findAll() {
        return em.createQuery("select c from DailyCheck c", DailyCheck.class).getResultList();
    }

    @Override
    public DailyCheck findById(Long id) {
        return em.find(DailyCheck.class, id);
    }

    @Override
    public void deleteDailyCheck(DailyCheck dailyCheck) { em.remove(dailyCheck);}

    @Override
    public List<DailyCheck> findByUserAll(PersonChild child){
        return em.createQuery("select d from DailyCheck d where d.child = :child", DailyCheck.class)
                .setParameter("child", child)
                .getResultList();
    }

    @Override
    public DailyCheck findByDate(Date date, PersonChild child){
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
