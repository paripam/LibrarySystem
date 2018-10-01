package com.paripa.by.dao;

import com.paripa.by.dao.exception.DaoException;
import com.paripa.by.entity.Entity;

import java.util.List;

public abstract class EntityDao<K, T extends Entity> {

    public abstract List<T> findAll() throws DaoException;
    public abstract T findById(K id) throws DaoException;
    public abstract void deleteById(K id) throws DaoException;
    public abstract K create(T entity) throws DaoException;
    public abstract void update(T entity) throws DaoException;
}
