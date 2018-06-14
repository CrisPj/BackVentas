package com.pythonteam.handlers;

import java.sql.SQLException;
import java.util.List;

public interface BaseController<T,J> {

    List<T> findAll();
    T findOne(J id);
    boolean delete(J id);
    T update(T t);
    T create(T t) throws SQLException;
}
