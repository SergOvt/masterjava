package ru.javaops.masterjava.persist.dao;

import ru.javaops.masterjava.persist.model.BaseEntity;

public interface AbstractDao<T extends BaseEntity> {
    void clean();

    int insertGeneratedId(T entity);

    void insertWithId(T entity);

    default T insert(T entity) {
        if (entity.isNew()) {
            int id = insertGeneratedId(entity);
            entity.setId(id);
        } else {
            insertWithId(entity);
        }
        return entity;
    }
}
