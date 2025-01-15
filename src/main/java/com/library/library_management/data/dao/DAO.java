package com.library.library_management.data.dao;

import jakarta.transaction.Transactional;

import java.util.List;

public interface DAO<Entity> {

    void insert(Entity t);

    void delete(int id);

    void update(Entity t);

    Entity getById(int id);

    List<Entity> getAll();

    long getCount();
}
