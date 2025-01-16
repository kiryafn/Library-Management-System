package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Copy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CopyDAO implements DAO<Copy> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Copy copy) {
        entityManager.persist(copy);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Copy copy = entityManager.find(Copy.class, id);
        if (copy != null) {
            entityManager.remove(copy);
        }
    }

    @Override
    @Transactional
    public void update(Copy copy) {
        entityManager.merge(copy);
    }

    @Override
    public Copy getById(int id) {
        return entityManager.find(Copy.class, id);
    }

    @Override
    public List<Copy> getAll() {
        return entityManager.createQuery("SELECT c FROM Copy c", Copy.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(c) FROM Copy c", Long.class).getSingleResult();
        return count.intValue();
    }
}