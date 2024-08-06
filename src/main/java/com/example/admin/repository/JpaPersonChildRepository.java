package com.example.admin.repository;

import com.example.admin.domain.Parents;
import com.example.admin.domain.PersonChild;
import com.example.admin.service.ParentsService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class JpaPersonChildRepository implements PersonChildRepository {
    private final EntityManager em;
    private final ParentsRepository parentsRepository;
    private final ParentsService parentsService;

    @Override
    public PersonChild findByUniqueKey(String uniqueKey) {
        return em.find(PersonChild.class, Long.parseLong(uniqueKey));
    }

    @Override
    public List<PersonChild> findPersonChildByParent(Parents parent){
        return em.createQuery("select c from PersonChild c where c.parent = :p", PersonChild.class)
                .setParameter("p", parent).getResultList();
    }

    @Override
    public PersonChild findByParent(Parents parent) {
        return em.createQuery("select c from PersonChild c where c.parent = :p", PersonChild.class)
                .setParameter("p", parent).getSingleResult();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PersonChild> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PersonChild> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<PersonChild> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PersonChild getOne(Long aLong) {
        return null;
    }

    @Override
    public PersonChild getById(Long aLong) {
        return null;
    }

    @Override
    public PersonChild getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends PersonChild> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PersonChild> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends PersonChild> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends PersonChild> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PersonChild> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PersonChild> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PersonChild, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends PersonChild> S save(S entity) {
        return null;
    }

    @Override
    public <S extends PersonChild> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<PersonChild> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<PersonChild> findAll() {
        return List.of();
    }

    @Override
    public List<PersonChild> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(PersonChild entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PersonChild> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PersonChild> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<PersonChild> findAll(Pageable pageable) {
        return null;
    }
}
