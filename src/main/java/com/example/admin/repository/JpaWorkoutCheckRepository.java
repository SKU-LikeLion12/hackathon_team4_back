package com.example.admin.repository;

import com.example.admin.domain.PersonChild;
import com.example.admin.domain.WorkoutCheck;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaWorkoutCheckRepository implements WorkoutCheckReopsitory{
    private final EntityManager em;
    private final PersonChildRepository personChildRepository;

    @Override
    public WorkoutCheck create(WorkoutCheck workoutCheck) {
        em.persist(workoutCheck);
        return workoutCheck;
    }

    @Override
    public void delete(PersonChild child, Date date, String type, String name){
        WorkoutCheck workoutCheck = find(child, date, type, name);
        if(workoutCheck == null) return;
        em.remove(workoutCheck);
    }

    @Override
    public List<WorkoutCheck> findByChild(PersonChild personChild) {
        try {
            return em.createQuery("select w from WorkoutCheck w where w.uniqueKey = :uniqueKey",WorkoutCheck.class)
                    .setParameter("uniqueKey", personChild.getUniqueKey())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<WorkoutCheck> findByDate(PersonChild child, Date date) {
        try {
            return em.createQuery("select w from WorkoutCheck w where w.checkedDay = :day and w.uniqueKey = :uniqueKey",WorkoutCheck.class)
                    .setParameter("day", date)
                    .setParameter("uniqueKey", child.getUniqueKey())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<WorkoutCheck> findByType(PersonChild child ,String type) {
        try {
            return em.createQuery("select w from WorkoutCheck w where w.workoutType = :type and w.uniqueKey = :uniqueKey",WorkoutCheck.class)
                    .setParameter("type", type)
                    .setParameter("uniqueKey", child.getUniqueKey())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<WorkoutCheck> findByName(PersonChild child, String name) {
        try {
            return em.createQuery("select w from WorkoutCheck w where w.workoutName = :name and w.uniqueKey = :uniqueKey",WorkoutCheck.class)
                    .setParameter("name", name)
                    .setParameter("uniqueKey", child.getUniqueKey())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public WorkoutCheck find(PersonChild child, Date date, String type, String name){
        try {
            return em.createQuery("select w from WorkoutCheck w where w.uniqueKey = :uniqueKey and w.checkedDay = :date and w.workoutType = :type and w.workoutName = :name",WorkoutCheck.class)
                    .setParameter("name", name)
                    .setParameter("uniqueKey", child.getUniqueKey())
                    .setParameter("date", date)
                    .setParameter("type", type)
                    .getSingleResult();
        } catch(Exception e){
            return null;
        }
    }
}
