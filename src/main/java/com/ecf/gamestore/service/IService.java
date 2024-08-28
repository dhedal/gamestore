package com.ecf.gamestore.service;

import java.util.List;

public interface IService<T> {
    public T save(T entity);
    public T findById(Long id) throws Throwable;
    public void delete(Long id) throws Throwable;
    public List<T> listAll();
}
