package com.ecf.gamestore.service;

import com.ecf.gamestore.models.interfaces.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractService<R extends JpaRepository, T extends IEntity> implements IService<T> {
    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Transactional
    public T save(T entity) {
        if(Objects.isNull(entity)) {
            throw new IllegalArgumentException("The entity to be saved cannot be null.");
        }
        else if(Objects.nonNull(entity) && Objects.nonNull(entity.getId())) {
            entity.setUpdatedAt(LocalDateTime.now());
        }
        else {
            entity.setUuid(UUID.randomUUID().toString());
            entity.setCreatedAt(LocalDateTime.now());
        }
        return (T) this.repository.save(entity);
    }

    @Transactional(readOnly = true)
    public T findById(Long id) throws Throwable {
        if(Objects.isNull(id)) throw new IllegalArgumentException("The entity's ID cannot be null");
        return (T) this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Transactional
    public void delete(Long id) throws Throwable {
        if(Objects.isNull(id)) throw new IllegalArgumentException("The entity's ID cannot be null");
        T entity = this.findById(id);
        this.repository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<T> listAll() {
        return this.repository.findAll();
    }
}
