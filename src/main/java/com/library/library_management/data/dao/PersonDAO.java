package com.library.library_management.data.dao;

import com.library.library_management.data.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO implements DAO<Person> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Person person) {
        entityManager.persist(person);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    @Transactional
    public void update(Person person) {
        entityManager.merge(person);
    }

    @Override
    public Person getById(int id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> getAll() {
        return entityManager.createQuery("SELECT u FROM Person u", Person.class).getResultList();
    }

    @Override
    public long getCount() {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM Person u", Long.class).getSingleResult();
        return count.intValue();
    }

    public Person getByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM Person u WHERE u.email = :email", Person.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}