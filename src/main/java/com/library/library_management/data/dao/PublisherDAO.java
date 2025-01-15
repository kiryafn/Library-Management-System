package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublisherDAO implements DAO<Publisher> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    @Transactional //method should be done in transaction (auto commit or rollback)
    public void delete(int id) {
        Publisher publisher = entityManager.find(Publisher.class, id);
        if (publisher != null) {
            entityManager.remove(publisher);
        }
    }

    @Override
    @Transactional
    public void update(Publisher publisher) {
        entityManager.merge(publisher);
    }

    @Override
    public Publisher getById(int id) {
        return entityManager.find(Publisher.class, id);
    }

    @Override
    public List<Publisher> getAll() {
        return entityManager.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM Publisher p", Long.class).getSingleResult();
        return count.intValue();
    }
}